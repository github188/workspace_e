package com.nari.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 公共日期方法处理类
 * @author 姜炜超
 */
public class DateUtil {
	 /**
	 * date转化为String（格式yyyy-MM-dd），如果有错返回当日信息
	 * @param date
	 * @return String 
	 */
    public static String dateToString(Date date){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	try{
    	    return format.format(date); 
    	}catch(Exception ex){
    		return format.format(new Date());
    	}
    }
    
    /**
	 * date转化为String（格式yyyy-MM-dd HH:mm:ss），如果有错返回当日信息
	 * @param date
	 * @return String 
	 */
    public static String dateToTSString(Date date){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try{
    	    return format.format(date); 
    	}catch(Exception ex){
    		return format.format(new Date());
    	}
    }
    
    /**
	 * date转化为String（格式yyyy.MM.dd），如果有错返回当日信息
	 * @param date
	 * @return String 
	 */
    public static String dateToString4Dot(Date date){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
    	try{
    	    return format.format(date); 
    	}catch(Exception ex){
    		return format.format(new Date());
    	}
    }
    
    /**
	 * 格式化日期（格式yyyyMMdd），如果有错返回当日信息
	 * @param date
	 * @return String 
	 */
    public static String dateToStringNoV(Date date){
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    	try{
    	    return format.format(date); 
    	}catch(Exception ex){
    		return format.format(new Date());
    	}
    }
    
    /**
	 * 获取两个日期的相差天数
	 * @param date1
	 * @param date2
	 * @return int 
	 * @throws Exception
	 */
    public static int getInterval(Date date1, Date date2){
    	if(date2.after(date1)){
            Date cal=date1;
            date1=date2;
            date2=cal;
        }       
    	long a1 = date1.getTime();
    	long a2 = date2.getTime();
    	long a3 = a1- a2;
    	return (int)(a3/(1000*60*60*24));
    }
    
    /**
	 * 返回下个月（格式yyyy-MM）
	 * @param date
	 * @return String 
	 */
    public static String afterMonth(String date){
    	String str[];
    	String chgDate = "";
    	if(null != date){
    	    str = date.split("-");
    	    if(null != str && 2 <= str.length){
    	    	if("12".equals(str[1])){
    	    		chgDate = (Integer.valueOf(str[0]).intValue() + 1) + "-01";
    	    	}else{
    	            chgDate = str[0]+"-"+(Integer.valueOf(str[1]).intValue() + 1);
    	    	}
    	    }
    	}
    	return chgDate;
    }
    
    /**
	 * 返回下i个月（格式yyyy-MM）
	 * @param date
	 * @return String 
	 */
    public static String afterMonth(int year, int month, int i){
    	String chgDate = "";
    	if(i <= 0){
    		chgDate = year+"-"+month;
    	}else{
    		int yearInte = i/12;
    		int monthInte = i%12;
    		if((month+monthInte) > 12){
    			chgDate = (year+yearInte+1)+"-"+(month+monthInte-12);
    		}else{
    			chgDate = (year+yearInte)+"-"+(month+monthInte);
    		}
    	}
    	return chgDate;
    }
    
    /**
	 * 返回下i个月（格式yyyy-MM）
	 * @param date
	 * @return String 
	 */
    public static String afterMonth(Date date, int i){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH)+1;
    	String chgDate = "";
    	if(i <= 0){
    		chgDate = year+"-"+month;
    	}else{
    		int yearInte = i/12;
    		int monthInte = i%12;
    		if((month+monthInte) > 12){
    			chgDate = (year+yearInte+1)+"-"+(month+monthInte-12);
    		}else{
    			chgDate = (year+yearInte)+"-"+(month+monthInte);
    		}
    	}
    	return chgDate;
    }
    
	/**
	 * 返回同期（即去年同日期）
	 * @param date
	 * @return String 
	 */
    public static String beforeYear(String date){
    	String str[];
    	String chgDate = "";
    	if(null != date){
    	    str = date.split("-");
    	    if(null != str && 3 <= str.length){
    	        chgDate = (Integer.valueOf(str[0]).intValue() - 1)+"-"+str[1]+"-"+str[2];
    	    }
    	}
    	return chgDate;
    }
    
    
    /**
	 * date往前推一定天数，然后转化为String
	 * @param date
	 * @return String 
	 */
    public static String beforeDate(Date date , int i){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE, -i);
    	return dateToString(cal.getTime());
    }
    
    /**
	 * date往后推一定天数，然后转化为String（格式yyyy-MM-dd）
	 * @param date
	 * @return String 
	 */
    public static String afterDate(Date date , int i){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE, i);
    	return dateToString(cal.getTime());
    }
    
    /**
	 * date往后推一定天数
	 * @param date
	 * @return date 
	 */
    public static Date afterDateNC(Date date , int i){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE, i);
    	return cal.getTime();
    }
    
    /**
	 * date往后推一定天数，然后转化为String（格式yyyy.MM.dd）
	 * @param date
	 * @return String 
	 */
    public static String afterDate4Dot(Date date , int i){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE, i);
    	return dateToString4Dot(cal.getTime());
    }
    
    /**
	 * date往后推一定天数，然后转化为String（格式yyyyMMdd）
	 * @param date
	 * @return String 
	 */
    public static String afterDateNoV(Date date , int i){
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	cal.add(Calendar.DATE, i);
    	return dateToStringNoV(cal.getTime());
    }
    
    /**
	 * 格式化日期（年月），如果有错返回当月信息
	 * @param date
	 * @return String 
	 */
    public static String dateToStringByMonth(Date date){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
    	try{
    	    return format.format(date); 
    	}catch(Exception ex){
    		return format.format(new Date());
    	}
    }
    
	
    /**
	 * timeStamp转化为String
	 * @param date
	 * @return String 
	 */
    public static String timeStampToStr(Timestamp time){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try{
    	    return format.format(time); 
    	}catch(Exception ex){
    		return "";
    	}
    } 
    
    /**
	 * date转化为String，主要是返回查询日的所在月份的第一天，如果有错返回当月首日
	 * @param date
	 * @return String 
	 */
    public static String fristDayByDate(Date date){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
    	try{
    	    return format.format(date)+"-01"; 
    	}catch(Exception ex){
    		return format.format(new Date())+"-01";
    	}
    }
    
    /**
	 * 得到输入月份的下个月第一天
	 * @param date 格式yyyy-MM或yyyy-MM-dd
	 * @return String 
	 */
    public static String getNextMonthFirstDay(String date){
    	String[] str = date.split("-");
    	if(null != str && str.length >=2) {
    		int year = Integer.valueOf(str[0]).intValue();
        	int month = Integer.valueOf(str[1]).intValue();
        	if(month < 12){
        		if(month < 9){
        			return year+"-0"+(month+1)+"-01";
        		}else{
        		    return year+"-"+(month+1)+"-01";
        		}
        	}else{
        		return (year+1)+"-"+"01-01";
        	}
    	}else{
    		return "";
    	}
    }
    
    /**
	 * date转化为数据库保存格式的date，如果有错返回当日信息
	 * @param date
	 * @return java.sql.Date
	 */
    public static java.sql.Date dateToSqlDate(Date date){
    	if(null == date){
    		return new java.sql.Date(new Date().getTime());
    	}
    	try{
    	    return (new java.sql.Date(date.getTime()));
    	}catch(Exception ex){
    		return new java.sql.Date(new Date().getTime());
    	}
    }
}
