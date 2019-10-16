package com.foxminded.university;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Collectors;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.jdbc.AudienceJdbcDao;
import com.foxminded.university.dao.jdbc.GroupJdbcDao;
import com.foxminded.university.dao.jdbc.LessonJdbcDao;
import com.foxminded.university.dao.jdbc.StudentJdbcDao;
import com.foxminded.university.dao.jdbc.SubjectJdbcDao;
import com.foxminded.university.dao.jdbc.TeacherJdbcDao;
import com.foxminded.university.model.Audience;
import com.foxminded.university.model.DaySchedule;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Lesson;
import com.foxminded.university.model.LessonTime;
import com.foxminded.university.model.MonthSchedule;
import com.foxminded.university.model.Student;
import com.foxminded.university.model.Subject;
import com.foxminded.university.model.Teacher;
import com.foxminded.university.service.ScheduleService;

public class University {
	private ConnecctionProvider provider=new ConnecctionProvider();
	private TeacherJdbcDao teacherJdbcDao = new TeacherJdbcDao(provider);
	private StudentJdbcDao studentJdbcDao = new StudentJdbcDao(provider);
	private LessonJdbcDao lessonJdbcDao = new LessonJdbcDao(provider);
	private AudienceJdbcDao audienceJdbcDao = new AudienceJdbcDao(provider);
	private GroupJdbcDao groupJdbcDao = new GroupJdbcDao(provider);
	private SubjectJdbcDao subjectJdbcDao = new SubjectJdbcDao(provider);
	private ScheduleService scheduleService = new ScheduleService();

	public void addTeacher(Teacher teacher) {
		teacherJdbcDao.add(teacher);
	}

	public void addSubjectToTeacher(Teacher teacher, Subject subject) {
		teacherJdbcDao.addSubjectToTeacher(teacher, subject);
	}

	public void removeTeacher(Teacher teacher) {
		teacherJdbcDao.remove(teacher);
	}

	public void updateTeacher(Teacher teacher) {
		teacherJdbcDao.update(teacher);
	}

	public void addStudent(Student student) throws SQLException {
		studentJdbcDao.add(student);
	}

	public void removeStudent(Student student) {
		studentJdbcDao.remove(student);
	}

	public void updateStudent(Student student) {
		studentJdbcDao.update(student);
	}

	public void addLesson(Lesson lesson) {
		lessonJdbcDao.add(lesson);
	}

	public void removeLesson(Lesson lesson) {
		lessonJdbcDao.remove(lesson);
	}

	public void addSubject(Subject subject) {
		subjectJdbcDao.add(subject);
	}

	public void removeSubject(Subject subject) {
		subjectJdbcDao.remove(subject);
	}

	public void addAudience(Audience audience) {
		audienceJdbcDao.add(audience);
	}

	public void removeAudience(Audience audience) {
		audienceJdbcDao.remove(audience);
	}

	public void addGroup(Group group) {
		groupJdbcDao.add(group);
	}

	public void removeGroup(Group group) {
		groupJdbcDao.remove(group);
	}

	public Teacher findTeacher(String name) {
		return teacherJdbcDao.findByName(name);
	}

	public Teacher findTeacherById(int id) {
		return teacherJdbcDao.findById(id);
	}

	public Student findStudent(String name) {
		return studentJdbcDao.findByName(name);
	}

	public Group findGroup(String name) {
		return groupJdbcDao.findByName(name);
	}

	public Group findGroupById(int id) {
		return groupJdbcDao.findById(id);
	}

	public Subject findSubject(String name) {
		return subjectJdbcDao.findByName(name);
	}

	public Subject findSubjectById(int id) {
		return subjectJdbcDao.findById(id);
	}

	public Lesson findLesson(int id) {
		return lessonJdbcDao.findById(id);
	}

	public Audience findAudience(int number) {
		return audienceJdbcDao.findByNumber(number);
	}

	public Audience findAudienceById(int id) {
		return audienceJdbcDao.findById(id);
	}

	public boolean findGroupInLesson(Group group, LocalDate localDate, LessonTime lessonTime) {
		return lessonJdbcDao.findGroup(group, localDate, lessonTime);
	}

	public boolean findTeacherInLesson(Teacher teacher, LocalDate localDate, LessonTime lessonTime) {
		return lessonJdbcDao.findTeacher(teacher, localDate, lessonTime);
	}

	public boolean findAudienceInLesson(Audience audience, LocalDate localDate, LessonTime lessonTime) {
		return lessonJdbcDao.findAudience(audience, localDate, lessonTime);
	}

	public DaySchedule findStudentDaySchedule(Student student, LocalDate date) {
		if (lessonJdbcDao.findByDate(date).isEmpty()) {
			return null;
		}
		Group group = groupJdbcDao.findByStudent(student);
		if (group == null) {
			return null;
		}
		DaySchedule daySchedule = new DaySchedule();
		daySchedule.setDate(date);
		daySchedule.setLessons(lessonJdbcDao.findByDate(date).stream().filter(lesson -> lesson.getGroup().equals(group))
				.collect(Collectors.toList()));
		return daySchedule;
	}

	public String formatDaySchedule(DaySchedule daySchedule) {
		return scheduleService.formatDaySchedule(daySchedule);
	}

	public String formatMonthSchedule(MonthSchedule monthSchedule) {
		return scheduleService.formatMonthSchedule(monthSchedule);
	}

	public DaySchedule findTeacherDaySchedule(Teacher teacher, LocalDate date) {
		if (lessonJdbcDao.findByDate(date).isEmpty()) {
			return null;
		} else {
			DaySchedule daySchedule = new DaySchedule();
			daySchedule.setDate(date);
			daySchedule.setLessons(lessonJdbcDao.findByDate(date).stream()
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