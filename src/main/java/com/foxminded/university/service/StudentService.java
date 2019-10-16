package com.foxminded.university.service;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.dao.jdbc.StudentJdbcDao;
import com.foxminded.university.model.Student;

public class StudentService {

	private ConnecctionProvider provider = new ConnecctionProvider();
	private StudentDao studentDao = new StudentJdbcDao(provider);

	public void add(Student student) {
		studentDao.add(student);
	}

	public void remove(Student student) {
		studentDao.remove(student);
	}

	public void update(Student student) {
		studentDao.update(student);
	}

	public Student findByName(String name) {
		return studentDao.findByName(name);
	}
}
