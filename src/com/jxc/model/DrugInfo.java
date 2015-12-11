package com.jxc.model;

public class DrugInfo {
	private String did;
	private String ypmc;
	private float xsj;
	private float jhj;
	private int sl;
	private String yt;
	private String scrq;
	private String bzq;
	private String sccj;
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String getYpmc() {
		return ypmc;
	}
	public void setYpmc(String ypmc) {
		this.ypmc = ypmc;
	}
	public float getXsj() {
		return xsj;
	}
	public void setXsj(float xsj) {
		this.xsj = xsj;
	}
	public float getJhj() {
		return jhj;
	}
	public void setJhj(float jhj) {
		this.jhj = jhj;
	}
	public int getSl() {
		return sl;
	}
	public void setSl(int sl) {
		this.sl = sl;
	}
	public String getYt() {
		return yt;
	}
	public void setYt(String yt) {
		this.yt = yt;
	}
	public String getScrq() {
		return scrq;
	}
	public void setScrq(String scrq) {
		this.scrq = scrq;
	}
	public String getBzq() {
		return bzq;
	}
	public void setBzq(String bzq) {
		this.bzq = bzq;
	}
	public String getSccj() {
		return sccj;
	}
	public void setSccj(String sccj) {
		this.sccj = sccj;
	}
	public String toString(){
		return getYpmc();
	}
	
}
