package com.foxminded.service;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.foxminded.university.model.Audience;
import com.foxminded.university.model.DaySchedule;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Lesson;
import com.foxminded.university.model.LessonTime;
import com.foxminded.university.model.Subject;
import com.foxminded.university.model.Teacher;
import com.foxminded.university.service.ScheduleService;

import static java.lang.System.lineSeparator;

public class DayScheduleServiceTest {

	private ScheduleService dayScheduleService;
	private DaySchedule daySchedule;
	private List<Lesson> lessons;
	private Lesson lesson;
	private LessonTime lessonTime;
	private Audience audience;
	private Group group;
	private Teacher teacher;
	private Subject subject;

	@Before
	public void setUp() {
		dayScheduleService = new ScheduleService();
		lesson = new Lesson();
		daySchedule = new DaySchedule();
		lessonTime = new LessonTime();
		lessons = new ArrayList<>();
		audience = new Audience();
		group = new Group();
		teacher = new Teacher();
		subject = new Subject();
	}

	@Test
	public void givenDaySchedule_whenFormatDaySchedule_thenString() {
		lesson.setId(1);
		lessonTime.setStart(LocalTime.of(12, 00));
		lessonTime.setEnd(LocalTime.of(13, 20));
		audience.setNumber(1);
		lesson.setAudience(audience);
		group.setGroupName("Group1");
		lesson.setGroup(group);
		teacher.setName("Walter White");
		lesson.setTeacher(teacher);
		subject.setName("Chemistry");
		lesson.setSubject(subject);
		lesson.setDate(LocalDate.of(2019, 8, 14));
		lesson.setLessonTime(lessonTime);
		lessons.add(lesson);
		daySchedule.setLessons(lessons);
		daySchedule.setDate(LocalDate.of(2019, 8, 14));
		StringBuilder expected = new StringBuilder();
		expected.append(
				"   Date            Teacher           Audience            Subject               Time             Groups");
		expected.append(lineSeparator());
		expected.append(
				"------------------------------------------------------------------------------------------------------");
		expected.append(lineSeparator());
		expected.append(
				"2019-08-14     Walter White             1               Chemistry              12:00            Group1");
		expected.append(lineSeparator());
		expected.append(
				"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		expected.append(lineSeparator());
		expected.append(lineSeparator());
		expected.append(lineSeparator());

		assertEquals(expected.toString(), dayScheduleService.formatDaySchedule(daySchedule));
	}

	@Test
	public void givenEmptyDaySchedule_whenFormatDaySchedule_thenString() {
		String expected = "";
		assertEquals(expected, dayScheduleService.formatDaySchedule(daySchedule));
	}
}