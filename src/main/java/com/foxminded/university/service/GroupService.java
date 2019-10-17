package com.foxminded.university.service;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.jdbc.GroupJdbcDao;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;

public class GroupService {

	private ConnecctionProvider provider = new ConnecctionProvider();
	private GroupDao groupDao = new GroupJdbcDao(provider);

	public void add(Group group) {
		groupDao.add(group);
	}

	public void remove(Group group) {
		groupDao.remove(group);
	}

	public Group findByName(String name) {
		return groupDao.findByName(name);
	}

	public Group findByStudent(Student student) {
		return groupDao.findByStudent(student);
	}

	public Group findById(int id) {
		return groupDao.findById(id);
	}
}