package com.foxminded.university.service;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.dao.jdbc.TeacherJdbcDao;
import com.foxminded.university.model.Teacher;

public class TeacherService {

	private ConnecctionProvider provider = new ConnecctionProvider();
	private TeacherDao teacherDao = new TeacherJdbcDao(provider);

	public void add(Teacher teacher) {
		teacherDao.add(teacher);
	}

	public void remove(Teacher teacher) {
		teacherDao.remove(teacher);
	}

	public Teacher findByName(String name) {
		return teacherDao.findByName(name);
	}

	public void update(Teacher teacher) {
		teacherDao.update(teacher);
	}

	public Teacher findById(int id) {
		return teacherDao.findById(id);
	}
}
