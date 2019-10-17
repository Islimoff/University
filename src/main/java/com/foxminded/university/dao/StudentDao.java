package com.foxminded.university.dao;

import com.foxminded.university.model.Student;

public interface StudentDao {

	Student findByName(String name);

	Student add(Student student);

	void remove(Student student);

	Student update(Student student);
}
