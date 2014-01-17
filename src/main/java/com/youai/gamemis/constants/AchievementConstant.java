package com.youai.gamemis.constants;
public class AchievementConstant {
	
	/**
	 * 攻击成就枚举
	 * @author mini5
	 *
	 */
	public static enum Entrys{
		
		/*
		 * 进攻
		 */
		ATTACK_FIGHT( 201, new int[]{10,100,1000,5000,10000},false, "发动进攻次数"),
		ATTACK_WIN( 202, new int[]{10,100,1000,5000,10000},false, "获得胜利场数"),
		ATTACK_WIN_PASSIVE(203, new int[]{10,100,1000,5000,10000},false, "被动胜利次数"),
		ATTACK_MELEE_WEAPONS(204, new int[]{500,5000},false, "使用近战武器战斗次数"),
		ATTACK_PISTOL(205, new int[]{500,5000},false, "使用手枪战斗次数"),
	    ATTACK_SHOTGUN(206, new int[]{500,5000},false, "使用散弹枪战斗次数"),
	    ATTACK_MICRO_SMG(207, new int[]{500,5000},false, "使用微冲枪战斗次数"),
	    ATTACK_AUTOMATIC_RIFLE(208, new int[]{500,5000},false, "使用自动步枪战斗次数"),
	    ATTACK_SNIPER_RIFLE(209, new int[]{500,5000},false, "使用狙击步枪战斗次数"),
	    ATTACK_MACHINE_GUN(210, new int[]{500,5000},false, "使用机枪战斗次数"),
	    ATTACK_HEAVY_WEAPONS(211 , new int[]{500,5000},false, "使用重炮武器战斗次数"),
	    ATTACK_CONTINUOUS_WIN(212, new int[]{10,50,100},true, "连续胜利次数"),
	    ATTACK_CONTINUOUS_AGAINST(213, new int[]{10,50,100},true, "连续抵抗胜利次数"),
	    ATTACK_LEAVE(214, new int[]{10,100,1000,10000},false, "走人次数"),
	    ATTACK_ROB_MONEY(215, new int[]{10,100,1000,10000},false, "抢钱次数"),
	    ATTACK_HOSPITALIZE(216, new int[]{10,100,1000,10000},false, "打残次数"),
	    ATTACK_DEADLY_STRIKE(217, new int[]{10,100,1000},false, "打出致命一击次数"),
		
		/*
		 * 犯罪
		 */
		CRIME_SEARCH(251, new int[]{50,500,5000,50000},false, "成功搜索次数"),
		CRIME_CONTRABAND(252, new int[]{50,500,5000,50000},false, "进行非法物品买卖次数"),
		CRIME_STEAL_EVENT(253, new int[]{50,500,5000,50000},false, "进行偷窃次数"),
		CRIME_STEAL_GOODS(254, new int[]{50,500,5000,50000},false, "通过偷窃获得杂物个数"),
		CRIME_DRUG_TRADE(255, new int[]{50,500,5000,50000},false, "成功进行运送货物次数"),
		CRIME_HIGH_TECH(256, new int[]{50,500,5000,50000},false, "成功实施计算机历练次数"),
		CRIME_MURDER(257, new int[]{50,500,5000,50000},false, "成功进行暗访次数"),
		CRIME_SECURITY(258, new int[]{50,500,5000,50000},false, "进行山寨次数"),
		CRIME_FRAUD(259, new int[]{50,500,5000,50000},false, "成功实施诈骗次数"),
		CRIME_KUNGFU(260, new int[]{50,500,5000,50000},false, "进行一展拳脚次数"),
		
		/*
		 * 药品
		 */
		REMEDY_MEDICINE(301, new int[]{50,500,5000},false, "使用药品次数"),
		REMEDY_DRUG(302, new int[]{50,500,5000},false, "使用毒品次数"),
		
		/*
		 * 工作
		 */
		WORK_DURATION(351, new int[]{10,100,500,1000},true, "连续工作天数"),
		WORK_LENGTH_SERVICE(352, new int[]{10,100,500},true, "在同一家公司待满天数"),
		WORK_REPLACE(353, new int[]{10,100,500},false, "加入不同公司次数"),
		
		/*
		 * 帮派
		 */
		GANG_LENGTH_SERVICE(401, new int[]{10,100,500,1000},true, "在帮会呆满天数"),
		GANG_CONTRIBUTION(402, new int[]{100,1000,10000,100000},false, "获得帮贡点数"),
		GANG_WIN_TIMES(403, new int[]{10,100,1000},false, "帮战胜利次数"),
		GANG_CONTINUOUS_WIN(404, new int[]{10,20,40,70,100,200,400,700,1000,1500,2000,2500,3000,4000,5000,6000,7000,8000,9000,10000},false, "帮战连击特殊点"),
		GANG_CONTINUOUS_WIN1(405, new int[]{10},true, "帮战连击特殊点10"),
		GANG_CONTINUOUS_WIN2(406, new int[]{20},true, "帮战连击特殊点20"),
		GANG_CONTINUOUS_WIN3(407, new int[]{40},true, "帮战连击特殊点40"),
		GANG_CONTINUOUS_WIN4(408, new int[]{70},true, "帮战连击特殊点70"),
		GANG_CONTINUOUS_WIN5(409, new int[]{100},true, "帮战连击特殊点100"),
		GANG_CONTINUOUS_WIN6(410, new int[]{200},true, "帮战连击特殊点200"),
		GANG_CONTINUOUS_WIN7(411, new int[]{400},true, "帮战连击特殊点400"),
		GANG_CONTINUOUS_WIN8(412, new int[]{700},true, "帮战连击特殊点700"),
		GANG_CONTINUOUS_WIN9(413, new int[]{1000},true, "帮战连击特殊点1000"),
		GANG_CONTINUOUS_WIN10(414, new int[]{1500},true, "帮战连击特殊点1500"),
		GANG_CONTINUOUS_WIN11(415, new int[]{2000},true, "帮战连击特殊点2000"),
		GANG_CONTINUOUS_WIN12(416, new int[]{2500},true, "帮战连击特殊点2500"),
		GANG_CONTINUOUS_WIN13(417, new int[]{3000},true, "帮战连击特殊点3000"),
		GANG_CONTINUOUS_WIN14(418, new int[]{4000},true, "帮战连击特殊点4000"),
		GANG_CONTINUOUS_WIN15(419, new int[]{5000},true, "帮战连击特殊点5000"),
		GANG_CONTINUOUS_WIN16(420, new int[]{6000},true, "帮战连击特殊点6000"),
		GANG_CONTINUOUS_WIN17(421, new int[]{7000},true, "帮战连击特殊点7000"),
		GANG_CONTINUOUS_WIN18(422, new int[]{8000},true, "帮战连击特殊点8000"),
		GANG_CONTINUOUS_WIN19(423, new int[]{9000},true, "帮战连击特殊点9000"),
		GANG_CONTINUOUS_WIN20(424, new int[]{10000},true, "帮战连击特殊点10000"),
		
		/*
		 * 健身房
		 */
		GYM_TOTAL_CONSUME(451, new int[]{5000,50000,500000,5000000},false, "在健身上总投入超过体力"),
		GYM_STRENGTH(452, new int[]{5000,10000,50000,500000},false, "在力量上总投入超过体力"),
		GYM_ENDURANCE(453, new int[]{5000,10000,50000,500000},false, "在耐力上总投入超过体力"),
		GYM_SPEED(454, new int[]{5000,10000,50000,500000},false, "在速度上总投入超过体力"),
		GYM_AGILE(455, new int[]{5000,10000,50000,500000},false, "在敏捷上总投入超过体力"),
		GYM_PRIMARY(456, new int[]{5000,50000,500000},false, "在初级健身房投入体力"),
		GYM_ADVANCED(457, new int[]{5000,50000,500000},false, "在高级健身房投入体力"),
		
		/*
		 * 监狱
		 */
		JAIL_BAILMENT(501, new int[]{50,500,5000},false, "保释玩家出狱个数"),
		JAIL_ESCAPE_PARTNER(502, new int[]{50,500,5000},false, "成功协助玩家越狱个数"),	
		JAIL_GAOL(503, new int[]{50,500,5000},false, "入狱次数"),
		JAIL_ESCAPE(504, new int[]{50,500,5000},false, "成功越狱次数"),
		
		/*
		 * 医院
		 */
		HOSPITAL_INPATIENT(551, new int[]{50,500,5000,10000},false, "入院次数"),
		HOSPITAL_MEDICINE(552, new int[]{50,500,5000,10000},false, "通过吃药提前出院次数"),
		
		/*
		 * 属性
		 */
		PROPERTY_SUM(601, new int[]{50,500,1000,10000,100000},false, "总属性数值"),
		PROPERTY_HAPPY(602, new int[]{300,1000,3000,5000},false, "快乐上限数值"),
		
		/*
		 * 旅行
		 */
		TRAVEL_FLIGHT(651,new int[]{20,500,1000},false, "乘坐航班次数"),
		TRAVEL_IMPRESSION(652,new int[]{3,7,11},false, "城市好感度满数目"),
		
		/*
		 * 交易
		 */
		DEAL_SHOP(701, new int[]{50,500,5000,10000},false, "进行商店买卖次数"),
		DEAL_BLACK_MARKET(702, new int[]{10,100,1000,5000},false, "进行黑市交易次数"),
		
		/*
		 * 悬赏
		 */
		WANTED_OWNER(751, new int[]{10,100,1000,5000},false, "登悬赏启示次数"),

		WANTED_EXECUTOR(752, new int[]{10,100,1000,5000},false, "完成悬赏任务次数"),
		WANTED_TARGET(753, new int[]{10,100,1000,5000},false, "被悬赏次数"),

		
		/*
		 * 财富
		 */
		WEALTH_MONEY(801, new int[]{10000,100000,1000000,10000000,100000000},false, "金钱数量"),
		WEALTH_GOLD(802, new int[]{100,500,1000,5000,10000,50000,100000},false, "金条数量"),
		WEALTH_ESTATE(803, new int[]{100000,1000000,10000000,100000000},false, "房产总价值"),
		
		/*
		 * 等级
		 */
		LEVEL_ROLE(851,new int[]{5,10,15,20,25,30,40,50,75,100,125,150,175,200,225,250,275,300,325,350},false, "角色等级"),
		
		/*
		 * 其他
		 */
		OTHERS_LOGIN(901, new int[]{10, 100, 500},false, "登陆奖励"),
		OTHERS_RANDOM_AWARD(902,new int[]{10,100,1000},false, "随机奖励"),
		OTHERS_VIP(903,new int[]{1},false, "获得VIP"),
		OTHERS_INTRO_LEVEL(904,new int[]{1,10,20,50,100},false, "发展下线"),
		OTHERS_INTRO_RECHARGE(905,new int[]{1000,5000,10000},false, "下线充值总额"),
		OTHERS_GIFT_MONEY(906,new int[]{100,500,1000,5000},false, "送钱次数"),
		OTHERS_GIFT_GOODS(907,new int[]{100,500,1000,5000},false, "送礼次数"),
		//赌博
		OTHERS_GAMBLING(908,new int[]{100,500,1000,5000},false, "赌博次数"),
		//老虎机
		OTHERS_GAMBLING_SLOT(909,new int[]{100,1000,10000},false, "老虎机次数"),
		OTHERS_GAMBLING_SLOT_AGAIN(910,new int[]{100},false, "老虎机100次再来一次"),
		OTHERS_GAMBLING_SLOT_HIGHEST_RATE(911,new int[]{1},false, "老虎机1次最高赔率"),
		//21点
		OTHER_GAMBLING_21(912, new int[]{100, 1000, 10000}, false, "玩21点次数"),
		OTHER_GAMBLING_21_CONTINUOUS_WIN(913, new int[]{10, 50, 100}, true, "21点连续获胜次数"),
		OTHER_GAMBLING_21_BUST(914, new int[]{1}, false, "21点拿到9张牌未bust"),
		//玩红黑阵次数
		RED_BLACK_TIMES(915, new int[]{5, 50, 500}, false, "玩红黑阵次数"),
		//红黑阵赢的次数
		RED_BLACK_WIN(916, new int[]{1, 10, 100}, false, "红黑阵赢的次数"),
		
		/*
		 * 婚姻
		 */
		MARITAL_PROPOSE_TIMES(951, new int[]{10, 100, 500, 1000},false, "求婚次数"),
		MARITAL_DRUATION(952,new int[]{30,200,500},false, "和一个玩家结婚天数"),
		
		/*
		 * 活动
		 */
		ACTIVITY_RUBCARDS(1001,new int[]{1},false, ""),
		
		/*
		 * 任务
		 */
		TASK_COMPLETE(1051, new int[]{10, 100, 500},false, "完成每日任务次数"),
		TASK_CONTINUOUS_COMPLETE(1052, new int[]{10, 30, 50, 100}, true, "连续完成每日任务次数"),
		
		/**
		 * 成就2.0
		 */
		//1%血量胜利
		NARROW_VICTORY(1101, new int[]{1},false, "1%血量胜利"),
		//城战个人占领值
		CITYWAR_PERSONAL_SCORE(1102, new int[]{ 100, 500},true, "城战个人占领值"),
		//消耗喇叭
		CONSUME_HORN(1103, new int[]{100, 1000, 10000},false, "消耗喇叭"),
		//房产数
		ESTATE_SUM(1104, new int[]{5, 20, 50}, true, "房产数"),
		//房产数租赁次数
		ESTATE_RENT(1105, new int[]{5, 50, 100, 500}, false, "房产数租赁次数"),
		//受伤连续胜利
		INJURED_CONTINUOUS_WIN(1106, new int[]{5}, false, "受伤连续胜利"),
		//刊登广告次数
		ADVERTISE(1107, new int[]{10, 100, 1000}, false, "刊登广告次数"),
		//打残高帅富
		CRIPPLE_POWERFUL_NPC(1108, new int[]{1}, false, "打残高帅富"),
		//离婚次数
		DIVORCE(1109, new int[]{10, 50, 100, 500}, false, "离婚次数"),
		//与10名不同玩家结婚
		CROWD_OF_WIVES(1110, new int[]{10}, true, "与10名不同玩家结婚"),
		//vip的持续时间 
		VIP_DURATION(1111, new int[]{5, 50, 100, 365}, false, "vip的持续时间"),
		//窝棚居住
		//ESTATE_SHACK_DURATION(1112, new int[]{1,2,3,5, 50, 100}, false, ""),
		//地下室居住
		ESTATE_BASEMENT_DURATION(1113, new int[]{5, 50, 100}, false, "地下室居住"),
		//廉租房居住
		ESTATE_LOW_COST_DURATION(1114, new int[]{5, 50, 100}, false, "廉租房居住"),
		//半独立屋居住
		ESTATE_SEMI_DURATION(1115, new int[]{5, 50, 100}, false, "半独立屋居住"),
		//木屋居住
		ESTATE_HUT_DURATION(1116, new int[]{5, 50, 100}, false, "木屋居住"),
		//独立屋居住
		ESTATE_HOUSE_DURATION(1117, new int[]{5, 50, 100}, false, "独立屋居住"),
		//豪宅居住
		ESTATE_MANSION_DURATION(1118, new int[]{5, 50, 100}, false, "豪宅居住"),
		//牧场居住
		ESTATE_RANCH_DURATION(1119, new int[]{5, 50, 100}, false, "牧场居住"),
		//海滨别墅居住
		ESTATE_VILLA_DURATION(1120, new int[]{5, 50, 100}, false, "海滨别墅居住"),
		//四合院居住
		ESTATE_COURTYARD_DURATION(1121, new int[]{5, 50, 100}, false, "四合院居住"),
		//大厦居住
		ESTATE_BUILDING_DURATION(1122, new int[]{5, 50, 100}, false, "大厦居住"),
		//宫殿居住
		ESTATE_PALACE_DURATION(1123, new int[]{5, 50, 100}, false, "宫殿居住"),
		//城堡居住
		ESTATE_CASTLE_DURATION(1124, new int[]{5, 50, 100}, false, "城堡居住"),
		//小岛居住
		ESTATE_ISLAND_DURATION(1125, new int[]{5, 50, 100}, false, "小岛居住"),

		//道德值降为0
		MORAL_LOSS(1126, new int[]{0}, false, "道德值降为0"),
		//进行任一次装修
		ESTATE_DECORATION(1127, new int[]{1}, false, "进行任1次装修"),
		//购买任意仆人
		ESTATE_MAID(1128, new int[]{1}, false, "购买任意仆人"),
		//购买停机坪
		ESTATE_DECORATION_APRON(1129, new int[]{1}, false, "购买停机坪"),
		//雇佣一名保镖
		HIRE_A_GUARD(1130, new int[]{1}, false, "雇佣1名保镖"),
		//雇佣一名保镖连续7天
		HIRE_A_GUARD_SEVEN_DAYS(1131, new int[]{7}, false, "雇佣一名保镖连续7天"),
		//雇佣10名不同的保镖
		HIRE_TEN_DIFF_GUARDS(1132, new int[]{10}, false, "雇佣10名不同的保镖"),
		//被保镖保护十次
		PROTECTED_TEN_TIMES(1133, new int[]{10}, false, "被保镖保护10次"),
		//给一个玩家当保镖
		HIRED_AS_A_GUARD(1134, new int[]{1}, false, "给一个玩家当保镖"),
		//给一名玩家当保镖连续7天
		HIRED_AS_A_GUARD_SEVEN_DAYS(1135, new int[]{7}, false, "给一名玩家当保镖连续7天"),
		//给10名不同玩家当保镖
		HIRED_AS_TEN_DIFF_GUARDS(1136, new int[]{10}, false, "给10名不同玩家当保镖"),
		//保护雇主十次
		PROTECT_TEN_TIMES(1137, new int[]{10}, false, "保护雇主10次"),

		
		/*
		 * 学校相关
		 */
		//法学学士
		BACHELOR_LAWS(1138, new int[]{1}, false, "法学学士"),
		//军事学士
		BACHELOR_MILITARY(1139, new int[]{1}, false, "军事学士"),
		//文学学士
		BACHELOR_ART(1140, new int[]{1}, false, "文学学士"),
		//工学学士
		BACHELOR_ENGINEERING(1141, new int[]{1}, false, "工学学士"),
		//初级运动员
		ATHLETE_JUNIOR(1142, new int[]{1}, false, "初级运动员"),
		//法学硕士
		MASTER_LAWS(1143, new int[]{1}, false, "法学硕士"),
		//军事学硕士
		MASTER_MILITARY(1144, new int[]{1}, false, "军事学硕士"),
		//文学硕士
		MASTER_ART(1145, new int[]{1}, false, "文学硕士"),
		//工学硕士
		MASTER_ENGINEERING(1146, new int[]{1}, false, "工学硕士"),
		//中级运动员
		ATHLETE_MIDDLE(1147, new int[]{1}, false, "中级运动员"),
		//法学博士
		DOCTOR_LAWS(1148, new int[]{1}, false, "法学博士"),
		//军事学博士
		DOCTOR_MILITARY(1149, new int[]{1}, false, "军事学博士"),
		//文学博士
		DOCTOR_ART(1150, new int[]{1}, false, "文学博士"),
		//工学博士
		DOCTOR_ENGINEERING(1151, new int[]{1}, false, "工学博士"),
		//高级运动员
		ATHLETE_SENIOR(1152, new int[]{1}, false, "高级运动员"),
		//初级学者
		SCHOLAR_JUNIOR(1153, new int[]{1}, false, "初级学者"),
		//中级学者
		SCHOLAR_MIDDLE(1154, new int[]{1}, false, "中级学者"),
		//高级学者
		SCHOLAR_SENIOR(1155, new int[]{1}, false, "高级学者"),

		/*
		 * 世界boss成就
		 */
		//伤害量第一
		BOSS_CHAMPION(1156, new int[]{1}, false, "boss战伤害量第一"),
		//第一击
		BOSS_FIRST_BLOOD(1157, new int[]{1}, false, "boss战第一击"),
		//最后一击
		BOSS_LAST_BLOOD(1158, new int[]{1}, false, "boss战最后一击"),
		//100连击
		BOSS_HUNDRED(1159, new int[]{1}, false, "boss战100连击"),
		//1000连击
		BOSS_THOUSAND(1160, new int[]{1}, false, "boss战1000连击"),
		
		
		/*
		 * 情人节活动
		 */
		//女神
		GODDESS(1161, new int[]{1}, false, "情人节女神"),
		//高富帅
		PERFECT_BOY(1162, new int[]{1000}, true, "情人节高富帅"),

		/*
		 * 师徒
		 */
		//出师个数
		APPR_SUM(1163, new int[]{1, 50, 100}, true, "出师个数"),

		/*
		 * 完成副本的次数
		 */
		DUNGEON_COMPLETE(1164, new int[]{100, 500, 1000}, true, "完成副本的次数"),

		/*
		 * 天梯站冠军
		 */
		LADDER_CHAMPION(1165, new int[]{1}, true, "天梯战冠军"),
		
		/*
		 * 强化的成就
		 */
		STRENGTHEN_PERFECT_WEAPON(1166, new int[]{1}, true, "强化12级的武器"),
		STRENGTHEN_FAILURE(1167, new int[]{100}, true, "强化失败次数"),
		
		/*
		 * 橱窗个数
		 */
		SHOW_WINDOW_AMOUNT(1168, new int[]{32, 64, 128}, true, "橱窗个数"),

		/*
		 * 拍卖行
		 */
		//买成功的次数
		AUCTION_BUY_TIMES(1169, new int[]{10, 100, 500}, true, "拍卖行买成功的次数"),
		//卖成功的次数
		AUCTION_SELL_TIMES(1170, new int[]{10, 100, 500}, true, "拍卖行卖成功的次数"),
		;
		
	    private int key;
		private int value[];
		
		/*
		 * 累加或者覆盖false：累加，true：覆盖
		 */
		private boolean continuousFlag;
		private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean isContinuousFlag() {
			return continuousFlag;
		}
		public void setContinuousFlag(boolean continuousFlag) {
			this.continuousFlag = continuousFlag;
		}
		public int getKey() {
			return key;
		}
		public void setKey(int key) {
			this.key = key;
		}
		public int[] getValue() {
			return value;
		}
		public void setValue(int[] value) {
			this.value = value;
		}
		private Entrys(int key, int value[], boolean continuousFlag, String name){
			this.key = key;
			this.value = value;
			this.continuousFlag = continuousFlag;
			this.name = name;
		}
		
		/**
		 * 说明：根据key获取相应枚举值
		 * @param key
		 * @return
		 * @throws EnumNotFound
		 */
		public static Entrys getAttack(int key) {
			Entrys[] entrysList = Entrys.values();
			for( Entrys entry : entrysList){
				if( entry.getKey() == key ){
					return entry;
				}
			}
			return null;
		}
		
		/**
		 * 说明：根据value获取相应枚举值
		 * @param value
		 * @return
		 */
		public static Entrys getAttack(int[] value)  {
			if(ATTACK_FIGHT.value.equals(value)) {
				return ATTACK_FIGHT;
			} else if(ATTACK_WIN.value.equals(value)) {
				return ATTACK_WIN;
			} else if(ATTACK_WIN_PASSIVE.value.equals(value)){
				return ATTACK_WIN_PASSIVE;
			}else if(value.length < 1) {
				throw new NullPointerException("value is null");
			}
			return null;
		}
		
		@Override
		public String toString() {
			return String.valueOf(this.key);
		}
	}
	

}