package com.jxc.model;

public class RuKuDetailInfo {
	private String rid;
	private String did;
	private int sl;
	private float jhj;
	private float je;
	public RuKuDetailInfo(){
		
	}
	public RuKuDetailInfo(String rid,String did,int sl,float jhj,float je){
		this.rid=rid;
		this.did=did;
		this.sl=sl;
		this.jhj=jhj;
		this.je=je;
		
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public int getSl() {
		return sl;
	}
	public void setSl(int sl) {
		this.sl = sl;
	}
	public float getJhj() {
		return jhj;
	}
	public void setJhj(float jhj) {
		this.jhj = jhj;
	}
	public float getJe() {
		return je;
	}
	public void setJe(float je) {
		this.je = je;
	}
	
}
