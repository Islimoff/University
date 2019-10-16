package com.foxminded.university.service;

import java.time.LocalDate;
import java.util.List;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.LessonDao;
import com.foxminded.university.dao.jdbc.LessonJdbcDao;
import com.foxminded.university.model.Audience;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Lesson;
import com.foxminded.university.model.LessonTime;
import com.foxminded.university.model.Teacher;

public class LessonService {
	
	private ConnecctionProvider provider = new ConnecctionProvider();
	private LessonDao lessonDao= new LessonJdbcDao(provider);

	public void add(Lesson lesson) {
		lessonDao.add(lesson);
	}

	public void remove(Lesson lesson) {
		lessonDao.remove(lesson);
	}

	public Lesson findById(int id) {
		return lessonDao.findById(id);
	}

	public List<Lesson> findByDate(LocalDate date) {
		return lessonDao.findByDate(date);
	}

	public boolean findGroup(Group group, LocalDate localDate, LessonTime lessonTime) {
		return lessonDao.findGroup(group, localDate, lessonTime);
	}

	public boolean findTeacher(Teacher teacher, LocalDate localDate, LessonTime lessonTime) {
		return lessonDao.findTeacher(teacher, localDate, lessonTime);
	}

	public boolean findAudience(Audience audience, LocalDate localDate, LessonTime lessonTime) {
		return lessonDao.findAudience(audience, localDate, lessonTime);
	}

	public boolean findTime(LessonTime lessonTime, LessonTime lessonTime2) {
		Duration durationLesson = Duration.between(lessonTime.getStart(), lessonTime.getEnd());
		Duration perspectiveDurationLesson = Duration.between(lessonTime.getStart(), lessonTime2.getStart());
		return durationLesson.toMinutes() > perspectiveDurationLesson.abs().toMinutes();
	}

}
