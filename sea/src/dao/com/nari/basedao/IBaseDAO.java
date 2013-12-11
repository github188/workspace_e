package com.nari.basedao;

import java.util.List;

/**
 * 接口 IBaseDAO
 * 
 * @author zhangzhw
 * @param <T>
 * @describe 基本DAO接口
 */
public interface IBaseDAO<T> {
	public void add(T t) throws RuntimeException;

	public void save(T t) throws RuntimeException;

	public void update(T t) throws RuntimeException;

	public void delete(T t) throws RuntimeException;

	public void merge(T t) throws RuntimeException;

	public List<T> findAll() throws RuntimeException;

	public T findById(String id) throws RuntimeException;

	public T findByExample(T t) throws RuntimeException;

	public List<T> findByHql(String hql) throws RuntimeException;

}
