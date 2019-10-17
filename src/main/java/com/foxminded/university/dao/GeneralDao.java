package com.foxminded.university.dao;

public interface GeneralDao {
	
	Object add(Object object);

	void remove(Object object);
	
	Object findById(int id);
}
