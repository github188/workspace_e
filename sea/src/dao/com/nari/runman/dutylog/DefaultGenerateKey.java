package com.nari.runman.dutylog;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.GeneratedValue;

public class DefaultGenerateKey implements IGenerateKey {

	@Override
	public String createKey(Field f, List args) {
//		if(f.isAnnotationPresent(GeneratedValue.class)){
//			GeneratedValue gv=f.getAnnotation(GeneratedValue.class);
//			return gv.generator()+".nextval";
//		}
		
		return null;
	}

}
