package com.nari.coherence;

import java.util.List;

import com.nari.fe.commdefine.param.TaskTermRelations;

/**
 * @desc 终端地址，任务ID,pn关系
 * @author Administrator
 *
 */
public class TaskTmnlPnRelation {

	private short pn ;
	private TaskTermRelations ttr;
	public short getPn() {
		return pn;
	}
	public void setPn(short pn) {
		this.pn = pn;
	}
	public TaskTermRelations getTtr() {
		return ttr;
	}
	public void setTtr(TaskTermRelations ttr) {
		this.ttr = ttr;
	}
	
}
