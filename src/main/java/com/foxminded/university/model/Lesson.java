package com.foxminded.university.model;

import java.time.LocalDate;

public class Lesson {

	private int id;
	private Audience audience;
	private Teacher teacher;
	private LocalDate date;
	private Subject subject;
	private LessonTime lessonTime;
	private Group group;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Audience getAudience() {
		return audience;
	}

	public void setAudience(Audience audience) {
		this.audience = audience;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public LessonTime getLessonTime() {
		return lessonTime;
	}

	public void setLessonTime(LessonTime lessonTime) {
		this.lessonTime = lessonTime;
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
			Lesson lesson = (Lesson) obj;
			return this.id == lesson.id && this.audience.equals(lesson.audience) && this.teacher.equals(lesson.teacher)
					&& this.date.isEqual(lesson.date) && this.subject.equals(lesson.subject)
					&& this.lessonTime.equals(lesson.lessonTime) && this.group.equals(lesson.group);
		}
	}
}
