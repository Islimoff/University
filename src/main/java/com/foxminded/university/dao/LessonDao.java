package com.foxminded.university.dao;

import java.time.LocalDate;
import java.util.List;

import com.foxminded.university.model.Audience;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Lesson;
import com.foxminded.university.model.LessonTime;
import com.foxminded.university.model.Teacher;

public interface LessonDao extends GeneralDao<Lesson> {

	List<Lesson> findByDate(LocalDate date);

	boolean findGroup(Group group, LocalDate localDate, LessonTime lessonTime);

	boolean findTeacher(Teacher teacher, LocalDate localDate, LessonTime lessonTime);

	boolean findAudience(Audience audience, LocalDate localDate, LessonTime lessonTime);

	boolean findTime(LessonTime lessonTime, LessonTime lessonTime2);

}
