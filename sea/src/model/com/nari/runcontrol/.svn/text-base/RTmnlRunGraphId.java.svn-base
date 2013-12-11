package com.nari.runcontrol;

/**
 * RTmnlRunGraphId entity. @author MyEclipse Persistence Tools
 */

public class RTmnlRunGraphId implements java.io.Serializable {

	// Fields

	private String terminalId;
	private String instlLocDiagram;
	private String wiringDiagram;

	// Constructors

	/** default constructor */
	public RTmnlRunGraphId() {
	}

	/** full constructor */
	public RTmnlRunGraphId(String terminalId, String instlLocDiagram,
			String wiringDiagram) {
		this.terminalId = terminalId;
		this.instlLocDiagram = instlLocDiagram;
		this.wiringDiagram = wiringDiagram;
	}

	// Property accessors

	public String getTerminalId() {
		return this.terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getInstlLocDiagram() {
		return this.instlLocDiagram;
	}

	public void setInstlLocDiagram(String instlLocDiagram) {
		this.instlLocDiagram = instlLocDiagram;
	}

	public String getWiringDiagram() {
		return this.wiringDiagram;
	}

	public void setWiringDiagram(String wiringDiagram) {
		this.wiringDiagram = wiringDiagram;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RTmnlRunGraphId))
			return false;
		RTmnlRunGraphId castOther = (RTmnlRunGraphId) other;

		return ((this.getTerminalId() == castOther.getTerminalId()) || (this
				.getTerminalId() != null
				&& castOther.getTerminalId() != null && this.getTerminalId()
				.equals(castOther.getTerminalId())))
				&& ((this.getInstlLocDiagram() == castOther
						.getInstlLocDiagram()) || (this.getInstlLocDiagram() != null
						&& castOther.getInstlLocDiagram() != null && this
						.getInstlLocDiagram().equals(
								castOther.getInstlLocDiagram())))
				&& ((this.getWiringDiagram() == castOther.getWiringDiagram()) || (this
						.getWiringDiagram() != null
						&& castOther.getWiringDiagram() != null && this
						.getWiringDiagram()
						.equals(castOther.getWiringDiagram())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTerminalId() == null ? 0 : this.getTerminalId()
						.hashCode());
		result = 37
				* result
				+ (getInstlLocDiagram() == null ? 0 : this.getInstlLocDiagram()
						.hashCode());
		result = 37
				* result
				+ (getWiringDiagram() == null ? 0 : this.getWiringDiagram()
						.hashCode());
		return result;
	}

}