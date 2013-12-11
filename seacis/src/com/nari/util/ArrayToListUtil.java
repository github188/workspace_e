package com.nari.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayToListUtil {
	
	//数组转化List
	public static List<String> arrayToList(String[] array) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}
}
