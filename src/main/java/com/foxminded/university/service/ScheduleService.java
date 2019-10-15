package com.foxminded.university.service;

import static java.lang.System.lineSeparator;

import java.util.Comparator;

import com.foxminded.university.model.DaySchedule;
import com.foxminded.university.model.MonthSchedule;

public class ScheduleService {

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
									lesson.getLessonTime().getStart(), lesson.getGroup().getGroupName())
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
}