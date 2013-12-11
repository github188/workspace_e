package com.nari.runcontrol;

/**
 * RCpSurveyDiagramId entity. @author MyEclipse Persistence Tools
 */

public class RCpSurveyDiagramId implements java.io.Serializable {

	// Fields

	private Long instSchemeId;
	private String instlLocDiagram;
	private String wiringDiagram;

	// Constructors

	/** default constructor */
	public RCpSurveyDiagramId() {
	}

	/** minimal constructor */
	public RCpSurveyDiagramId(Long instSchemeId) {
		this.instSchemeId = instSchemeId;
	}

	/** full constructor */
	public RCpSurveyDiagramId(Long instSchemeId, String instlLocDiagram,
			String wiringDiagram) {
		this.instSchemeId = instSchemeId;
		this.instlLocDiagram = instlLocDiagram;
		this.wiringDiagram = wiringDiagram;
	}

	// Property accessors

	public Long getInstSchemeId() {
		return this.instSchemeId;
	}

	public void setInstSchemeId(Long instSchemeId) {
		this.instSchemeId = instSchemeId;
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
		if (!(other instanceof RCpSurveyDiagramId))
			return false;
		RCpSurveyDiagramId castOther = (RCpSurveyDiagramId) other;

		return ((this.getInstSchemeId() == castOther.getInstSchemeId()) || (this
				.getInstSchemeId() != null
				&& castOther.getInstSchemeId() != null && this
				.getInstSchemeId().equals(castOther.getInstSchemeId())))
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
				+ (getInstSchemeId() == null ? 0 : this.getInstSchemeId()
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