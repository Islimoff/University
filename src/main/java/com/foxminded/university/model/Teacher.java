package com.foxminded.university.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher {

	private String name;
	private int id;
	private List<Subject> subjects = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || !(getClass() == obj.getClass()))
			return false;
		else {
			Teacher teacher = (Teacher) obj;
			return teacher.name.equals(this.name) && this.id == teacher.id && this.subjects.equals(teacher.subjects);
		}
	}
}