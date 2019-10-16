package com.foxminded.university.dao;

import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;

public interface GroupDao {

	public Group add(Group group);

	public void remove(Group group);

	public Group findByName(String name);

	public Group findByStudent(Student student);
}
