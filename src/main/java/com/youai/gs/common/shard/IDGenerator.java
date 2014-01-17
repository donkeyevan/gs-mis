package com.youai.gs.common.shard;

import javax.annotation.Resource;

import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

import org.springframework.data.redis.core.RedisTemplate;
public class IDGenerator {

//	public final static String PLAYER_ID = "PLAYER_ID";
//	public final static String CITY_ID = "CITY_ID";
//	public final static String LOT_ID = "LOT_ID";
//	public final static String MAGICCARD_ID = "MAGICCARD_ID";
//	public final static String VEHICLE_ID = "VEHICLE_ID";
//	public final static String FRIENDSHIP_ID = "FRIENDSHIP_ID";
//	public final static String FRIENDREQUEST_ID = "FRIENDREQUEST_ID";
//	public final static String COMMENTS_ID = "COMMENTS_ID";
//	private final RedisAtomicInteger playerCounter;
//	private final RedisAtomicInteger cityCounter;
//	private final RedisAtomicInteger lotCounter;
//	private final RedisAtomicInteger magiccardCounter;
//	private final RedisAtomicInteger vehicleCounter;
//	private final RedisAtomicInteger friendshipCounter;
//	private final RedisAtomicInteger friendrequestCounter;
//	private final RedisAtomicInteger commentsCounter;
//	public IDGenerator(RedisTemplate redisTemplate){
//		 playerCounter = new RedisAtomicInteger( IDGenerator.PLAYER_ID, 
//				 redisTemplate.getConnectionFactory() );
//		 cityCounter = new RedisAtomicInteger( IDGenerator.CITY_ID, 
//				 redisTemplate.getConnectionFactory() );
//		 vehicleCounter =  new RedisAtomicInteger( IDGenerator.VEHICLE_ID, 
//				 redisTemplate.getConnectionFactory() );
//		 lotCounter =  new RedisAtomicInteger( IDGenerator.LOT_ID, 
//				 redisTemplate.getConnectionFactory() );
//		 magiccardCounter =  new RedisAtomicInteger( IDGenerator.MAGICCARD_ID, 
//					redisTemplate.getConnectionFactory() );
//		 friendshipCounter =  new RedisAtomicInteger( IDGenerator.FRIENDSHIP_ID, 
//					redisTemplate.getConnectionFactory() );
//		 friendrequestCounter =  new RedisAtomicInteger( IDGenerator.FRIENDREQUEST_ID, 
//					redisTemplate.getConnectionFactory() );
//		 commentsCounter = new RedisAtomicInteger( IDGenerator.COMMENTS_ID, 
//					redisTemplate.getConnectionFactory() );
	
//		 
//	}
	
//	public int createCommentsId(){
//	return commentsCounter.addAndGet(1);
//}
//public int createPlayerId(){
//	return playerCounter.addAndGet(1);
//}
//public int createCityId(){
//	return cityCounter.addAndGet(1);
//}
//public int createLotId(){
//	return lotCounter.addAndGet(1);
//}
//public int createMagiccardId(){
//	return magiccardCounter.addAndGet(1);
//}
//public int createVehicleId(){
//	return vehicleCounter.addAndGet(1);
//}
//
//public int createFriendshipId(){
//	return friendshipCounter.addAndGet(1);
//}
//
//public int createFriendRequestId(){
//	return friendrequestCounter.addAndGet(1);
//}

	
	public Integer createCommentsId(){
		return null;
	}
	public Integer createPlayerId(){
		return null;
	}
	public Integer createCityId(){
		return null;
	}
	public Integer createLotId(){
		return null;
	}
	public Integer createMagiccardId(){
		return null;
	}
	public Integer createVehicleId(){
		return null;
	}
	
	public Integer createFriendshipId(){
		return null;
	}
	
	public Integer createFriendRequestId(){
		return null;
	}
	

}
