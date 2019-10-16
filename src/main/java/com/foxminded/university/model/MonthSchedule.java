package com.foxminded.university.model;

import java.util.ArrayList;
import java.util.List;

public class MonthSchedule {

	private List<DaySchedule> daySchedules = new ArrayList<>();

	public List<DaySchedule> getDaySchedules() {
		return daySchedules;
	}

	public void setDaySchedules(List<DaySchedule> daySchedules) {
		this.daySchedules = daySchedules;
	}

	public void addDaySchedule(DaySchedule daySchedule) {
		daySchedules.add(daySchedule);
	}

	public void removeDaySchedule(DaySchedule daySchedule) {
		daySchedules.remove(daySchedule);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || !(getClass() == obj.getClass()))
			return false;
		else {
			MonthSchedule monthSchedule = (MonthSchedule) obj;
			return monthSchedule.daySchedules.equals(this.daySchedules);
		}
	}
}