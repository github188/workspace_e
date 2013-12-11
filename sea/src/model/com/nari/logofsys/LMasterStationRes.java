package com.nari.logofsys;

/**
 * LMasterStationRes entity. @author MyEclipse Persistence Tools
 */

public class LMasterStationRes implements java.io.Serializable {

	// Fields

	private LMasterStationResId id;
	private Double netUseRatio;
	private Double cpuUseRatio;
	private Double memoryUseRatio;
	private Double diskUseRatio;

	// Constructors

	/** default constructor */
	public LMasterStationRes() {
	}

	/** minimal constructor */
	public LMasterStationRes(LMasterStationResId id) {
		this.id = id;
	}

	/** full constructor */
	public LMasterStationRes(LMasterStationResId id, Double netUseRatio,
			Double cpuUseRatio, Double memoryUseRatio, Double diskUseRatio) {
		this.id = id;
		this.netUseRatio = netUseRatio;
		this.cpuUseRatio = cpuUseRatio;
		this.memoryUseRatio = memoryUseRatio;
		this.diskUseRatio = diskUseRatio;
	}

	// Property accessors

	public LMasterStationResId getId() {
		return this.id;
	}

	public void setId(LMasterStationResId id) {
		this.id = id;
	}

	public Double getNetUseRatio() {
		return this.netUseRatio;
	}

	public void setNetUseRatio(Double netUseRatio) {
		this.netUseRatio = netUseRatio;
	}

	public Double getCpuUseRatio() {
		return this.cpuUseRatio;
	}

	public void setCpuUseRatio(Double cpuUseRatio) {
		this.cpuUseRatio = cpuUseRatio;
	}

	public Double getMemoryUseRatio() {
		return this.memoryUseRatio;
	}

	public void setMemoryUseRatio(Double memoryUseRatio) {
		this.memoryUseRatio = memoryUseRatio;
	}

	public Double getDiskUseRatio() {
		return this.diskUseRatio;
	}

	public void setDiskUseRatio(Double diskUseRatio) {
		this.diskUseRatio = diskUseRatio;
	}

}