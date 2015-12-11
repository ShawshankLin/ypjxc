package com.jxc.model;

import java.awt.geom.FlatteningPathIterator;
import java.util.HashSet;
import java.util.Set;

public class SellMainInfo {
	private String sid;
	private String cid;
	private int pzs;
	private int xssl;
	private float xsje;
	private String xsrq;
	private String jsr;
	private Set<SellDetailInfo> tabSellDetails = new HashSet<SellDetailInfo>(0);
	public SellMainInfo(){
		
	}
	public SellMainInfo(String sid,String cid,int pzs,int xssl,float xsje,String xsrq,String jsr){
		this.sid=sid;
		this.cid=cid;
		this.pzs=pzs;
		this.xssl=xssl;
		this.xsje=xsje;
		this.xsrq=xsrq;
		this.jsr=jsr;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public int getXssl() {
		return xssl;
	}
	public void setXssl(int xssl) {
		this.xssl = xssl;
	}
	public float getXsje() {
		return xsje;
	}
	public void setXsje(float xsje) {
		this.xsje = xsje;
	}
	public String getXsrq() {
		return xsrq;
	}
	public void setXsrq(String xsrq) {
		this.xsrq = xsrq;
	}
	public String getJsr() {
		return jsr;
	}
	public void setJsr(String jsr) {
		this.jsr = jsr;
	}
	public int getPzs() {
		return pzs;
	}
	public void setPzs(int pzs) {
		this.pzs = pzs;
	}
	public Set<SellDetailInfo> getTabSellDetails() {
		return tabSellDetails;
	}
	public void setTabSellDetails(Set<SellDetailInfo> tabSellDetails) {
		this.tabSellDetails = tabSellDetails;
	}
	
	
}
