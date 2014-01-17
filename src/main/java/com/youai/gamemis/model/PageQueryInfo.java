package com.youai.gamemis.model;

import java.util.List;

public class PageQueryInfo {
  
    public int pageSum;
    public int rowCount;
    public int pn;
    public int rn;
    
    /*
     * 数据内容
     */
    public List<Mentity> mentities;
    
    /*
     * title内容
     */
    public Mentity mentity;
    public Mentity getMentity() {
		return mentity;
	}
	public void setMentity(Mentity mentity) {
		this.mentity = mentity;
	}
	public List<Mentity> getMentities() {
        return mentities;
    }
    public void setMentities(List<Mentity> mentities) {
        this.mentities = mentities;
    }
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
}
