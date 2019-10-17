package com.foxminded.university.dao;

import com.foxminded.university.model.Teacher;

public interface TeacherDao extends GeneralDao<Teacher> {

	Teacher findByName(String name);

	Teacher update(Teacher teacher);
}
