package com.youai.gamemis.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.Assert;

/**
 * custom datasource that we can change the datasource conveniently
 * @author J_Milo
 *
 */

public abstract class MyAbstractRoutingDataSource extends AbstractDataSource{
	
	private boolean lenientFallback = true;
	
	private Map<Object, DataSource> resolvedDataSources;
	
	private DataSource resolvedDefaultDataSource;
	
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MyAbstractRoutingDataSource.class);
					   

	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return determineTargetDataSource().getConnection();
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return determineTargetDataSource().getConnection(username, password);
		
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void putNewDataSource(Object key, DataSource dataSource){
		if(this.resolvedDataSources == null){
			this.resolvedDataSources = new HashMap<Object, DataSource>();
		}
		if(this.resolvedDataSources.containsKey(key)){
			this.resolvedDataSources.remove(key);
			logger.info("remove old key:" + key);
		}
		logger.info("add key:" + key + ", value=" + dataSource);
		this.resolvedDataSources.put(key, dataSource);
	}
	
	/**
	 * Retrieve the current target DataSource. Determines the
	 * {@link #determineCurrentLookupKey() current lookup key}, performs
	 * a lookup in the {@link #setTargetDataSources targetDataSources} map,
	 * falls back to the specified
	 * {@link #setDefaultTargetDataSource default target DataSource} if necessary.
	 * @see #determineCurrentLookupKey()
	 */
	protected DataSource determineTargetDataSource() {
		Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
		Object lookupKey = determineCurrentLookupKey();
		/*int index = 0;
		for (Entry<Object, DataSource> element : resolvedDataSources.entrySet()) {
			logger.debug("myAbstractDS, index:" + index + ", key:" + element.getKey() + ", value:" + element.getValue().toString());
			index++;
		}*/
		DataSource dataSource = this.resolvedDataSources.get(lookupKey);
		if (dataSource == null && (this.lenientFallback || lookupKey == null)) {
			dataSource = this.resolvedDefaultDataSource;
		}
		if (dataSource == null) {
			throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
		}
		logger.info("myAbstractDS, hit DS is " + lookupKey + ", the detail is:"  + dataSource.toString());
		return dataSource;
	}
	
	protected abstract Object determineCurrentLookupKey();

	public boolean isLenientFallback() {
		return lenientFallback;
	}

	public void setLenientFallback(boolean lenientFallback) {
		this.lenientFallback = lenientFallback;
	}

	public Map<Object, DataSource> getResolvedDataSources() {
		return resolvedDataSources;
	}

	public void setResolvedDataSources(Map<Object, DataSource> resolvedDataSources) {
		this.resolvedDataSources = resolvedDataSources;
	}
	
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (iface.isInstance(this)){
			return (T) this;
		}
		return determineTargetDataSource().unwrap(iface);
	}

	public DataSource getResolvedDefaultDataSource() {
		return resolvedDefaultDataSource;
	}

	public void setResolvedDefaultDataSource(DataSource resolvedDefaultDataSource) {
		this.resolvedDefaultDataSource = resolvedDefaultDataSource;
	}
	
}
