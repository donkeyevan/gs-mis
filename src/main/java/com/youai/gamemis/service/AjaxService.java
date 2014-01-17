//package com.youai.gamemis.service;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.youai.gamemis.constants.MisConstant;
//import com.youai.gamemis.model.FieldValueType;
//import com.youai.gamemis.model.Mentity;
//import com.youai.gamemis.model.Mfield;
//import com.youai.gamemis.model.MyEntry;
//import com.youai.gamemis.model.OptionValue;
//import com.youai.gamemis.model.OptionValueAjax;
//import com.youai.gamemis.model.dao.GameEntityDAO;
//import com.youai.gs.dzm.model.ArmorType;
//import com.youai.gs.dzm.model.ArmsType;
//import com.youai.gs.dzm.model.City;
//import com.youai.gs.dzm.model.CollectType;
//import com.youai.gs.dzm.model.CrimeType;
//import com.youai.gs.dzm.model.DailyTaskItemTpl;
//import com.youai.gs.dzm.model.DebrisType;
//import com.youai.gs.dzm.model.DrugType;
//import com.youai.gs.dzm.model.FightPve;
//import com.youai.gs.dzm.model.FoodType;
//import com.youai.gs.dzm.model.FuncToolsType;
//import com.youai.gs.dzm.model.GoldToolType;
//import com.youai.gs.dzm.model.GoodsCategory;
//import com.youai.gs.dzm.model.MedicineType;
//import com.youai.gs.dzm.model.MountsType;
//import com.youai.gs.dzm.model.SpecialityType;
//import com.youai.gs.dzm.model.dao.DailyTaskItemDAO;
//import com.youai.gs.dzm.model.dao.DailyTaskItemTplDAO;
//
//@Service("ajaxService")
//public class AjaxService {
//	private final static Logger logger = Logger.getLogger(AjaxService.class);
//	private static Gson gson = new GsonBuilder().serializeNulls().create();
//
//	@Autowired
//	GameEntityDAO gentityDao;
//	@Autowired
//	DailyTaskItemTplDAO dailyTaskItemTplDAO;
//	@Autowired
//	DailyTaskItemDAO dailyTaskItemDAO;
//
//	private String[] cleanInputs = new String[] { "param1", "param2", "param3" };
//
//	public OptionValueAjax entrance(String entityStr, String fieldStr, Integer param) throws ClassNotFoundException,
//			InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException,
//			IllegalArgumentException, InvocationTargetException {
//		Class clazz = this.getClass();
//		Method m1 = clazz.getDeclaredMethod(entityStr + fieldStr.substring(0, 1).toUpperCase() + fieldStr.substring(1),
//				Integer.class, String.class);
//		OptionValueAjax ova = (OptionValueAjax) m1.invoke(this, param, fieldStr);
//		return ova;
//	}
//
//	/**
//	 * 此方法供list和edit用
//	 * @param mentity 
//	 */
//	public void addAjaxElement(Mentity mentity) throws SecurityException, IllegalArgumentException,
//			ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
//			InvocationTargetException {
//		if (mentity != null) {
//			List<Mfield> fields = mentity.getFields();
//			String entityStr = "";
//			String label = "";
//			for (Mfield field : fields) {
//
//				// 先找出链头，即ajax_input
//				if (field.getValueUitype() == FieldValueType.ajax_select.ordinal()) {
//					if("".equals(entityStr)){
//						entityStr = this.getClassName(mentity);
//					}
//					String relatedInput = field.getAjaxRelatedInput();
//
//					while (!relatedInput.equals("none")) {
//						// 取到ajax_input的related_input
//						Mfield relatedField = null;
//						for (Mfield mfield : fields) {
//							if (mfield.getName().equals(relatedInput)) {
//								relatedField = mfield;
//								break;
//							}
//						}
//						if(field.getName().equals("param1")){
//							System.out.println("fdsf");
//						}
//						OptionValueAjax ova = entrance(entityStr, field.getName(), (Integer) field.getValue());
//						if (ova != null && ova.getOptions() != null && ova.getOptions().size() > 0) {
//							List<OptionValue> options = ova.getOptions();
//							relatedField.setFieldRealValues(gson.toJson(options));
//							//relatedField.setOptionValues(options);
//							relatedField.setValueUitype(FieldValueType.ajax_select.ordinal() + 10);
//							if(!"".equals(label) && !"none".equals(label)){
//								relatedField.setNickname(label);
//							}
//							
//							// 查找related_input本身是否存在下级related_input
//							// OptionValue selectedOption = null;
//							// String nextRelatedInput = null;
//							
//							for (OptionValue optionValue : options) {
//								if (((Integer)optionValue.getKey()).intValue() == (Integer)relatedField.getValue()) {
//									// selectedOption = optionValue;
//									relatedInput = optionValue.getRelatedInput();
//									label = optionValue.getRelatedInputLabel();
//									break;
//								}
//							}
//							field = relatedField;
//						}
//					}
//				}
//			}
//		}
//	}
//	
//	public String getClassName(Mentity mentity){
//		String entityStr = mentity.getEntityClass();
//		entityStr = entityStr.substring(entityStr.lastIndexOf(".") + 1, entityStr.length());
//		entityStr = entityStr.substring(0, 1).toLowerCase() + entityStr.subSequence(1, entityStr.length());
//		return entityStr;
//	}
//
//	public OptionValueAjax dailyTaskItemType(Integer param, String inputName) throws ClassNotFoundException {
//		OptionValueAjax ova = new OptionValueAjax();
//		List<OptionValue> ovs = new ArrayList<OptionValue>();
//
//		List<Object> entities = gentityDao.getEntityByClazzAndField("com.youai.gs.dzm.model.DailyTaskItemTpl",
//				new MyEntry("type", param));
//		for (Object object : entities) {
//			DailyTaskItemTpl tpl = (DailyTaskItemTpl) object;
//			OptionValue ov = new OptionValue();
//			Integer idx = tpl.getIdx();
//			ov.setKey(idx);
//			ov.setValue(tpl.getName());
//			ov.setParentInput(inputName);
//			ov.setParentInputValue(String.valueOf(param));
//			switch (dailyTaskItemTypeAssi(idx)) {
//			case 1:
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				break;
//			case 2:
//				ov.setRelatedInput("param2");
//				ov.setRelatedInputLabel("木桩");
//				break;
//			case 3:
//				ov.setRelatedInput("param3");
//				ov.setRelatedInputLabel("物品类型");
//				break;
//			case 4:
//				ov.setRelatedInput("param2");
//				ov.setRelatedInputLabel("犯罪类型");
//				break;
//			case 5:
//				ov.setRelatedInput("param2");
//				ov.setRelatedInputLabel("城市");
//				break;
//			}
//			ovs.add(ov);
//		}
//		ova.setOptions(ovs);
//		ova.setPreCleanInputs(cleanInputs);
//		return ova;
//	}
//
//	public OptionValueAjax dailyTaskItemIdx(Integer param, String inputName) throws ClassNotFoundException {
//		OptionValueAjax ova = new OptionValueAjax();
//		List<OptionValue> ovs = new ArrayList<OptionValue>();
//		List<Object> entities = null;
//
//		switch (dailyTaskItemTypeAssi(param)) {
//		case 1:
//			for (Integer option : MisConstant.NUM_SELECT_OPTIONS) {
//				OptionValue ov = new OptionValue();
//				ov.setKey(option);
//				ov.setValue(String.valueOf(option));
//				ov.setRelatedInput("none");
//				ov.setRelatedInputLabel("none");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			// ovs.add(new OptionValue("relatedInput", "\"none\""));
//			break;
//		case 2: // 打木桩
//			entities = gentityDao.getEntityByClazzAndField("com.youai.gs.dzm.model.FightPve");
//			for (Object object : entities) {
//				FightPve tpl = (FightPve) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			// ovs.add(new OptionValue("relatedInput", "\"param2\""));
//			break;
//		case 3: // 购买物品
//			entities = gentityDao.getEntityByClazzAndField("com.youai.gs.dzm.model.GoodsCategory");
//			for (Object object : entities) {
//				GoodsCategory tpl = (GoodsCategory) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param2");
//				ov.setRelatedInputLabel("物品");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			// ovs.add(new OptionValue("relatedInput", "\"param2\""));
//			break;
//		case 4: // 历练
//			entities = gentityDao.getEntityByClazzAndField("com.youai.gs.dzm.model.CrimeType");
//			for (Object object : entities) {
//				CrimeType tpl = (CrimeType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			// ovs.add(new OptionValue("relatedInput", "\"param2\""));
//			break;
//		case 5: // 飞到某城市
//			entities = gentityDao.getEntityByClazzAndField("com.youai.gs.dzm.model.City");
//			for (Object object : entities) {
//				City tpl = (City) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			// ovs.add(new OptionValue("relatedInput", "\"param2\""));
//			break;
//		}
//		ova.setOptions(ovs);
//		ova.setPreCleanInputs(cleanInputs);
//		return ova;
//	}
//
//	public OptionValueAjax dailyTaskItemParam2(Integer param, String inputName) throws ClassNotFoundException {
//		OptionValueAjax ova = new OptionValueAjax();
//		List<OptionValue> ovs = new ArrayList<OptionValue>();
//
//		for (Integer option : MisConstant.NUM_SELECT_OPTIONS) {
//			OptionValue ov = new OptionValue();
//			ov.setKey(option);
//			ov.setValue(String.valueOf(option));
//			ov.setRelatedInput("none");
//			ov.setRelatedInputLabel("none");
//			ov.setParentInput(inputName);
//			ov.setParentInputValue(String.valueOf(param));
//			ovs.add(ov);
//		}
//		// ovs.add(new OptionValue("relatedInput", "\"none\""));
//		ova.setOptions(ovs);
//		return ova;
//	}
//
//	public OptionValueAjax goodsFallingCategory(Integer param, String inputName) throws ClassNotFoundException {
//		OptionValueAjax ova = new OptionValueAjax();
//		List<OptionValue> ovs = new ArrayList<OptionValue>();
//		List<Object> entities = null;
//		switch (param) {
//		case 0:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				ArmsType tpl = (ArmsType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("none");
//				ov.setRelatedInputLabel("none");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 1:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				ArmorType tpl = (ArmorType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("none");
//				ov.setRelatedInputLabel("none");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 2:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				DrugType tpl = (DrugType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("none");
//				ov.setRelatedInputLabel("none");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 3:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				MedicineType tpl = (MedicineType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("none");
//				ov.setRelatedInputLabel("none");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 4:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				SpecialityType tpl = (SpecialityType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("none");
//				ov.setRelatedInputLabel("none");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 5:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				FuncToolsType tpl = (FuncToolsType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("none");
//				ov.setRelatedInputLabel("none");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 6:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				FoodType tpl = (FoodType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("none");
//				ov.setRelatedInputLabel("none");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 7:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				CollectType tpl = (CollectType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("none");
//				ov.setRelatedInputLabel("none");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 8:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				DebrisType tpl = (DebrisType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("none");
//				ov.setRelatedInputLabel("none");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 9:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				GoldToolType tpl = (GoldToolType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("none");
//				ov.setRelatedInputLabel("none");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 10:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				MountsType tpl = (MountsType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("none");
//				ov.setRelatedInputLabel("none");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		}
//		ova.setOptions(ovs);
//		return ova;
//	}
//
//	public OptionValueAjax dailyTaskItemParam3(Integer param, String inputName) throws ClassNotFoundException {
//		OptionValueAjax ova = new OptionValueAjax();
//		List<OptionValue> ovs = new ArrayList<OptionValue>();
//		List<Object> entities = null;
//		switch (param) {
//		case 0:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				ArmsType tpl = (ArmsType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 1:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				ArmorType tpl = (ArmorType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 2:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				DrugType tpl = (DrugType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 3:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				MedicineType tpl = (MedicineType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 4:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				SpecialityType tpl = (SpecialityType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 5:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				FuncToolsType tpl = (FuncToolsType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 6:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				FoodType tpl = (FoodType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 7:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				CollectType tpl = (CollectType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 8:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				DebrisType tpl = (DebrisType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 9:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				GoldToolType tpl = (GoldToolType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		case 10:
//			entities = gentityDao.getEntityByClazzAndField(dailyTaskItemParam3Assi(param));
//			for (Object object : entities) {
//				MountsType tpl = (MountsType) object;
//				OptionValue ov = new OptionValue();
//				ov.setKey(tpl.getIdx());
//				ov.setValue(tpl.getName());
//				ov.setRelatedInput("param1");
//				ov.setRelatedInputLabel("次数");
//				ov.setParentInput(inputName);
//				ov.setParentInputValue(String.valueOf(param));
//				ovs.add(ov);
//			}
//			break;
//		}
//		ova.setOptions(ovs);
//		return ova;
//	}
//
//	public Integer dailyTaskItemTypeAssi(Integer value) {
//		Integer category = -1;
//		switch (value) {
//		case 1: // 俱乐部战斗
//		case 2: // 打残
//		case 3: // 抢钱
//		case 4:
//		case 5:
//		case 8:
//		case 9:
//		case 11:
//		case 13:
//		case 14:
//		case 15:
//		case 16:
//		case 17:
//		case 18:
//		case 20:
//		case 21:
//		case 23:
//		case 24:
//			category = 1;
//			break;
//		case 6: // 打木桩
//			category = 2;
//			break;
//		case 7: // 购买物品
//		case 19: // 赠送物品
//		case 22: // 黑市兑换
//		case 25: // 卖东西给npc
//			category = 3;
//			break;
//		case 10: // 历练
//			category = 4;
//			break;
//		case 12: // 飞到某城市
//			category = 5;
//			break;
//		}
//		return category;
//	}
//
//	public String dailyTaskItemParam3Assi(Integer value) {
//		String category = "";
//		switch (value) {
//		case 0:
//			category = "com.youai.gs.dzm.model.ArmsType";
//			break;
//		case 1:
//			category = "com.youai.gs.dzm.model.ArmorType";
//			break;
//		case 2:
//			category = "com.youai.gs.dzm.model.DrugType";
//			break;
//		case 3:
//			category = "com.youai.gs.dzm.model.MedicineType";
//			break;
//		case 4:
//			category = "com.youai.gs.dzm.model.SpecialityType";
//			break;
//		case 5:
//			category = "com.youai.gs.dzm.model.FuncToolsType";
//			break;
//		case 6:
//			category = "com.youai.gs.dzm.model.FoodType";
//			break;
//		case 7:
//			category = "com.youai.gs.dzm.model.CollectType";
//			break;
//		case 8:
//			category = "com.youai.gs.dzm.model.DebrisType";
//			break;
//		case 9:
//			category = "com.youai.gs.dzm.model.GoldToolType";
//			break;
//		case 10:
//			category = "com.youai.gs.dzm.model.MountsType";
//			break;
//		}
//		return category;
//	}
//
//}
