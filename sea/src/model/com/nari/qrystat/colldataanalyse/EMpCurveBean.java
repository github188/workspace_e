package com.nari.qrystat.colldataanalyse;

import java.util.Date;

/**
 * EMpCurve entity. @author MyEclipse Persistence Tools
 */

public class EMpCurveBean implements java.io.Serializable {

	// Fields

	private Long dataId;
	private String dataTime;
	private Double ct;
	private Double pt;
	private Boolean mark;
	private Double ua;
	private Double ub;
	private Double uc;
	private Double ia;
	private Double ib;
	private Double ic;
	private Double i0;
	private Double p;
	private Double pa;
	private Double pb;
	private Double pc;
	private Double q;
	private Double qa;
	private Double qb;
	private Double qc;
	private Byte loadMark;
	private Double f;
	private Double anUaba;
	private Double anUb;
	private Double anUcbc;
	private Double anIa;
	private Double anIb;
	private Double anIc;
	private Double papE;
	private Double prpE;
	private Double rapE;
	private Double rrpE;
	private Double papR;
	private Double prpR;
	private Double rapR;
	private Double rrpR;
	private Double fa;
	private Double fb;
	private Double fc;
	private String tmnlAssetNo;
	private String assetNo;
	private String mpNo;
	private String mpName;
	private String tmnlAddr;

	// Constructors

	/** default constructor */
	public EMpCurveBean() {
	}

	/** full constructor */
	public EMpCurveBean(String dataTime, Double ct, Double pt, Boolean mark,
			Double ua, Double ub, Double uc, Double ia, Double ib, Double ic,
			Double i0, Double p, Double pa, Double pb, Double pc, Double q,
			Double qa, Double qb, Double qc, Byte loadMark, Double f,
			Double anUaba, Double anUb, Double anUcbc, Double anIa,
			Double anIb, Double anIc, Double papE, Double prpE, Double rapE,
			Double rrpE, Double papR, Double prpR, Double rapR, Double rrpR) {
		this.dataTime = dataTime;
		this.ct = ct;
		this.pt = pt;
		this.mark = mark;
		this.ua = ua;
		this.ub = ub;
		this.uc = uc;
		this.ia = ia;
		this.ib = ib;
		this.ic = ic;
		this.i0 = i0;
		this.p = p;
		this.pa = pa;
		this.pb = pb;
		this.pc = pc;
		this.q = q;
		this.qa = qa;
		this.qb = qb;
		this.qc = qc;
		this.loadMark = loadMark;
		this.f = f;
		this.anUaba = anUaba;
		this.anUb = anUb;
		this.anUcbc = anUcbc;
		this.anIa = anIa;
		this.anIb = anIb;
		this.anIc = anIc;
		this.papE = papE;
		this.prpE = prpE;
		this.rapE = rapE;
		this.rrpE = rrpE;
		this.papR = papR;
		this.prpR = prpR;
		this.rapR = rapR;
		this.rrpR = rrpR;
	}

	// Property accessors

	public Long getDataId() {
		return this.dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public String getDataTime() {
		return this.dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public Double getCt() {
		return this.ct;
	}

	public void setCt(Double ct) {
		this.ct = ct;
	}

	public Double getPt() {
		return this.pt;
	}

	public void setPt(Double pt) {
		this.pt = pt;
	}

	public Boolean getMark() {
		return this.mark;
	}

	public void setMark(Boolean mark) {
		this.mark = mark;
	}

	public Double getUa() {
		return this.ua;
	}

	public void setUa(Double ua) {
		this.ua = ua;
	}

	public Double getUb() {
		return this.ub;
	}

	public void setUb(Double ub) {
		this.ub = ub;
	}

	public Double getUc() {
		return this.uc;
	}

	public void setUc(Double uc) {
		this.uc = uc;
	}

	public Double getIa() {
		return this.ia;
	}

	public void setIa(Double ia) {
		this.ia = ia;
	}

	public Double getIb() {
		return this.ib;
	}

	public void setIb(Double ib) {
		this.ib = ib;
	}

	public Double getIc() {
		return this.ic;
	}

	public void setIc(Double ic) {
		this.ic = ic;
	}

	public Double getI0() {
		return this.i0;
	}

	public void setI0(Double i0) {
		this.i0 = i0;
	}

	public Double getP() {
		return this.p;
	}

	public void setP(Double p) {
		this.p = p;
	}

	public Double getPa() {
		return this.pa;
	}

	public void setPa(Double pa) {
		this.pa = pa;
	}

	public Double getPb() {
		return this.pb;
	}

	public void setPb(Double pb) {
		this.pb = pb;
	}

	public Double getPc() {
		return this.pc;
	}

	public void setPc(Double pc) {
		this.pc = pc;
	}

	public Double getQ() {
		return this.q;
	}

	public void setQ(Double q) {
		this.q = q;
	}

	public Double getQa() {
		return this.qa;
	}

	public void setQa(Double qa) {
		this.qa = qa;
	}

	public Double getQb() {
		return this.qb;
	}

	public void setQb(Double qb) {
		this.qb = qb;
	}

	public Double getQc() {
		return this.qc;
	}

	public void setQc(Double qc) {
		this.qc = qc;
	}

	public Byte getLoadMark() {
		return this.loadMark;
	}

	public void setLoadMark(Byte loadMark) {
		this.loadMark = loadMark;
	}

	public Double getF() {
		return this.f;
	}

	public void setF(Double f) {
		this.f = f;
	}

	public Double getAnUaba() {
		return this.anUaba;
	}

	public void setAnUaba(Double anUaba) {
		this.anUaba = anUaba;
	}

	public Double getAnUb() {
		return this.anUb;
	}

	public void setAnUb(Double anUb) {
		this.anUb = anUb;
	}

	public Double getAnUcbc() {
		return this.anUcbc;
	}

	public void setAnUcbc(Double anUcbc) {
		this.anUcbc = anUcbc;
	}

	public Double getAnIa() {
		return this.anIa;
	}

	public void setAnIa(Double anIa) {
		this.anIa = anIa;
	}

	public Double getAnIb() {
		return this.anIb;
	}

	public void setAnIb(Double anIb) {
		this.anIb = anIb;
	}

	public Double getAnIc() {
		return this.anIc;
	}

	public void setAnIc(Double anIc) {
		this.anIc = anIc;
	}

	public Double getPapE() {
		return this.papE;
	}

	public void setPapE(Double papE) {
		this.papE = papE;
	}

	public Double getPrpE() {
		return this.prpE;
	}

	public void setPrpE(Double prpE) {
		this.prpE = prpE;
	}

	public Double getRapE() {
		return this.rapE;
	}

	public void setRapE(Double rapE) {
		this.rapE = rapE;
	}

	public Double getRrpE() {
		return this.rrpE;
	}

	public void setRrpE(Double rrpE) {
		this.rrpE = rrpE;
	}

	public Double getPapR() {
		return this.papR;
	}

	public void setPapR(Double papR) {
		this.papR = papR;
	}

	public Double getPrpR() {
		return this.prpR;
	}

	public void setPrpR(Double prpR) {
		this.prpR = prpR;
	}

	public Double getRapR() {
		return this.rapR;
	}

	public void setRapR(Double rapR) {
		this.rapR = rapR;
	}

	public Double getRrpR() {
		return this.rrpR;
	}

	public void setRrpR(Double rrpR) {
		this.rrpR = rrpR;
	}

	public Double getFa() {
		return fa;
	}

	public void setFa(Double fa) {
		this.fa = fa;
	}

	public Double getFb() {
		return fb;
	}

	public void setFb(Double fb) {
		this.fb = fb;
	}

	public Double getFc() {
		return fc;
	}

	public void setFc(Double fc) {
		this.fc = fc;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getAssetNo() {
		return assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public String getMpNo() {
		return mpNo;
	}

	public void setMpNo(String mpNo) {
		this.mpNo = mpNo;
	}

	public String getMpName() {
		return mpName;
	}

	public void setMpName(String mpName) {
		this.mpName = mpName;
	}

	public String getTmnlAddr() {
		return tmnlAddr;
	}

	public void setTmnlAddr(String tmnlAddr) {
		this.tmnlAddr = tmnlAddr;
	}
}