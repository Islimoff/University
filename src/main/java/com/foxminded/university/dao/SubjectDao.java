package com.foxminded.university.dao;

import com.foxminded.university.model.Subject;

public interface SubjectDao extends GeneralDao<Subject> {

	public Subject findByName(String name);
}
