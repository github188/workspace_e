package com.nari.basicdata;

/**
 * BDataMappingId entity. @author MyEclipse Persistence Tools
 */

public class BDataMappingId implements java.io.Serializable {

	// Fields

	private String protocolNo;
	private Byte dataGroup;
	private String tableName;
	private String columnName;
	private Short columnId;
	private Byte ctpt;
	private String timePoint;

	// Constructors

	/** default constructor */
	public BDataMappingId() {
	}

	/** full constructor */
	public BDataMappingId(String protocolNo, Byte dataGroup, String tableName,
			String columnName, Short columnId, Byte ctpt, String timePoint) {
		this.protocolNo = protocolNo;
		this.dataGroup = dataGroup;
		this.tableName = tableName;
		this.columnName = columnName;
		this.columnId = columnId;
		this.ctpt = ctpt;
		this.timePoint = timePoint;
	}

	// Property accessors

	public String getProtocolNo() {
		return this.protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	public Byte getDataGroup() {
		return this.dataGroup;
	}

	public void setDataGroup(Byte dataGroup) {
		this.dataGroup = dataGroup;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Short getColumnId() {
		return this.columnId;
	}

	public void setColumnId(Short columnId) {
		this.columnId = columnId;
	}

	public Byte getCtpt() {
		return this.ctpt;
	}

	public void setCtpt(Byte ctpt) {
		this.ctpt = ctpt;
	}

	public String getTimePoint() {
		return this.timePoint;
	}

	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BDataMappingId))
			return false;
		BDataMappingId castOther = (BDataMappingId) other;

		return ((this.getProtocolNo() == castOther.getProtocolNo()) || (this
				.getProtocolNo() != null
				&& castOther.getProtocolNo() != null && this.getProtocolNo()
				.equals(castOther.getProtocolNo())))
				&& ((this.getDataGroup() == castOther.getDataGroup()) || (this
						.getDataGroup() != null
						&& castOther.getDataGroup() != null && this
						.getDataGroup().equals(castOther.getDataGroup())))
				&& ((this.getTableName() == castOther.getTableName()) || (this
						.getTableName() != null
						&& castOther.getTableName() != null && this
						.getTableName().equals(castOther.getTableName())))
				&& ((this.getColumnName() == castOther.getColumnName()) || (this
						.getColumnName() != null
						&& castOther.getColumnName() != null && this
						.getColumnName().equals(castOther.getColumnName())))
				&& ((this.getColumnId() == castOther.getColumnId()) || (this
						.getColumnId() != null
						&& castOther.getColumnId() != null && this
						.getColumnId().equals(castOther.getColumnId())))
				&& ((this.getCtpt() == castOther.getCtpt()) || (this.getCtpt() != null
						&& castOther.getCtpt() != null && this.getCtpt()
						.equals(castOther.getCtpt())))
				&& ((this.getTimePoint() == castOther.getTimePoint()) || (this
						.getTimePoint() != null
						&& castOther.getTimePoint() != null && this
						.getTimePoint().equals(castOther.getTimePoint())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getProtocolNo() == null ? 0 : this.getProtocolNo()
						.hashCode());
		result = 37 * result
				+ (getDataGroup() == null ? 0 : this.getDataGroup().hashCode());
		result = 37 * result
				+ (getTableName() == null ? 0 : this.getTableName().hashCode());
		result = 37
				* result
				+ (getColumnName() == null ? 0 : this.getColumnName()
						.hashCode());
		result = 37 * result
				+ (getColumnId() == null ? 0 : this.getColumnId().hashCode());
		result = 37 * result
				+ (getCtpt() == null ? 0 : this.getCtpt().hashCode());
		result = 37 * result
				+ (getTimePoint() == null ? 0 : this.getTimePoint().hashCode());
		return result;
	}

}