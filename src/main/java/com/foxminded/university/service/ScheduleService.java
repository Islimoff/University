package com.foxminded.university.service;

import static java.lang.System.lineSeparator;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.LessonDao;
import com.foxminded.university.dao.jdbc.GroupJdbcDao;
import com.foxminded.university.dao.jdbc.LessonJdbcDao;
import com.foxminded.university.model.DaySchedule;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.MonthSchedule;
import com.foxminded.university.model.Student;
import com.foxminded.university.model.Teacher;

public class ScheduleService {

	private ConnecctionProvider provider = new ConnecctionProvider();
	private LessonDao lessonDao = new LessonJdbcDao(provider);
	private GroupDao groupDao = new GroupJdbcDao(provider);

	public String formatDaySchedule(DaySchedule daySchedule) {
		StringBuilder result = new StringBuilder();
		if (!daySchedule.getLessons().isEmpty()) {
			result.append(String.format("%7s%19s%19s%19s%19s%19s", "Date", "Teacher", "Audience", "Subject", "Time",
					"Groups") + lineSeparator()
					+ "------------------------------------------------------------------------------------------------------"
					+ lineSeparator());
			daySchedule.getLessons().stream().sorted(Comparator.comparing(lesson -> lesson.getLessonTime().getStart()))
					.forEach(lesson -> result.append(
							String.format("%4s%17s%14d%24s%19s%18s", lesson.getDate(), lesson.getTeacher().getName(),
									lesson.getAudience().getNumber(), lesson.getSubject()
											.getName(),
									lesson.getLessonTime().getStart(), lesson.getGroup().getName())
									+ lineSeparator()
									+ "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
									+ lineSeparator() + lineSeparator() + lineSeparator()));
			return result.toString();
		}
		return result.toString();
	}

	public String formatMonthSchedule(MonthSchedule monthSchedule) {
		StringBuilder result = new StringBuilder();
		monthSchedule.getDaySchedules().forEach(daySchedule -> result.append(formatDaySchedule(daySchedule)));
		return result.toString();
	}

	public DaySchedule findStudentDaySchedule(Student student, LocalDate date) {
		if (lessonDao.findByDate(date).isEmpty()) {
			return null;
		}
		Group group = groupDao.findByStudent(student);
		if (group == null) {
			return null;
		}
		DaySchedule daySchedule = new DaySchedule();
		daySchedule.setDate(date);
		daySchedule.setLessons(lessonDao.findByDate(date).stream().filter(lesson -> lesson.getGroup().equals(group))
				.collect(Collectors.toList()));
		return daySchedule;
	}

	public DaySchedule findTeacherDaySchedule(Teacher teacher, LocalDate date) {
		if (lessonDao.findByDate(date).isEmpty()) {
			return null;
		} else {
			DaySchedule daySchedule = new DaySchedule();
			daySchedule.setDate(date);
			daySchedule.setLessons(lessonDao.findByDate(date).stream()
					.filter(lesson -> lesson.getTeacher().getName().equals(teacher.getName()))
					.collect(Collectors.toList()));
			return daySchedule;
		}
	}

	public MonthSchedule findStudentMonthSchedule(Student student, int year, Month month) {
		MonthSchedule monthSchedule = new MonthSchedule();
		for (int day = 1; day <= month.maxLength(); day++) {
			LocalDate dayOfMonth = LocalDate.of(year, month, day);
			DaySchedule daySchedule = findStudentDaySchedule(student, dayOfMonth);
			if (daySchedule != null) {
				monthSchedule.addDaySchedule(daySchedule);
			}
		}
		if (monthSchedule.getDaySchedules().isEmpty()) {
			return null;
		}
		return monthSchedule;
	}

	public MonthSchedule findTeacherMonthSchedule(Teacher teacher, int year, Month month) {
		MonthSchedule monthSchedule = new MonthSchedule();
		for (int day = 1; day <= month.maxLength(); day++) {
			LocalDate dayOfMonth = LocalDate.of(year, month, day);
			DaySchedule daySchedule = findTeacherDaySchedule(teacher, dayOfMonth);
			if (daySchedule != null) {
				monthSchedule.addDaySchedule(daySchedule);
			}
		}
		if (monthSchedule.getDaySchedules().isEmpty()) {
			return null;
		}
		return monthSchedule;
	}
}