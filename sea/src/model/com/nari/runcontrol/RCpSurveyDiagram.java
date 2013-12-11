package com.nari.runcontrol;

/**
 * RCpSurveyDiagram entity. @author MyEclipse Persistence Tools
 */

public class RCpSurveyDiagram implements java.io.Serializable {

	// Fields

	private RCpSurveyDiagramId id;

	// Constructors

	/** default constructor */
	public RCpSurveyDiagram() {
	}

	/** full constructor */
	public RCpSurveyDiagram(RCpSurveyDiagramId id) {
		this.id = id;
	}

	// Property accessors

	public RCpSurveyDiagramId getId() {
		return this.id;
	}

	public void setId(RCpSurveyDiagramId id) {
		this.id = id;
	}

}