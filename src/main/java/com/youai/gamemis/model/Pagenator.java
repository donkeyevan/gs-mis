package com.youai.gamemis.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.youai.gamemis.constants.GameConfig;

public class Pagenator {
	public int pageSum;
	public int rowCount;
	public int pn;
	public int rn;
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public List rows;
	public int getPageSum() {
		return pageSum;
	}
	public void setPageSum(int pageSum) {
		this.pageSum = pageSum;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getPn() {
		return pn;
	}
	public void setPn(int pn) {
		this.pn = pn;
	}
	public int getRn() {
		return rn;
	}
	public void setRn(int rn) {
		this.rn = rn;
	}
	public void parse( HttpServletRequest request){
		String rn_str = request.getParameter( "rn" ) == null? "": request.getParameter("rn").trim();
		String pn_str = request.getParameter( "pn" ) == null? "": request.getParameter("pn").trim();
		this.rn = rn_str.isEmpty() ? GameConfig.RN : Integer.parseInt( rn_str );
		this.pn = pn_str.isEmpty() ? GameConfig.PN : Integer.parseInt( pn_str );
		
	}
}
