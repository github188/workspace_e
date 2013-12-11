package com.nari.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import net.sf.cglib.core.ReflectUtils;

import org.hibernate.property.Getter;
import org.hibernate.util.ReflectHelper;

import com.nari.util.SelectConfig.DealValueType;
import com.nari.util.SelectConfig.LikeMode;
import com.nari.util.SelectConfig.LimitType;



/*****
 * 
 *select sql语句生成器
 *@author huangxuan 语句生成器主体
 * ******/
public class SelectSqlCreator {
	
	@SuppressWarnings("unchecked")
	public static ResultParse getSelectSql(Object obj){
		if(obj==null){
			return new ResultParse("", new ArrayList());
		}
		StringBuilder sqls=new StringBuilder();
		//List<String> sqls=new ArrayList<String>();
		List list=new ArrayList();
		 Class<? extends Object> objClass = obj.getClass();
		 Field[] fs = objClass.getDeclaredFields();
		 for(int i=0;i<fs.length;i++){
		Field f = fs[i];
		Getter g=null;
		try {
			g= ReflectHelper.getGetter(objClass, f.getName());
		} catch (Exception e) {
			g=null;
		}
		if(g==null){
			continue;
		}
		//得到注解的信息
		SelectConfig o=f.getAnnotation(SelectConfig.class);
		if(null==o){
			continue;
		}
		String column=o.column();
		if(column==null){
			column=f.getName();
		}
		Object value=null;
		DealValueType dn = o.dealNull();
		//默认的null的处理方式为null不处理
		if(dn==null){
			dn=DealValueType.PARSENULL;
		}
		LimitType limit = o.limit();
		if(limit==null){
			limit=LimitType.EQ;
		}
		Object get=g.get(obj);
		
		if(dn.equals(DealValueType.PARSENULL)){
		
			if(!isNull(get)){
				//通过限制类型来最终拼接sql语句片段
			value=	parse(limit, column, sqls,get,o);
			/*****对IN的限制条件进行处理***/
			if(limit.equals(LimitType.IN)){
				sqls.append(" "+column+" in(");
				if(get instanceof Collection){
					Collection c=(Collection) get;
					Iterator it=c.iterator();
					while(it.hasNext()){
					Object co = it.next();
					if(it.hasNext()){
						sqls.append("?,");
					}else{
						sqls.append("?) and ");
					}
					list.add(co);
					}
				}
				//如果为数组
				else if(get.getClass().getName().startsWith("[L")){
					Object [] obs=(Object[]) get;
					for(int j=0;j<obs.length;j++){
						if(j==obs.length-1){
							sqls.append("?) ");
						}
						else{
							sqls.append("?,");
						}
						list.add(obs[j]);
					}
				}else{
					sqls.append("?) and ");
					list.add(value);
				}
			}else{
				list.add(value);
			}
			}
		}else if(dn.equals(DealValueType.PARSEEMPTY)){
			if(!isEmpty(get)){
				value=parse(limit, column, sqls,get,o);
				/*****对IN的限制条件进行处理***/
				if(limit.equals(LimitType.IN)){
					sqls.append(" "+column+" in(");
					if(get instanceof Collection){
						Collection c=(Collection) get;
						Iterator it=c.iterator();
						while(it.hasNext()){
						Object co = it.next();
						if(it.hasNext()){
							sqls.append("?,");
						}else{
							sqls.append("?) and ");
						}
						list.add(co);
						}
					}
					//如果为数组
					else if(get.getClass().getName().startsWith("[L")){
						Object [] obs=(Object[]) get;
						for(int j=0;j<obs.length;j++){
							if(j==obs.length-1){
								sqls.append("?) and ");
							}
							else{
								sqls.append("?,");
							}
							list.add(obs[j]);
						}
					}else{
						sqls.append("?) and ");
						list.add(value);
					}
				}else{
					list.add(value);
				}
			}
		}
		
		
	}
		 if(sqls.toString().endsWith("and")||sqls.toString().endsWith("and ")){
		 sqls.replace(sqls.lastIndexOf("and"), sqls.length(), " ");
		 }
		return new ResultParse(sqls.toString(),list);
	}
	private static Object parse(LimitType limit,String column,StringBuilder sqls,Object value,SelectConfig o){
		//通过限制类型来最终拼接sql语句片段
		if(limit.equals(LimitType.EQ)){
			sqls.append(column+"=? and ");
		}else if(limit.equals(LimitType.GE)){
			sqls.append(column+">=? and ");
		}else if(limit.equals(LimitType.GT)){
			sqls.append(column+">? and ");
		}
		else if(limit.equals(LimitType.LE)){
			sqls.append(column+"<=? and ");
		}
		else if(limit.equals(LimitType.LT)){
			sqls.append(column+"<? and ");
		}
		else if(limit.equals(LimitType.LIKE)){
			sqls.append(column+" like ? and ");
		}
		
		if(limit.equals(LimitType.LIKE)){
			LikeMode likeMode = o.likeMode();
			if(likeMode==null){
				likeMode=LikeMode.START;
			}
			if(likeMode.equals(LikeMode.START)){
				value=value.toString()+"%";
			}else if(likeMode.equals(LikeMode.END)){
				value="%"+value.toString();
			}else if(likeMode.equals(LikeMode.CONTAINS)){
				value="%"+value.toString()+"%";
			}
		}
		return value;
	}
	public static boolean isNull(Object obj){
		return obj==null;
	}
	public static boolean isEmpty(Object obj){
		return obj==null||obj.toString().trim().equals("");
	}
	
	
}
