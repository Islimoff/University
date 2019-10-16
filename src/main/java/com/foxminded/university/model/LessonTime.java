package com.foxminded.university.model;

import java.time.LocalTime;

public class LessonTime {

	private LocalTime start;
	private LocalTime end;

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || !(getClass() == obj.getClass()))
			return false;
		else {
			LessonTime lessonTime = (LessonTime) obj;
			return this.start.equals(lessonTime.start) && this.end.equals(lessonTime.end);
		}
	}
}