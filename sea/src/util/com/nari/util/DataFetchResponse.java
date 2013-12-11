package com.nari.util;

import com.nari.fe.commdefine.task.Response;

/**
 * 为了容易在应用程序里面对状态进行封装，使用此类进行操作
 * 
 * @author huangxuan
 * 
 */
public class DataFetchResponse extends Response {
	private String str;
	private String desc;
	private int id;

	public DataFetchResponse(String str, int id, String desc) {
		this.str = str;
		this.desc = desc;
		this.id = id;
	}

	public int getErrorCodeId() {
		return this.id;
	}

	public String getErrorCodeStr() {
		return desc;
	}
}
