package com.nari.util;

import java.math.BigDecimal;

public class NumberUtil {
	
	private static final int DEF_DIV_SCALE = 10;
	/**
	 * 判断对象是否为空
	 * @param o	被判断的对象
	 * @return
	 */
	public static float removeNull(Object o) {
		return removeNull(o, 0);
	}	
	
	/**
	 * 判断对象是否为空
	 * @param o	被判断的对象
	 * @param f	默认
	 * @return
	 */
	public static float removeNull(Object o, float f) {
		if (o == null) {
			return f;
		}
		
		try {
			return Float.parseFloat(o.toString());
		} catch (NumberFormatException nfe) {
			return f;
		}
	}
	
	public static Double removeDoubleNull(Object o){
		if(o == null){
			return new Double(0) ;
		}
		try {
			return new Double(Double.parseDouble(o.toString()));
		} catch (NumberFormatException nfe) {
			return new Double(0);
		}
	}
	
    
    /**
     * 两个Double数相
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double add(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
    }
    
    /**
     * 两个Double数相
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double sub(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }
    
    /**
     * 两个Double数相
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double mul(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
    }
    
    /**
     * 两个Double数相
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double div(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 两个Double数相除，并保留scale位小
     * @param v1
     * @param v2
     * @param scale
     * @return Double
     */
    public static Double div(Double v1,Double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
            "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

	
	/**
	 * 判断对象是否为空
	 * @param o	被判断的对象
	 * @return
	 */
	public static int removeIntNull(Object o) {
		return removeIntNull(o, 0);
	}	
	
	/**
	 * 判断对象是否为空
	 * @param o	被判断的对象
	 * @param f	默认
	 * @return
	 */
	public static int removeIntNull(Object o, int i) {
		if (o == null) {
			return i;
		}
		try {
			return Integer.parseInt(o.toString());
		}catch (NumberFormatException nfe) {
			return i;
		}
	}
	/**
	 * 
	 * @param divisor  除数
	 * @param dividend 被除
	 * @return 除法结果
	 * @author huajun
	 * @see 得到两个数相除的结果
	 */
	public static int getIntQuotient(int divisor,int dividend)
	{
		if(dividend==0||divisor==0){
			return 0;
		}
		int result=0;
		try{
			result=(int)(divisor*1000/dividend);
			String temp=Integer.toString(result);
			char a=temp.charAt(temp.length()-1);
			if(a>=53){
				result=(result+10)/10;
			}else{
				result=  result/10;
			}
		}catch(Exception e){
			result=0;
		}
		return result;
	}
	/**
	 * @param divisor  除数
	 * @param dividend 被除
	 * @return 除法结果
	 * @author qinling
	 * @see 得到两个数相除的结果
	 */
	public static float getQuotient(int divisor,int dividend){
		if(dividend==0||divisor==0){
			return 0;
		}
		float result=0;
		try{
			result=(divisor*100000/dividend);
			String temp=Float.toString(result);
			char a=temp.charAt(temp.length()-3);
			if(a>=53){
				result= ((int)(result+10)/10)/(float)100;
			}else{
				result= ((int)result/10)/(float)100;
			}
		}catch(Exception e){
			result=0;
		}
		return result;
	}
	/**
	 * 格式化Double类型的数据为两位小数
	 * @param f
	 * @return 格式化后的双精度数据
	 * @author songchaofly@sohu.com
	 */
	public static double FormatDouble(double f){
		java.text.NumberFormat form = java.text.NumberFormat.getInstance();
		((java.text.DecimalFormat)form).applyPattern("##0.00");
		String f_s;
		try{
			f_s = form.format(f);
		}catch (IllegalArgumentException iae){
			f_s = "0.00";
		}
		return new Double(f_s).doubleValue();
	}
	//测试程序
	public static void main(String[] args) {}
}