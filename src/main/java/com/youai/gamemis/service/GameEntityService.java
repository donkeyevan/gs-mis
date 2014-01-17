package com.youai.gamemis.service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.youai.gamemis.model.ActionType;
import com.youai.gamemis.model.DBCatalogType;
import com.youai.gamemis.model.ExtendLink;
import com.youai.gamemis.model.FieldValueType;
import com.youai.gamemis.model.Mentity;
import com.youai.gamemis.model.Mfield;
import com.youai.gamemis.model.MisLog;
import com.youai.gamemis.model.MyEntry;
import com.youai.gamemis.model.PageQueryInfo;
import com.youai.gamemis.model.dao.GameEntityDAO;
import com.youai.gamemis.util.CommonUtil;
import com.youai.gamemis.util.RequestHelper;

@Service("gameEntityService")
public class GameEntityService {
	private final static Logger logger = Logger
			.getLogger(GameEntityService.class);
	private static Gson gson = new GsonBuilder().serializeNulls().create();

	@Autowired
	GameEntityDAO gentityDao;
//	@Autowired
//	AjaxService ajaxService;

	public Mentity addGameEntity(Mentity mentity, String adminName)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, SecurityException, NoSuchMethodException,
			IllegalArgumentException, InvocationTargetException {
		String modelClass = mentity.getEntityClass();
		Class clazz = Class.forName(modelClass);
		Object obj = clazz.newInstance();
		Method method = null;
		List<Mfield> mfields = mentity.getFields();
		for (Mfield mfield : mfields) {			
			Class typeClazz = RequestHelper.getFieldClass(mfield.getType());			
			method = clazz.getMethod(CommonUtil.getSetterMethod(mfield.getName(), mentity.getCatalog() ), typeClazz );
			if( mfield.getValue() != null ){
				method.invoke(obj, mfield.getValue());
			}
		}
		
		//记录日志
		if( mentity.getEntityClass().endsWith(".Bag") && gentityDao.getCatalog() == DBCatalogType.game_server ){
				MisLog misLog = new MisLog();
				misLog.setAdminName(adminName);
				misLog.setOpTable( mentity.getEntityClass().substring( mentity.getEntityClass().lastIndexOf(".")+1));
				misLog.setOpField( null );
				method = clazz.getMethod(CommonUtil.getGetterMethod( 
						"count", mentity.getCatalog()) );
				Object amount = (Object)method.invoke( obj );
				misLog.setSNewValue( String.valueOf((Integer)amount) );
				misLog.setSOldValue( String.valueOf( 0 ) );
				method = clazz.getMethod(CommonUtil.getGetterMethod( 
						"playerid", mentity.getCatalog()) );
				Object ownerId = (Object)method.invoke( obj );
				misLog.setTargetId( (Integer)ownerId );
				method = clazz.getMethod(CommonUtil.getGetterMethod( 
						"itemid", mentity.getCatalog()) );
				Object goodsType = (Object)method.invoke( obj );
				misLog.setOpType( (Integer)goodsType );
				
				misLog.setTargetName( null );
				misLog.setCreatedAt( new Date() );
				misLog.setActionType( ActionType.ADD_PLAYER_GOOODS.getType() );
				gentityDao.save( misLog );
		}
		
		gentityDao.setCatalog( mentity.getCatalog() );
		gentityDao.save(obj);
		method = clazz.getMethod( CommonUtil.getGetterMethod(mentity.getIdField().getName(),  mentity.getCatalog() ) );
		Object key = method.invoke( obj );
		mentity.getIdField().setValue( key );
		logger.info("add mentity class:"+mentity.getEntityClass() );
		return mentity;
	}

	public PageQueryInfo getGameEntites(DetachedCriteria detachedCriteria,
			PageQueryInfo queryInfo, Mentity mentity )
			throws ClassNotFoundException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {
		gentityDao.setCatalog( mentity.getCatalog() );
		
		Map result = gentityDao.findGentitiesByClass(detachedCriteria,
				queryInfo);
		int pagesum = (Integer) result.get("pagesum");
		int totalCount = (Integer)result.get("totalcount");
		List<Object> entities = (List<Object>) result.get("entities");
		List<Mentity> uientities = new ArrayList<Mentity>();
		List<Mfield> metaFields = mentity.getAllfields();
		Map<String,Mfield> fieldMap = new HashMap<String,Mfield>();
		for(Mfield field: metaFields ){
			fieldMap.put( field.getId(), field);
			//logger.info( gson.toJson( field.getOptionValueMap() ) );
		}
		for (Object entity : entities) {
			Mentity centity = (Mentity) mentity.clone();
			List<Mfield> dataFields = centity.getFields();
			Class clazz = Class.forName(mentity.getEntityClass());
			for (Mfield mfield : dataFields) {
				
				Method method = clazz.getMethod(CommonUtil.getGetterMethod(mfield.getName(),  mentity.getCatalog()));
				Object value = method.invoke(entity);
				if( mfield.getValueUitype() == FieldValueType.custom_select.ordinal() ){
					method = clazz.getMethod(CommonUtil.getGetterMethod(mfield.getDependField(),  mentity.getCatalog()));
					 Object parentValue = method.invoke(entity);
					 mfield.setValue(value);
					 mfield.setFieldRealValues("");
					 Mfield metaField = fieldMap.get( mfield.getId() );
					 String showValue = metaField.getOptionValueMap().get( parentValue + "-"+value );
					 logger.info( "key:"+ parentValue + "-" + value +" showValue:" + showValue );
					 
					 mfield.setShowValue( showValue );
				}else{
					mfield.setValue(value);
				}
			}
			String idName = centity.getIdField().getName();
			Method method = clazz.getMethod( CommonUtil.getGetterMethod( idName , mentity.getCatalog()) );
			Object value = method.invoke(entity);
			centity.getIdField().setValue( value );
			if( centity.getExtendLinkType() != null ){
				for( ExtendLink elink : centity.getExtendLinkType().getExtendLinks() ){
					elink.setLink( elink.getLink().replace("#{id}", ""+centity.getIdField().getValue()) );
				}
			}
			
			//加入ajax_input操作
			//ajaxService.addAjaxElement(centity);
			
			uientities.add(centity);
		}
		queryInfo.setMentities(uientities);
		queryInfo.setPageSum(pagesum);
		queryInfo.setRowCount( totalCount );
		return queryInfo;
	}
	public Mentity getGameEntity(Mentity gameEntity) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		gentityDao.setCatalog( gameEntity.getCatalog() );
		Object entity = gentityDao.getById((Serializable)(gameEntity.getIdField().getValue()), gameEntity.getEntityClass() );
		if( entity == null ){
			return null;
		}
		List<Mfield> dataFields = gameEntity.getFields();
		Class clazz = Class.forName(gameEntity.getEntityClass());
		
		for (Mfield mfield : dataFields) {
			if( mfield.getValueUitype() == FieldValueType.custom_select.ordinal() ){
				Method method = clazz.getMethod( CommonUtil.getGetterMethod(mfield.getName(), gameEntity.getCatalog()) );
				Object value = method.invoke(entity);
				 method = clazz.getMethod(CommonUtil.getGetterMethod(mfield.getDependField(),  gameEntity.getCatalog()));
				 Object parentValue = method.invoke(entity);
				 mfield.setValue(parentValue + ":"+value );

			}else{
				Method method = clazz.getMethod( CommonUtil.getGetterMethod(mfield.getName(), gameEntity.getCatalog()) );
				Object value = method.invoke(entity);
				mfield.setValue(value);
			}
		}
		String idName = gameEntity.getIdField().getName();
		Method method = clazz.getMethod( CommonUtil.getGetterMethod( idName, gameEntity.getCatalog() ) );
		Object value = method.invoke(entity);
		gameEntity.getIdField().setValue( value );
		return gameEntity;
	}
	public Mentity getNextGameEntity(DetachedCriteria detachedCriteria, Mentity gameEntity) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Object entity = gentityDao.getNext( detachedCriteria );
		if( entity == null ){
			return null;
		}
		List<Mfield> dataFields = gameEntity.getFields();
		Class clazz = Class.forName(gameEntity.getEntityClass());
		for (Mfield mfield : dataFields) {
			String methodName = CommonUtil.getGetterMethod(mfield.getName(), gameEntity.getCatalog() );
			Method method = clazz.getMethod( methodName );
			Object value = method.invoke(entity);
			mfield.setValue(value);
		}
		String idName = gameEntity.getIdField().getName();
		Method method = clazz.getMethod( CommonUtil.getGetterMethod( idName, gameEntity.getCatalog() ) );
		Object value = method.invoke(entity);
		gameEntity.getIdField().setValue( value );
		return gameEntity;
	}
	public boolean saveGameEntity(Mentity gameEntity, String adminName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
		Class clazz = Class.forName( gameEntity.getEntityClass() );
		Object obj = gentityDao.getById((Serializable)(gameEntity.getIdField().getValue()), gameEntity.getEntityClass() );
		//Object obj = clazz.newInstance();
		Class idType = RequestHelper.getFieldClass( gameEntity.getIdField().getType());
		//Method method = clazz.getMethod( CommonUtil.getSetterMethod( gameEntity.getIdField().getName(),  gameEntity.getCatalog() ), idType );
		//method.invoke( obj, gameEntity.getIdField().getValue());
		Method method;
		String playerName = null;
		List<Mfield> mfields = gameEntity.getFields();
		gentityDao.setCatalog( gameEntity.getCatalog() );
		if( gameEntity.getEntityClass().endsWith(".Player") && gentityDao.getCatalog() == DBCatalogType.game_server ){
			method = clazz.getMethod( CommonUtil.getGetterMethod( 
					"name",  gameEntity.getCatalog() )
			);
			playerName = (String)method.invoke( obj );
		}
		for(Mfield mfield : mfields){
			if( mfield.getValue() != null ){
				//记录为用户加金条和金币的日志行为
				if( gameEntity.getEntityClass().endsWith(".Player")  && gentityDao.getCatalog() == DBCatalogType.game_server){
						method = clazz.getMethod( CommonUtil.getGetterMethod( 
								mfield.getName(),  gameEntity.getCatalog() )	
						);
						Object oldValue = (Object)method.invoke( obj );
						if( !RequestHelper.valueEqual( oldValue, mfield.getValue(), mfield.getType() ) ){
							MisLog misLog = new MisLog();
							misLog.setAdminName(adminName);
							misLog.setOpTable(  gameEntity.getEntityClass().substring( gameEntity.getEntityClass().lastIndexOf(".") + 1));
							misLog.setOpField( mfield.getName() );
							misLog.setSNewValue( String.valueOf(mfield.getValue() ) );
							misLog.setSOldValue( String.valueOf( oldValue ) );
							if( mfield.getName().equalsIgnoreCase("silvercoins")  ){
								misLog.setOldValue( (Long)oldValue );
								misLog.setNewValue( (Long)mfield.getValue() );
								misLog.setActionType(ActionType.UPDATE_PLAYER_MONEY.getType());
							}
							else if( mfield.getName().equalsIgnoreCase("rechargegoldcoins") || mfield.getName().equalsIgnoreCase("sysgoldcoins") ){
								misLog.setOldValue( ((Integer)oldValue).longValue()  );
								misLog.setNewValue( ((Integer)mfield.getValue()).longValue() );
								misLog.setActionType(ActionType.UPDATE_PLAYER_GOLD.getType());
							}else{
								misLog.setActionType( ActionType.UPDATE_PLAYER_ESLE.getType() );
							}
							misLog.setTargetId( (Integer)gameEntity.getIdField().getValue() );
							misLog.setTargetName( playerName );
							misLog.setCreatedAt( new Date() );
							gentityDao.save( misLog );
						}
	
				}else if(  ( gameEntity.getEntityClass().endsWith(".Bag") ) && gentityDao.getCatalog() == DBCatalogType.game_server ){

					method = clazz.getMethod( CommonUtil.getGetterMethod( 
							mfield.getName(),  gameEntity.getCatalog() )	
					);
					Object oldValue = (Object)method.invoke( obj );
					
					if( !RequestHelper.valueEqual( oldValue, mfield.getValue(), mfield.getType() ) ){
						MisLog misLog = new MisLog();
						misLog.setAdminName(adminName);
						misLog.setOpTable(  gameEntity.getEntityClass().substring( gameEntity.getEntityClass().lastIndexOf(".")+1));
						misLog.setOpField( mfield.getName() );
						misLog.setSNewValue( String.valueOf(mfield.getValue()) );
						misLog.setSOldValue( String.valueOf( oldValue ) );
						misLog.setTargetId( (Integer)gameEntity.getIdField().getValue() );
						misLog.setTargetName( playerName );
						misLog.setCreatedAt( new Date() );
						misLog.setActionType( ActionType.UPDATE_PLAYER_ESLE.getType() );
						method = clazz.getMethod(CommonUtil.getGetterMethod( 
								"type", gameEntity.getCatalog()) );
						Object goodsType = (Object)method.invoke( obj );
						misLog.setOpType( (Integer)goodsType );
						
						method = clazz.getMethod(CommonUtil.getGetterMethod( 
								"category", gameEntity.getCatalog()) );
						Object goodsCategory = (Object)method.invoke( obj );
						misLog.setOpCategory( (Integer)goodsCategory );
						gentityDao.save( misLog );
					}
				}
				else{
					if( gentityDao.getCatalog() == DBCatalogType.game_server ){
						method = clazz.getMethod( CommonUtil.getGetterMethod( 
								mfield.getName(),  gameEntity.getCatalog() )	
						);
						Object oldValue = (Object)method.invoke( obj );
						if( !RequestHelper.valueEqual( oldValue, mfield.getValue(), mfield.getType() ) ){
							MisLog misLog = new MisLog();
							misLog.setAdminName(adminName);
							misLog.setOpTable(  gameEntity.getEntityClass().substring( gameEntity.getEntityClass().lastIndexOf(".")+1));
							misLog.setOpField( mfield.getName() );
							misLog.setSNewValue( String.valueOf(mfield.getValue()) );
							misLog.setSOldValue( String.valueOf( oldValue ) );
							misLog.setTargetId( (Integer)gameEntity.getIdField().getValue() );
							misLog.setTargetName( playerName );
							misLog.setCreatedAt( new Date() );
							misLog.setActionType( ActionType.UPDATE_PLAYER_ESLE.getType() );
							gentityDao.save( misLog );
						}
					}
				}
				method = clazz.getMethod( CommonUtil.getSetterMethod(
					mfield.getName(),  gameEntity.getCatalog() ), 
					RequestHelper.getFieldClass( mfield.getType())
				); 
				method.invoke(obj, mfield.getValue() );
			}
		}
		
		
		gentityDao.update( obj );
		
		return true;
	}
	public boolean deleteGameEntity( Mentity gameEntity) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
		Class clazz = Class.forName( gameEntity.getEntityClass() );
		Object obj = clazz.newInstance();
		Method method = clazz.getMethod( CommonUtil.getSetterMethod(gameEntity.getIdField().getName(), gameEntity.getCatalog() ), 
				RequestHelper.getFieldClass(gameEntity.getIdField().getType()));
		method.invoke(obj, gameEntity.getIdField().getValue() );
		gentityDao.setCatalog( gameEntity.getCatalog() );
		gentityDao.delete( obj );
		return true;
	}
	public Mentity getSysEntity( Mentity sysEntity ) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class clazz = Class.forName( sysEntity.getEntityClass() );
		gentityDao.setCatalog( sysEntity.getCatalog() );
		Object entity = gentityDao.getUniqueEntity( clazz );
		if( entity == null ){
			return null;
		}
		List<Mfield> dataFields = sysEntity.getFields();
	
		for (Mfield mfield : dataFields) {
			Method method = clazz.getMethod( CommonUtil.getGetterMethod(mfield.getName(), sysEntity.getCatalog() ) );
			Object value = method.invoke(entity);
			mfield.setValue(value);
		}
		String idName = sysEntity.getIdField().getName();
		Method method = clazz.getMethod( CommonUtil.getGetterMethod( idName,  sysEntity.getCatalog() ) );
		Object value = method.invoke(entity);
		sysEntity.getIdField().setValue( value );
		return sysEntity;
	}
	
	public List<Object> getEntityByClazzAndField(Class clazz, MyEntry ...entry ) throws ClassNotFoundException{
		return this.gentityDao.getEntityByClazzAndField(clazz, entry);
	}

	public List<Object> getEntityByClazzAndField(String clazzName, MyEntry ...entry ) throws ClassNotFoundException{
		return this.gentityDao.getEntityByClazzAndField(clazzName, entry);
	}

}
