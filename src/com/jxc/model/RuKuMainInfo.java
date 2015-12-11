package com.jxc.model;

import java.util.HashSet;
import java.util.Set;

public class RuKuMainInfo {
	private String rid;
	private int pzs;
	private int rks;
	private float rkje;
	private String rkrq;
	private String jsr;
	private String gid;
	private Set<RuKuDetailInfo> tabRukuDetails = new HashSet<RuKuDetailInfo>(0);
	
	
	public RuKuMainInfo(String rid, int pzs, int rks, float rkje, String rkrq,
			String jsr, String gid) {
		this.rid = rid;
		this.pzs = pzs;
		this.rks = rks;
		this.rkje = rkje;
		this.rkrq = rkrq;
		this.jsr = jsr;
		this.gid = gid;

	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public int getPzs() {
		return pzs;
	}

	public void setPzs(int pzs) {
		this.pzs = pzs;
	}

	public int getRks() {
		return rks;
	}

	public void setRks(int rks) {
		this.rks = rks;
	}

	public float getRkje() {
		return rkje;
	}

	public void setRkje(float rkje) {
		this.rkje = rkje;
	}

	public String getRkrq() {
		return rkrq;
	}

	public void setRkrq(String rkrq) {
		this.rkrq = rkrq;
	}

	public String getJsr() {
		return jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}



	public RuKuMainInfo() {

	}

	public Set<RuKuDetailInfo> getTabRukuDetails() {
		return this.tabRukuDetails;
	}

	public void setTabRukuDetails(Set<RuKuDetailInfo> tabRukuDetails) {
		this.tabRukuDetails = tabRukuDetails;
	}

}
