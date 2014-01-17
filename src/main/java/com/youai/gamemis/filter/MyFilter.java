package com.youai.gamemis.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.youai.gamemis.constants.AppConstant;

public class MyFilter implements Filter {

	public MyFilter() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Integer dataSourceIdx = (Integer)httpRequest.getSession().getAttribute(AppConstant.SESSION_DEAULT_SERVER_IDX_KEY);
		if(dataSourceIdx == null || dataSourceIdx == -1){
			//the test server's idx is 2
			dataSourceIdx = 2;
		}
		SpObserver.putSp("dataSource" + dataSourceIdx);
		chain.doFilter(request, response);
	}

	public void destroy() {

	}

}