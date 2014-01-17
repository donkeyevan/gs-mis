package com.youai.gamemis.model;

import java.util.ArrayList;
import java.util.List;

public class ExtendLinkType  implements java.io.Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ExtendLink> extendLinks;

	public List<ExtendLink> getExtendLinks() {
		return extendLinks;
	}

	public void setExtendLinks(List<ExtendLink> extendLinks) {
		this.extendLinks = extendLinks;
	}
	
	public Object clone() {  
		try {
			ExtendLinkType o = (ExtendLinkType)super.clone();
			List<ExtendLink> elinks = new ArrayList<ExtendLink>();
			for(ExtendLink link : o.getExtendLinks() ){
				elinks.add( (ExtendLink)link.clone());
			}
			o.setExtendLinks( elinks );
			return o;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
