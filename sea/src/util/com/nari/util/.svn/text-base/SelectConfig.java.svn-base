package com.nari.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/*****
 * 注释定义
 * @author huangxuan
 * ***/
@Retention(RetentionPolicy.RUNTIME)
public @interface SelectConfig {
	/***
	 * 支持in
	 * *****/
	public enum LimitType {
		EQ, GE, GT, LE, LT,LIKE,IN
	};// 限制类型的枚举
	public enum LikeMode{
		START,
		END,
		CONTAINS
	}
	//LIKE模式，在限制模式为like或者为iLike的时候起作用
	public enum DealValueType {
		PARSENULL, PARSEEMPTY
	};// 对空的类型进行的处理的方式

	public String column();// 描述字段在数据库中的对应列

	public LimitType limit() default LimitType.EQ;// 限制的类型

	public DealValueType dealNull() default DealValueType.PARSENULL;// 空值的处理方式
	
	public LikeMode likeMode() default LikeMode.START;
}
