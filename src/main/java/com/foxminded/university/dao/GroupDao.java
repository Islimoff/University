package com.foxminded.university.dao;

import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;

public interface GroupDao extends GeneralDao<Group> {

	public Group findByName(String name);

	public Group findByStudent(Student student);
}
