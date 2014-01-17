package com.youai.gs.common.db.redis;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.serializer.StringRedisSerializer;


public class RedisProxy {
	protected static Logger logger = Logger.getLogger(RedisProxy.class);
	@Resource(name="redisTemplate")
	private RedisTemplate<String, Object> template;
	private ObjectMapper objectMapper = new ObjectMapper();
	private StringRedisSerializer stringSerializer;
	private boolean enableRedis = true;
	
	public StringRedisSerializer getStringSerializer() {
		return stringSerializer;
	}
	public void setStringSerializer(StringRedisSerializer stringSerializer) {
		this.stringSerializer = stringSerializer;
	}
	public boolean isEnable() {
		return enableRedis;
	}
	public void setEnable(boolean enableRedis) {
		this.enableRedis = enableRedis;
	}
	
	public void set(String key, Object value){
		if( this.isEnable() ) {
			template.opsForValue().set( key, value );
		}
	}
	
	public boolean hasKey(String key) {
		if (this.isEnable()) {
			return template.hasKey(key);
		}
		return false;
	}
	
	public Object get(String key){
		if( this.isEnable() ) {
			return template.opsForValue().get(key);
		} else {
			return null;
		}
	}
	
	public long incr(String key) {
		return customIncr( key, 1);
		//return incr(key, 1);
	}
	private long customIncr( String key, long value){
		if( !this.isEnable() ){
			return -1;
		}
		Long a = (Long)template.opsForValue().get( key );
		if( a == null ){
			a = 0L;
		}
		logger.debug(">>>>>>>>>city["+key+"] has car num:"+a);
		a = a+value;
		template.opsForValue().set( key , a);
		
		return a;
	}
	
	private long customDecr(String key, long value ){
		if( !this.isEnable() ){
			return -1;
		}
		Long a = (Long)template.opsForValue().get( key );
		if( a == null ){
			a = 0L;
		}else{
			a = a - value;
		}
		template.opsForValue().set( key , a);
		return a;
	}
	private long incr(String key, long value) {
		if (this.isEnable()) {
			return template.opsForValue().increment(key, value);
		}
		return -1;
	}
	
	public long decr(String key) {
		return customDecr(key, 1);
		//return decr(key, 1);
	}
	
	public long decr(String key, long value) {
		return incr(key, -value);
	}
	
	public void delete(String key) {
		if (this.isEnable()) {
			template.delete(key);
		}
	}
	
	public boolean isHashKeyExsited( String key, Object hashKey ){
		return template.opsForHash().hasKey( key, hashKey);
	}
	
	public void hashPut(String key, Object hashKey, Object hashValue ){
		template.opsForHash().put(key, hashKey, hashValue);
	}
	public void hashDelete(String key, Object hashKey ){
		template.opsForHash().delete( key, hashKey );
	}
	public Object hashGet( String key, Object hashKey ){
		return template.opsForHash().get( key, hashKey );
	}
	
	public Set<Object> hashKeys(String key){
		return template.opsForHash().keys( key );
	}
	
	public Map<Object,Object> hgetall( String key ){
		return template.opsForHash().entries( key );
	}
	public boolean isSetMember( String key, Object value ){
		return template.opsForSet().isMember( key, value );
	}
	public void setAdd( String key, Object value ){
		template.opsForSet().add( key, value );
	}
	public void setDelete(String key, Object value){
		template.opsForSet().remove(key, value);
	}
	public Set<Object> smembers(String key){
		return template.opsForSet().members(key);
	}
	public Set<Object> zrange(String key, long start, long end){
		return template.opsForZSet().range(key, start, end);
	}
	public double zscore( String key , Object item ){
		return template.opsForZSet().score( key , item);
	}
	public double zrank( String key , Object item ){
		return template.opsForZSet().rank( key , item);
	}
	public double zrevrank(String key, Object item ){
		return template.opsForZSet().reverseRank( key, item );
	}
	public void zrem( String key, Object item ){
		template.opsForZSet().remove( key, item );
	}
	public void zadd(String sortedSetKey, Object key, double value){
		if( this.isEnable() ){
			template.opsForZSet().add(sortedSetKey, key, value);
		}
	}
	public DataType type( String key ){
		return template.type(key);
	}
	public  List<Object> lrange( String key, long start, long end){
		return template.opsForList().range( key, start, end);
	}
	public long llen( String key){
		return template.opsForList().size( key );
	}
	
	public void zRemrangeByRank(String sortedSetKey, long begin, long end){
		template.opsForZSet().removeRange(sortedSetKey, begin, end);
	}
	
	public Set zReverseRange(String sortedSetKey, long begin, long end){
		return template.opsForZSet().reverseRange(sortedSetKey, begin, end);
	}
	
	public Set<TypedTuple<Object>> zRverseRangeWithScore( String sortedSetKey, long begin, long end ){
		return template.opsForZSet().reverseRangeWithScores(sortedSetKey, begin, end);
	}
	
	public Long zReverseRank(String sortedSetKey, Object key ){
		if( this.isEnable() ){
			return template.opsForZSet().reverseRank(sortedSetKey, key);
		}
		else{
			return null;
		}
	}
	
	public Long zsize(String sortedSetKey ){
		if( this.isEnable() ){
			
			return template.opsForZSet().size(sortedSetKey);
			
		}
		return 0L;
	}
	
	public Object hsetJson(final String key, final String subKey, final Object value)  
	{
		try{
			final byte[] bvalue = objectMapper.writeValueAsBytes( value );
			template.execute( new RedisCallback<Object>() {
	    	    public Object doInRedis(RedisConnection connection) throws DataAccessException {
	    	    	return connection.hSet( key.getBytes(), subKey.getBytes(), bvalue );
	    	    } }, true );
			return true;
		}catch(Exception e){
			logger.error( "", e);
			
		}
		return false;
	}
	
	public <MT> Collection<MT> hvaluesJson(final String key, Class<MT> clazz){
		List<byte[]> rawValues= template.execute( new RedisCallback<List<byte[]>>() {
    	    public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
    	    	List<byte[]> rawValues= connection.hVals( key.getBytes() );
    	    	if (rawValues == null) {
    				return null;
    			}
    	    	return rawValues;
    	    } }, true );
		try {
			if( rawValues != null ){
		    	Collection<MT> values = (List.class.isAssignableFrom(List.class) ? new ArrayList<MT>(rawValues.size())
						: new LinkedHashSet<MT>(rawValues.size()));
				for (byte[] bs : rawValues) {
					values.add( bs == null ? null : objectMapper.readValue(bs, 0, bs.length, clazz));
				}
				return  values;
			}else{
				return null;
			}
    	}catch(Exception e){
    		logger.error("", e);
    	}
    	return null;
	}
	
	public <MK,MT> Map<MK,MT> hgetallJson(final String key, final Class<MT> clazz ){
		Map<byte[], byte[]> entries = template.execute( new RedisCallback<Map<byte[], byte[]>>() {
    	    public Map<byte[], byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
    	    	
    	    	Map<byte[], byte[]> entries= connection.hGetAll( key.getBytes() );
    	    	if (entries == null) {
    				return null;
    			}
    	    	return entries;
    	    } }, true );
		try {
			if( entries != null ){
	    		Map<MK,MT> map = new LinkedHashMap<MK,MT>(entries.size());
	    		for (Map.Entry<byte[], byte[]> entry : entries.entrySet()) {
	    			map.put( (MK) stringSerializer.deserialize(entry.getKey()), 
	    					entry.getValue() == null ? null : (MT)objectMapper.readValue(entry.getValue(),
	    					0, entry.getValue().length, clazz ) );
	    		}
	    		return  map;
			}else{
				return null;
			}
    	}catch(Exception e){
    		logger.error("", e);
    	}
    	return null;
	}
	public <HV> HV hgetJson(final String key, final String subKey, Class<HV> clazz){
		byte[] value= template.execute( new RedisCallback<byte[]>() {
    	    public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
    	    	byte[] source= connection.hGet( key.getBytes(), subKey.getBytes()  );
    	    	if( source == null ){
    	    		return null;
    	    	}
    	    	return source;
    	    	
    	} }, true );
		try {
			if( value != null ){
				return objectMapper.readValue( value, 0, value.length, clazz );
			}else{
				return null;
			}
			
		}
		catch (Throwable ex) {
			logger.error("",ex);	
		}
		return null;
	}
	
	public <HV> List<HV> hmgetJson(final String key, Collection<String> fields, Class<HV> clazz){
		
		final byte[] rawKey = rawKey(key);

		final byte[][] rawHashKeys = new byte[fields.size()][];

		int counter = 0;
		for (String hashKey : fields) {
			rawHashKeys[counter++] = stringSerializer.serialize( hashKey );
		}

		List<byte[]> rawValues = template.execute( new RedisCallback<List<byte[]>>() {
    	    public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hMGet(rawKey, rawHashKeys);
			}
		} , true);
		if (rawValues == null) {
			return null;
		}
		try{
			Collection<HV> values = (List.class.isAssignableFrom(List.class) ? new ArrayList<HV>(rawValues.size())
					: new LinkedHashSet<HV>(rawValues.size()));
			if( values != null ){
				for (byte[] bs : rawValues) {
					values.add( bs == null ? null : objectMapper.readValue( bs, 0, bs.length, clazz) );
				}
			}
			return (List<HV>) values;
		}catch(Exception e){
			logger.error("", e);
			return null;
		}
	} 
	
	public boolean lpushJson(String key, Object value ){
		try{
			final byte[] rawKey = rawKey(key);
			final byte[] rawValue = objectMapper.writeValueAsBytes(value);
			
			template.execute( new RedisCallback<Object>() {
	    	    	public Object doInRedis(RedisConnection connection) throws DataAccessException {
	    	    		return connection.lPush( rawKey, rawValue );
	    	    	} }, true );
				return true;
		}catch(Exception e){
			logger.error( "", e);
		}
		return false;
	}
	
	public <V> List<V> lrangeJson(String key, final int begin,final int end, Class<V> clazz){
		try{
			final byte[] rawKey = rawKey(key);
			List<byte[]> rawValues = template.execute( new RedisCallback<List<byte[]>>() {
	    	    public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.lRange(rawKey, begin, end);
				}
			} , true);
			if (rawValues == null) {
				return null;
			}
	
			List<V> values =  new ArrayList<V>(rawValues.size());
			for (byte[] bs : rawValues) {
					values.add(bs == null ? null : objectMapper.readValue( bs, clazz) );
				
			}
			return values;
		}catch(Exception e){
			logger.error("", e);
		}
		return null;
	}
	private byte[] rawKey(String key){
		return key.getBytes();
	}
	
}
