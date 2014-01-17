package com.youai.gamemis.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;

import javax.persistence.Table;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.model.ExtendLinkType;
import com.youai.gamemis.model.FieldValueType;
import com.youai.gamemis.model.Mentity;
import com.youai.gamemis.model.Mfield;
import com.youai.gamemis.model.OptionValue;
import com.youai.gamemis.model.OptionValueType;
import com.youai.gamemis.model.dao.MentityDAO;
import com.youai.gamemis.model.dao.MfieldDAO;
import com.youai.gamemis.options.CustomSelectOptions;
import com.youai.gamemis.util.CommonUtil;

import javax.persistence.Id;
import javax.persistence.Transient;

@Service("managedEntityService")
public class ManagedEntityService  implements ApplicationContextAware{

	private final static Logger logger = Logger
			.getLogger(GameEntityService.class);
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	private static ApplicationContext applicationContext;
	@Autowired
	private MentityDAO mentityDao;
	@Autowired
	private MfieldDAO mfieldDao;
	public List<Mentity> list() {
		List<Mentity> mentities = mentityDao.list();
//		for (Mentity entity : mentities) {
//			String mentityId = entity.getId();
//			List<Mfield> mfields = mfieldDao.findMfieldsByMentityId( mentityId );
//			entity.setFields( null );
//		}
		return mentities;
	}
	
	public Mentity getMentityById( String id ){
		Mentity mentity = mentityDao.findById( id );
		mentity.setShard(0);
		List<Mfield> mfields = mfieldDao.findMfieldsByMentityId( id );
		List<Mfield> commonFields = new ArrayList<Mfield>();
		for( Mfield mfield : mfields ){
			if( mfield.getIsKey() == 1 ){
				mentity.setIdField( mfield );
			}else{
				commonFields.add( mfield );
			}
			if( mfield.getName().equalsIgnoreCase("serveridx") ){
				mentity.setShard(1);
			}
		}
		mentity.setFields( commonFields );
		return mentity;
	}
	
	/**
	 * 拿到Mentity,通过解析json
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Mentity getMentityWithRealValues( String id ) throws Exception{
		Mentity mentity = this.getMentityById( id );
		
		for( Mfield mfield :mentity.getFields() ){
			if( mfield.getValueUitype() == FieldValueType.select.ordinal() || mfield.getValueUitype() == FieldValueType.multi_select.ordinal()){
				String values = mfield.getFieldValues();
				logger.info(">>>>>>>>>field name:"+mfield.getName()+" field values:"+values );
				OptionValueType optionValueType = gson.fromJson( values, OptionValueType.class);
				
				//type=0 ：value来自于用户输入的values，type=1：value来自于关联的类属性值
				if(optionValueType.getType() == 0 ){
					mfield.setFieldRealValues( gson.toJson( optionValueType.getOptions() ) );
				}else if( optionValueType.getType() == 1){
					List<OptionValue> options = optionValueType.getOptions();
					String optionClass = null;
					String text_field = null;
					String value_field = null;
					for( OptionValue option: options){
						if( option.getKey().equals("class" ) ){
							optionClass = option.getValue();
						}
						//select 中option的文本
						if( option.getKey().equals( "text") ){
							text_field = option.getValue();
						}
						//select 中option的value
						if( option.getKey().equals("value") ){
							value_field = option.getValue();
						}
					}
					if( optionClass == null || text_field == null || value_field == null ){
						throw new Exception("Json parameter has not the necessary fields: class, text, value!");
					}
					Class option_clazz =  Class.forName( optionClass);
					List<OptionValue> optionValues = mentityDao.getOptionValues(option_clazz, value_field, text_field );	
					mfield.setFieldRealValues( gson.toJson( optionValues) );
				}
			}else if( mfield.getValueUitype() == FieldValueType.custom_select.ordinal()) {
				String values = mfield.getFieldValues();
				logger.info(">>>>>>>>>field name:"+mfield.getName()+" field values:"+values );
				OptionValueType optionValueType = gson.fromJson( values, OptionValueType.class);
				List<OptionValue> options = optionValueType.getOptions();
				String beanClass = null;
				String dependFieldName = null;
				for( OptionValue option : options ){
					if( option.getKey().equals("bean") ){
						 beanClass = option.getValue();
					}
					if( option.getKey().equals("dependfield") ){
						dependFieldName = option.getValue();
					}
				}
				CustomSelectOptions beanObject = (CustomSelectOptions)applicationContext.getBean(beanClass);
				List<OptionValue> optionValues = beanObject.getOptions();
				
				mfield.setDependField( dependFieldName );
				Map<String,String> optionMap = new HashMap<String,String>();
				for(OptionValue optionValue: optionValues ){
					optionMap.put( optionValue.getParentKey()+"-"+optionValue.getKey(), optionValue.getValue() );
				}
				mfield.setOptionValueMap( optionMap );
				mfield.setOptionValues( optionValues );
				mfield.setFieldRealValues( gson.toJson( optionValues ) );
			}else if( mfield.getValueUitype() == FieldValueType.ajax_select.ordinal()) {
				String values = mfield.getFieldValues();
				logger.info(">>>>>>>>>field name:"+mfield.getName()+" field values:"+values );
				OptionValueType optionValueType = gson.fromJson( values, OptionValueType.class);
				if(optionValueType != null){
					List<OptionValue> options = optionValueType.getOptions() ;
					String optionClass = null;
					String text_field = null;
					String value_field = null;
					String ajax_url = null;
					String ajax_related_input = null;
					for( OptionValue option: options){
						if( option.getKey().equals("class" ) ){
							optionClass = option.getValue();
						}
						//select 中option的文本
						if( option.getKey().equals( "text") ){
							text_field = option.getValue();
						}
						//select 中option的value
						if( option.getKey().equals("value") ){
							value_field = option.getValue();
						}
					}
					if( optionClass != null && text_field != null && value_field != null ){
						Class option_clazz =  Class.forName( optionClass);
						List<OptionValue> optionValues = mentityDao.getOptionValues(option_clazz, value_field, text_field );	
						mfield.setFieldRealValues( gson.toJson( optionValues) );
					}
				}
			}
		}
		return mentity;
	}
	 public ArrayList<Mfield> getMfields(String entityClassPath) {       
	        Class entityClass;
	        ArrayList<Mfield> mfields = new ArrayList<Mfield>();
	        try {
	            entityClass = Class.forName(entityClassPath);
	           
	            Field[] fields = entityClass.getDeclaredFields();
	            HashMap<String, Field> fieldNameMap = new HashMap<String, Field>();
	            Table table = (Table)entityClass.getAnnotation(Table.class);
	            String catalog = table.catalog();
	            for (Field field : fields) {
	                if( field.getAnnotation(Transient.class) == null ){
	                	Mfield mfield = new Mfield();
	                	mfield.setName( field.getName() );
	                	mfield.setType( field.getType().getCanonicalName() );
	                	mfield.setIsKey( 0 );
	                	
	                	//设置主键，1，从field上取id的anno 2，从field的get方法上取id的anno
	                	if( field.getAnnotation(Id.class) != null ){
	                		mfield.setIsKey( 1 );
	                	}else{
	                		Method method;
							try {
								method = entityClass.getMethod( CommonUtil.getGetterMethod( field.getName(), catalog) );
								if( method.getAnnotation(Id.class) != null ){
			                		mfield.setIsKey( 1 );
			                	}
							} catch (SecurityException e) {
								// TODO Auto-generated catch block
								logger.info( "The class:"+entityClassPath+" has no getter method for property:"+field.getName() );
							} catch (NoSuchMethodException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                	
	                	}
	                	
	                	mfield.setNum(0);
	                	//fieldNameMap.put(field.getName(), field);
	                	mfields.add( mfield );
	                }  
	            }


	            return mfields;
	        } catch (ClassNotFoundException e) {
	            // TODO Auto-generated catch block
	            logger.error("Class "+entityClassPath +" nod found!" );
	            logger.error(e.toString() );
	            return null;
	        }

	    }
	 
	 
	 public Mentity addMentity( Mentity item ){
		 mentityDao.saveOrUpdate( item );
		
		 List<Mfield> fields = item.getFields();
		 for(Mfield field: fields ){
			 field.setMentityId( item.getId() );
			 mfieldDao.saveOrUpdate(field);
		 }
		 return item;
	 }
	 
	 public boolean deleteMentity(String id){
		 mentityDao.deleteMentity( id );
		 
		 mfieldDao.deleteByMentityId( id );
		 return true;
	 }
	 
	 public boolean updateMentity( Mentity item ){
		 
		 mentityDao.saveOrUpdate( item );
		 List<Mfield> fields = item.getFields();
		 mfieldDao.deleteByMentityId( item.getId() );
		 for(Mfield mfield: fields ){
			 mfieldDao.saveOrUpdate(mfield);
		 }
		 return true;
		 
	 }

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.applicationContext = arg0;
		
	}
}
