package com.nari.sysman.codeman;

import java.util.List;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.support.Page;
import com.nari.sysman.codeman.CodeManOut;
import com.nari.sysman.codeman.Impl.CodeManManagerImpl;

public class CodeManAction extends BaseAction {
	private CodeManManagerImpl iCodeManManager;
	private long totalCount;
	private List<CodeManOut> CodeManOutList;
	private List<CodeManOutSub> CodeManOutSubList;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private boolean success = true;
	private String sortId;
	private List<CodeManIn> codeManInList;
	private List<CodeManIn> codeManInSubList;
	private String sql;
	
	

	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}


	public List<CodeManIn> getCodeManInSubList() {
		return codeManInSubList;
	}


	public void setCodeManInSubList(List<CodeManIn> codeManInSubList) {
		this.codeManInSubList = codeManInSubList;
	}


	public List<CodeManIn> getCodeManInList() {
		return codeManInList;
	}


	public void setCodeManInList(List<CodeManIn> codeManInList) {
		this.codeManInList = codeManInList;
	}


	public List<CodeManOutSub> getCodeManOutSubList() {
		return CodeManOutSubList;
	}


	public void setCodeManOutSubList(List<CodeManOutSub> codeManOutSubList) {
		CodeManOutSubList = codeManOutSubList;
	}


	public String getSortId() {
		return sortId;
	}


	public void setSortId(String sortId) {
		this.sortId = sortId;
	}


	public CodeManManagerImpl getiCodeManManager() {
		return iCodeManManager;
	}


	public long getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}


	public List<CodeManOut> getCodeManOutList() {
		return CodeManOutList;
	}


	public void setCodeManOutList(List<CodeManOut> codeManOutList) {
		CodeManOutList = codeManOutList;
	}


	public long getStart() {
		return start;
	}


	public void setStart(long start) {
		this.start = start;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public void setiCodeManManager(CodeManManagerImpl iCodeManManager) {
		this.iCodeManManager = iCodeManManager;
	}
	/**
	 * 查询营销编码目录
	 * @return 
	 * @throws Exception
	 */
	public String queryCodeManOut() throws Exception{
		try{
			Page<CodeManOut> CodeManOutPage = this.getiCodeManManager().queryCodeManOut(start, limit);
			CodeManOutList = CodeManOutPage.getResult();
			totalCount = CodeManOutPage.getTotalCount();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询营销编码具体菜单项
	 * @return 
	 * @throws Exception
	 */
	public String queryCodeManOutSub() throws Exception{
		try{
			Page<CodeManOutSub> CodeManOutSubPage = this.getiCodeManManager().queryCodeManOutSub(sortId, start, limit);
			CodeManOutSubList = CodeManOutSubPage.getResult();
			totalCount = CodeManOutSubPage.getTotalCount();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询内部编码具体菜单项
	 * @return 
	 * @throws Exception
	 */
	public String queryCodeManIn() throws Exception{
		try{
			Page<CodeManIn> CodeManInPage = this.getiCodeManManager().queryCodeManIn(start, limit);
			codeManInList = CodeManInPage.getResult();
			totalCount = CodeManInPage.getTotalCount();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询内部编码具体菜单项
	 * @return 
	 * @throws Exception
	 */
	public String queryCodeManInSub() throws Exception{
		try{
			Page<CodeManIn> CodeManInSubPage = this.getiCodeManManager().queryCodeManInSub(sortId, start, limit);
			codeManInSubList = CodeManInSubPage.getResult();
			totalCount = CodeManInSubPage.getTotalCount();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 内部编码增删改操作
	 * @return 
	 * @throws Exception
	 */
	public String codeManSet() throws Exception{
		try{
			this.iCodeManManager.CodeManSet(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
