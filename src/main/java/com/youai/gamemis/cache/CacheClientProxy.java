package com.youai.gamemis.cache;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.danga.MemCached.MemCachedClient;

public class CacheClientProxy {
	protected static Logger logger = Logger.getLogger(CacheClientProxy.class);
	@Autowired 
	private MemCachedClient memcachedClient;
	private boolean enableCache = true;
	public boolean isEnableCachne() {
		return enableCache;
	}
	public void setEnableCache(boolean enableCache) {
		this.enableCache = enableCache;
	}
	
	
	public boolean set(String key, Object value){
		if( this.enableCache ){
			return memcachedClient.set(key, value);
		}
		logger.debug("The cache proxy disabled the cache! set cache failed! key:"+key);
		return false;
	}
	public Object get( String key ){
		if( this.enableCache ){
			return memcachedClient.get( key );
		}
		logger.debug("The cache proxy disabled the cache! get from cache failed! key:"+key);
		return null;
	}
	public boolean delete( String key ){
		if( this.enableCache ){
			return memcachedClient.delete( key );
		}
		logger.debug("The cache proxy disabled the cache! Delete from cache failed! key:" + key );
		return false;
	}
	public boolean flushAll(){
		if( this.enableCache ){
			return memcachedClient.flushAll();
		}
		logger.debug("The cache proxy disabled the cache! flush all cache failed! ");
		return false;
	}
	public boolean keyExists( String key){
		if( this.enableCache ){
			return memcachedClient.keyExists( key );
		}
		logger.debug("The cache proxy disabled the cache! keyExists failed! key: "+key);
		return false;
	}
}
