package com.foxminded.university.dao;

import com.foxminded.university.model.Student;

public interface StudentDao extends GeneralDao<Student> {

	Student findByName(String name);

	Student update(Student student);
}
