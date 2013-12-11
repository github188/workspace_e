package test.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class TestString extends Assert {

	@Test
	public void testString() {
		String s1 = "F30 终端抄表时间";
		String s2 = "F169抄表日冻结正向有功电能示值（总、费率1~M）";
		String s3 = "当前三相及总有/无功功率";

		Pattern pattern = Pattern.compile("^[F0-9]+");
		Matcher m1 = pattern.matcher(s1);
		Matcher m2 = pattern.matcher(s2);
		Matcher m3 = pattern.matcher(s3);
		if(m1.find()) {
			System.out.println(m1.group());
		}
		if(m2.find()) {
			System.out.println(m2.group());
		}
		if(m3.find()) {
			System.out.println(m3.group());
		}
	}

	@Test
	public void testCollection() {
		List<Integer> intArrayList = new ArrayList<Integer>();
		List<Integer> intLinkedList = new LinkedList<Integer>();

		Random r = new Random(System.currentTimeMillis());
		for(int i = 0; i < 1000000; i++) {
			Integer roundInt = r.nextInt();
			intArrayList.add(roundInt);
			intLinkedList.add(roundInt);
		}

		long timeStart = new Date().getTime();
		Collections.sort(intArrayList);
		long timeEnd = new Date().getTime();
		System.out.println("ArrayList：" + (timeEnd - timeStart));

		timeStart = new Date().getTime();
		Collections.sort(intLinkedList);
		timeEnd = new Date().getTime();
		System.out.println("LinkedList：" + (timeEnd - timeStart));
	}

}
