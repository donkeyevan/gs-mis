//package com.youai.gamemis.options;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.hibernate.SessionFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.youai.gamemis.model.OptionValue;
//import com.youai.gs.dzm.model.Aerolite;
//import com.youai.gs.dzm.model.ArmorType;
//import com.youai.gs.dzm.model.ArmsType;
//import com.youai.gs.dzm.model.CollectType;
//import com.youai.gs.dzm.model.DebrisType;
//import com.youai.gs.dzm.model.DrugType;
//import com.youai.gs.dzm.model.FoodType;
//import com.youai.gs.dzm.model.FuncToolsType;
//import com.youai.gs.dzm.model.GiftPackType;
//import com.youai.gs.dzm.model.GoldToolType;
//import com.youai.gs.dzm.model.MedicineType;
//import com.youai.gs.dzm.model.MountsType;
//import com.youai.gs.dzm.model.SpecialityType;
//@Transactional
//@Component(value="GoodsTypeOptionsImpl")
//public class GoodsTypeOptionsImpl implements CustomSelectOptions{
//	@Resource(name="sessionFactory")
//	private  SessionFactory sessionFactory ;
//	 private static enum GOODS_CATEGORY{
//	    	/**
//	    	 * 武器 0
//	    	 */
//	    	ARMS,
//	    	/**
//	    	 * 防具 1
//	    	 */
//	    	ARMOR,
//	    	/**
//	    	 * 毒品 2
//	    	 */
//	    	DRUG,
//	    	/**
//	    	 * 药品 3
//	    	 */
//	    	MEDICINE,
//	    	/**
//	    	 * 特产 4
//	    	 */
//	    	SPEICIALTY,
//	    	/**
//	    	 * 功能道具 5
//	    	 */
//	    	FUNC_TOOLS,
//	    	/**
//	    	 * 食物 6
//	    	 */
//	    	FOOD,
//	    	/**
//	    	 * 收集品 7
//	    	 */
//	    	COLLECT,
//	    	/**
//	    	 * 杂物 8
//	    	 */
//	    	DEBRIS,
//	    	/**
//	    	 * 金币道具 9
//	    	 */
//	    	GOLD_TOOLS,
//	    	/**
//	    	 * 座骑 10
//	    	 */
//	    	MOUNTS,
//	    	/**
//	    	 * 礼包 11
//	    	 */
//	    	GIFT_PACK,
//	    	/**
//	    	 * 强化石 12
//	    	 */
//	    	AEROLITE,
//	    }
//	 public GoodsTypeOptionsImpl(){
//		 
//	 }
//	public List<OptionValue> getOptions() {
//		List<OptionValue> options = new ArrayList<OptionValue>();
//		List<ArmsType> armsTypes = sessionFactory.getCurrentSession().createCriteria( ArmsType.class ).list();
//		
//		for(ArmsType item : armsTypes ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue( "武器" );
//			optionValue.setParentKey( GOODS_CATEGORY.ARMS.ordinal() );
//			options.add( optionValue );
//			if(item.getStrengthenLevel() > 0){
//				for (int i = 1; i <= item.getStrengthenLevel(); i++) {
//					OptionValue optionValueStre = new OptionValue();
//					optionValueStre.setKey( item.getIdx() * 100 + i );
//					optionValueStre.setValue( item.getName() + i + "级" );
//					optionValueStre.setParentValue( "武器" );
//					optionValueStre.setParentKey( GOODS_CATEGORY.ARMS.ordinal() );
//					options.add( optionValueStre );
//				}
//			}
//		}
//		List<ArmorType> armorTypes = sessionFactory.getCurrentSession().createCriteria( ArmorType.class ).list();
//		for(ArmorType item : armorTypes ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setParentKey(GOODS_CATEGORY.ARMOR.ordinal());
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue( "防具" );
//			options.add( optionValue );
//			if(item.getStrengthenLevel() > 0){
//				for (int i = 1; i <= item.getStrengthenLevel(); i++) {
//					OptionValue optionValueStre = new OptionValue();
//					optionValueStre.setKey( item.getIdx() * 100 + i );
//					optionValueStre.setValue( item.getName() + i + "级" );
//					optionValueStre.setParentValue( "防具" );
//					optionValueStre.setParentKey( GOODS_CATEGORY.ARMOR.ordinal() );
//					options.add( optionValueStre );
//				}
//			}
//		}
//		List<DrugType> drugTypes = sessionFactory.getCurrentSession().createCriteria( DrugType.class ).list();
//		for(DrugType item : drugTypes ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setParentKey(GOODS_CATEGORY.DRUG.ordinal());
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue( "毒品" );
//			options.add( optionValue );
//		}
//		List<MedicineType> medicineTypes = sessionFactory.getCurrentSession().createCriteria( MedicineType.class ).list();
//		for(MedicineType item : medicineTypes ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setParentKey( GOODS_CATEGORY.MEDICINE.ordinal() );
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue( "药品" );
//			options.add( optionValue );
//		}
//		List<SpecialityType> specialityTypes = sessionFactory.getCurrentSession().createCriteria( SpecialityType.class ).list();
//		for(SpecialityType item : specialityTypes ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setParentKey( GOODS_CATEGORY.SPEICIALTY.ordinal() );
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue( "特产" );
//			options.add( optionValue );
//		}
//		List<FuncToolsType> funcToolsTypes = sessionFactory.getCurrentSession().createCriteria( FuncToolsType.class ).list();
//		for(FuncToolsType item : funcToolsTypes ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setParentKey( GOODS_CATEGORY.FUNC_TOOLS.ordinal() );
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue( "功能道具" );
//			options.add( optionValue );
//		}
//		List<FoodType> foodTypes = sessionFactory.getCurrentSession().createCriteria( FoodType.class ).list();
//		for(FoodType item : foodTypes ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setParentKey( GOODS_CATEGORY.FOOD.ordinal() );
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue( "食物" );
//			options.add( optionValue );
//		}
//		List<CollectType> collectTypes = sessionFactory.getCurrentSession().createCriteria( CollectType.class ).list();
//		for(CollectType item : collectTypes ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setParentKey( GOODS_CATEGORY.COLLECT.ordinal() );
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue( "收集品" );
//			options.add( optionValue );
//		}
//		List<DebrisType> debrisTypes = sessionFactory.getCurrentSession().createCriteria( DebrisType.class ).list();
//		for(DebrisType item : debrisTypes ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setParentKey( GOODS_CATEGORY.DEBRIS.ordinal() );
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue( "杂物" );
//			options.add( optionValue );
//		}
//		List<GoldToolType> goldToolsTypes = sessionFactory.getCurrentSession().createCriteria( GoldToolType.class ).list();
//		for(GoldToolType item : goldToolsTypes ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setParentKey( GOODS_CATEGORY.GOLD_TOOLS.ordinal() );
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue( "金币道具" );
//			options.add( optionValue );
//		}
//		List<MountsType> mountsTypes = sessionFactory.getCurrentSession().createCriteria( MountsType.class ).list();
//		for(MountsType item: mountsTypes ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setParentKey( GOODS_CATEGORY.MOUNTS.ordinal() );
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue("座骑");
//			options.add( optionValue );
//		}
//		List<GiftPackType> giftPackTypes = sessionFactory.getCurrentSession().createCriteria( GiftPackType.class ).list();
//		for(GiftPackType item: giftPackTypes ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setParentKey( GOODS_CATEGORY.GIFT_PACK.ordinal() );
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue("礼包");
//			options.add( optionValue );
//		}
//		List<Aerolite> aerolites = sessionFactory.getCurrentSession().createCriteria( Aerolite.class ).list();
//		for(Aerolite item: aerolites ){
//			OptionValue optionValue = new OptionValue();
//			optionValue.setKey( item.getIdx() );
//			optionValue.setParentKey( GOODS_CATEGORY.AEROLITE.ordinal() );
//			optionValue.setValue( item.getName() );
//			optionValue.setParentValue("强化石");
//			options.add( optionValue );
//		}
//		OptionValue optionValue = new OptionValue();
//		optionValue.setKey( 0);
//		optionValue.setParentKey( -1 );
//		optionValue.setValue( "无" );
//		optionValue.setParentValue("无");
//		options.add( optionValue );
//		return options;
//	}
//
//}
