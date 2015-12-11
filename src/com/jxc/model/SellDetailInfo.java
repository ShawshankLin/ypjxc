package com.jxc.model;

public class SellDetailInfo {
	private String sid;
	private String did;
	private int sl;
	private float xsj;
	private float je;
	public SellDetailInfo() {

	}

	public SellDetailInfo(String sid,String did,int sl,float xsj,float je) {
		this.sid=sid;
		this.did=did;
		this.sl=sl;
		this.xsj=xsj;
		this.je=je;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
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

	public float getXsj() {
		return xsj;
	}

	public void setXsj(float xsj) {
		this.xsj = xsj;
	}

	public float getJe() {
		return je;
	}

	public void setJe(float je) {
		this.je = je;
	}

}
