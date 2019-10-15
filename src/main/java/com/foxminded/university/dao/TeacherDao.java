package com.foxminded.university.dao;

import com.foxminded.university.model.Subject;
import com.foxminded.university.model.Teacher;

public interface TeacherDao {
 
		Teacher findByName(String name);

		void add(Teacher teacher);

		void remove(Teacher teacher);

		void update(Teacher teacher);

		Teacher findById(int id);

		void addSubjectToTeacher(Teacher teacher, Subject subject);
	}

