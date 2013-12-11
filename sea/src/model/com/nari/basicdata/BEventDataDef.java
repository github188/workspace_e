package com.nari.basicdata;

/**
 * BEventDataDef entity. @author MyEclipse Persistence Tools
 */

public class BEventDataDef implements java.io.Serializable {

	// Fields

	private BEventDataDefId id;
	private String itemNo;
	private Short protItemSn;
	private String itemName;
	private Byte explanDatan;

	// Constructors

	/** default constructor */
	public BEventDataDef() {
	}

	/** minimal constructor */
	public BEventDataDef(BEventDataDefId id) {
		this.id = id;
	}

	/** full constructor */
	public BEventDataDef(BEventDataDefId id, String itemNo, Short protItemSn,
			String itemName, Byte explanDatan) {
		this.id = id;
		this.itemNo = itemNo;
		this.protItemSn = protItemSn;
		this.itemName = itemName;
		this.explanDatan = explanDatan;
	}

	// Property accessors

	public BEventDataDefId getId() {
		return this.id;
	}

	public void setId(BEventDataDefId id) {
		this.id = id;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public Short getProtItemSn() {
		return this.protItemSn;
	}

	public void setProtItemSn(Short protItemSn) {
		this.protItemSn = protItemSn;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Byte getExplanDatan() {
		return this.explanDatan;
	}

	public void setExplanDatan(Byte explanDatan) {
		this.explanDatan = explanDatan;
	}

}