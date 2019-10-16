package com.foxminded.university;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

import com.foxminded.config.DBUnitConfig;
import com.foxminded.university.model.Audience;
import com.foxminded.university.model.DaySchedule;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Lesson;
import com.foxminded.university.model.LessonTime;
import com.foxminded.university.model.MonthSchedule;
import com.foxminded.university.model.Student;
import com.foxminded.university.model.Subject;
import com.foxminded.university.model.Teacher;

public class UniversityTest extends DBUnitConfig {

	private University university;
	private Student student;
	private Group group;
	private Lesson lesson;
	private DaySchedule daySchedule;
	private LocalDate date;
	private Teacher teacher;
	private MonthSchedule monthSchedule;
	private Audience audience;
	private Subject subject;
	private LessonTime lessonTime;
	private List<Lesson> lessons;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		beforeData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("University.xml")));
		tester.setDataSet(beforeData);
		tester.onSetup();
		university = new University();
		student = new Student();
		group = new Group();
		lesson = new Lesson();
		daySchedule = new DaySchedule();
		teacher = new Teacher();
		monthSchedule = new MonthSchedule();
		audience = new Audience();
		lessonTime = new LessonTime();
		subject = new Subject();
		lessons = new ArrayList<>();
	}

	public UniversityTest(String name) {
		super(name);
	}

	@Test
	public void testGivenStudent_whenFindStudentDaySchedule_thenDaySchedule() throws SQLException {
		student.setName("Max");
		student.setId(1);
		List<Student> students = new ArrayList<>();
		students.add(student);
		lessons.add(getLesson());
		daySchedule.setLessons(lessons);
		date = LocalDate.of(2019, 12, 12);
		daySchedule.setDate(date);

		assertEquals(daySchedule, university.findStudentDaySchedule(student, date));
	}

	@Test
	public void testGivenWrongDate_whenFindStudentDaySchedule_thenNull() throws SQLException {
		student.setName("Max");
		student.setId(1);
		List<Student> students = new ArrayList<>();
		students.add(student);
		lessons.add(getLesson());
		daySchedule.setLessons(lessons);
		date = LocalDate.of(2019, 12, 12);
		daySchedule.setDate(date);

		assertNull(university.findStudentDaySchedule(student, LocalDate.of(2020, 8, 14)));
	}

	@Test
	public void testGivenTeacher_whenFindTeacherDaySchedule_thenDaySchedule() {
		teacher.setName("Shon");
		student.setId(1);
		lessons.add(getLesson());
		daySchedule.setLessons(lessons);
		date = LocalDate.of(2019, 12, 12);
		daySchedule.setDate(date);

		assertEquals(daySchedule, university.findTeacherDaySchedule(teacher, date));
	}

	@Test
	public void testGivenWrongDate_whenFindTeacherDaySchedule_thenNull() {
		teacher.setName("Shon");
		student.setId(1);
		lessons.add(getLesson());
		daySchedule.setLessons(lessons);
		date = LocalDate.of(2019, 12, 12);
		daySchedule.setDate(date);

		assertNull(university.findTeacherDaySchedule(teacher, LocalDate.of(2020, 8, 14)));
	}

	@Test
	public void testGivenStudent_whenFindStudentMonthSchedule_thenMonthSchedule() throws SQLException {
		student.setName("Max");
		student.setId(1);
		List<Student> students = new ArrayList<>();
		students.add(student);
		lessons.add(getLesson());
		daySchedule.setLessons(lessons);
		date = LocalDate.of(2019, 12, 12);
		daySchedule.setDate(date);
		monthSchedule.addDaySchedule(daySchedule);

		assertEquals(monthSchedule, university.findStudentMonthSchedule(student, 2019, Month.DECEMBER));
	}

	@Test
	public void testGivenWrongDate_whenFindStudentMonthSchedule_thenNull() throws SQLException {
		student.setName("Max");
		student.setId(1);
		List<Student> students = new ArrayList<>();
		students.add(student);
		lessons.add(getLesson());
		daySchedule.setLessons(lessons);
		date = LocalDate.of(2019, 12, 12);
		daySchedule.setDate(date);
		monthSchedule.addDaySchedule(daySchedule);

		assertNull(university.findStudentMonthSchedule(student, 2020, Month.AUGUST));
	}

	@Test
	public void testGivenTeacher_whenFindTeacherMonthSchedule_thenMonthSchedule() {
		teacher.setName("Shon");
		student.setId(1);
		lessons.add(getLesson());
		daySchedule.setLessons(lessons);
		date = LocalDate.of(2019, 12, 12);
		daySchedule.setDate(date);
		monthSchedule.addDaySchedule(daySchedule);

		assertEquals(monthSchedule, university.findTeacherMonthSchedule(teacher, 2019, Month.DECEMBER));
	}

	@Test
	public void testGivenWrongDate_whenFindTeacherMonthSchedule_thenMonthSchedule() {
		teacher.setName("Shon");
		student.setId(1);
		lessons.add(getLesson());
		daySchedule.setLessons(lessons);
		date = LocalDate.of(2019, 12, 12);
		daySchedule.setDate(date);
		monthSchedule.addDaySchedule(daySchedule);

		assertNull(university.findTeacherMonthSchedule(teacher, 2020, Month.AUGUST));
	}

	private Lesson getLesson() {
		lesson.setId(1);
		audience.setId(1);
		audience.setNumber(1);
		lesson.setAudience(audience);
		group.setId(1);
		group.setGroupName("Gr-1");
		lesson.setGroup(group);
		teacher.setId(1);
		teacher.setName("Shon");
		lesson.setTeacher(teacher);
		subject.setId(1);
		subject.setName("English");
		lesson.setSubject(subject);
		lesson.setDate(LocalDate.of(2019, 12, 12));
		lessonTime.setStart(LocalTime.of(8, 00));
		lessonTime.setEnd(LocalTime.of(9, 20));
		lesson.setLessonTime(lessonTime);
		return lesson;
	}
}
