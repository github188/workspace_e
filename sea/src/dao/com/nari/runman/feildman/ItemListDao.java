package com.nari.runman.feildman;

import java.util.List;

import com.nari.basicdata.BCommProtItemListId;

/**
 * 
 * @author longkc
 *
 */
public interface ItemListDao {
	/**
	 * 
	 * @return
	 */
	public List<BCommProtItemListId> findItemList(String proItemNO);

}
