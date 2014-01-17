package com.youai.gamemis.service;


import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youai.gamemis.model.StatDataType;
import com.youai.gamemis.model.StatPaymentItem;
import com.youai.gamemis.model.StatPaymentResult;
import com.youai.gamemis.model.StatPlayerResult;
import com.youai.gamemis.model.StatUIPayment;
import com.youai.gamemis.model.dao.StatDAO;
import com.youai.gamemis.model.dao.StatPaymentItemDAO;
import com.youai.gamemis.util.CommonUtil;

@Service("StatService")
public class StatService {
	@Autowired
	StatDAO statDao;
	@Autowired
	StatPaymentItemDAO statPaymentDao;
	public List<StatPlayerResult> getStatPlayers(String begin_date, String end_date){				
		List<StatPlayerResult> statPlayers = statDao.getPlayerStat(begin_date, end_date);
		Collections.sort( statPlayers );
		return statPlayers;
	}
	
	public List<StatUIPayment> getStatPayments(String begin_date, String end_date) throws IllegalAccessException, InvocationTargetException, InstantiationException{
		List<StatPaymentResult> statPayments = statDao.getStatPayments(begin_date, end_date);
		List<StatPaymentItem> statPaymentItems = statPaymentDao.getStatPaymentItems();
		Map<String, StatPaymentItem> spmItemMap = new HashMap<String, StatPaymentItem>();

		ArrayList<DynaProperty> pros = new ArrayList<DynaProperty>();
		for(StatPaymentItem spmItem : statPaymentItems ){
//			Class sdType = CommonUtil.getStatDataTypeClazz( spmItem.getDataType() );
//			DynaProperty dp = new DynaProperty( spmItem.getStatItem(), sdType );
//		    pros.add( dp );
			spmItemMap.put( spmItem.getProductId(), spmItem );
		}
	
	
		//BasicDynaClass dynaC=new BasicDynaClass("statUi", null,pros.toArray(new DynaProperty[0]));
		Map<Date,StatUIPayment> spmuiMap = new HashMap<Date, StatUIPayment>();
		
		for( StatPaymentResult statPayment : statPayments ){
			if( spmItemMap.containsKey( statPayment.getResName() ) ){
				String statItem = spmItemMap.get( statPayment.getResName() ).getStatItem();
				if( spmuiMap.containsKey( statPayment.getStatDate()) ){
					StatUIPayment statUIPayment = spmuiMap.get(statPayment.getStatDate());
					BeanUtils.setProperty( statUIPayment, statItem,  statPayment.getResNum() );
				}else{
					StatUIPayment statUIPayment = new StatUIPayment();
					 BeanUtils.setProperty( statUIPayment, "statDate",   statPayment.getStatDate() );
					 BeanUtils.setProperty( statUIPayment,  statItem,  statPayment.getResNum() );
					spmuiMap.put( statPayment.getStatDate(), statUIPayment);
				}
			}
		}
		
		if( spmuiMap.isEmpty() ){ return null; }
		
		List<StatUIPayment> statUIBeans = new ArrayList(spmuiMap.values());
		Collections.sort( statUIBeans );
		return statUIBeans;
	}
	
	public int getPayuserNum( String begin_date, String end_date ){
		return statDao.getPayuserNum( begin_date, end_date);
	}
}
