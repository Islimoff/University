package com.foxminded.university.dao;

import com.foxminded.university.model.Student;

public interface StudentDao {

	Student findByName(String name);

	void add(Student student);

	void remove(Student student);

	void update(Student student);
}
