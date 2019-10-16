package com.foxminded.university.service;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.SubjectDao;
import com.foxminded.university.dao.jdbc.SubjectJdbcDao;
import com.foxminded.university.model.Subject;

public class SubjectService {

	private ConnecctionProvider provider = new ConnecctionProvider();
	private SubjectDao subjectDao = new SubjectJdbcDao(provider);

	public void add(Subject subject) {
		subjectDao.add(subject);
	}

	public void remove(Subject subject) {
		subjectDao.remove(subject);
	}

	public Subject findByName(String name) {
		return subjectDao.findByName(name);
	}

	public Subject findById(int id) {
		return subjectDao.findById(id);
	}
}
