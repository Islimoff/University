package com.foxminded.university.dao;

public interface GeneralDao<T> {
	
	T add(T entity);

	T findById(int id);

	void remove(T entity);
}
