package com.foxminded.university.model;

public class Audience {

	private int number;
	private int id;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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
			Audience audience = (Audience) obj;
			return audience.number == this.number;
		}
	}
}