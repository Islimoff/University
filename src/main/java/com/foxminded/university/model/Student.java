package com.foxminded.university.model;

public class Student {

	private String name;
	private int id;
	private int groupId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || !(getClass() == obj.getClass()))
			return false;
		else {
			Student student = (Student) obj;
			return student.name.equals(this.name) && this.id == student.id && this.groupId == student.groupId;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}