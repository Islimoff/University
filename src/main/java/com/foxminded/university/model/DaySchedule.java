package com.foxminded.university.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaySchedule {

	private List<Lesson> lessons = new ArrayList<>();
	private LocalDate date;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || !(getClass() == obj.getClass()))
			return false;
		else {
			DaySchedule daySchedule = (DaySchedule) obj;
			return daySchedule.date.isEqual(this.date) && daySchedule.lessons.equals(this.lessons);
		}
	}
}