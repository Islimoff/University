package com.foxminded.university.model;

public class Subject {

	private String name;
	private int id;

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

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || !(getClass() == obj.getClass()))
			return false;
		else {
			Subject subject = (Subject) obj;
			return subject.name.equals(this.name) && this.id == subject.id;
		}
	}
}
