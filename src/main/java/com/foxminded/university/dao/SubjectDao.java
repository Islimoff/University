package com.foxminded.university.dao;

import com.foxminded.university.model.Subject;

public interface SubjectDao {

	public void add(Subject subject);

	public Subject remove(Subject subject);

	public Subject findByName(String name);

	public Subject findById(int id);
}
