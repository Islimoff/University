package com.foxminded.university.dao;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

import com.foxminded.config.DBUnitConfig;
import com.foxminded.university.dao.jdbc.LessonJdbcDao;
import com.foxminded.university.model.Audience;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Lesson;
import com.foxminded.university.model.LessonTime;
import com.foxminded.university.model.Subject;
import com.foxminded.university.model.Teacher;

public class LessonJdbcDaoTest extends DBUnitConfig {

	private ConnecctionProvider provider;
	private LessonJdbcDao lessonJdbcDao;
	private Lesson lesson;
	private LessonTime lessonTime;
	private Group group;
	private Teacher teacher;
	private Audience audience;
	private Subject subject;
	private List<Lesson> lessons;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		beforeData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("Lesson.xml")));
		tester.setDataSet(beforeData);
		tester.onSetup();
		provider = new ConnecctionProvider();
		lessonJdbcDao = new LessonJdbcDao(provider);
		lesson = new Lesson();
		lessonTime = new LessonTime();
		group = new Group();
		teacher = new Teacher();
		audience = new Audience();
		subject = new Subject();
		lessons = new ArrayList<>();
	}

	public LessonJdbcDaoTest(String name) {
		super(name);
	}

	@Test
	public void testAdd() throws Exception {
		audience.setId(3);
		audience.setNumber(3);
		lesson.setAudience(audience);
		group.setId(3);
		group.setGroupName("Gr-3");
		lesson.setGroup(group);
		teacher.setId(3);
		teacher.setName("Rick");
		lesson.setTeacher(teacher);
		subject.setId(3);
		subject.setName("Math");
		lesson.setSubject(subject);
		lesson.setDate(LocalDate.of(2020, 12, 12));
		lessonTime.setStart(LocalTime.of(12, 00));
		lessonTime.setEnd(LocalTime.of(13, 20));
		lesson.setLessonTime(lessonTime);
		lessonJdbcDao.add(lesson);
		IDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("Lesson-save.xml")));
		IDataSet actualData = tester.getConnection().createDataSet();
		String[] ignore = { "lesson_id" };

		Assertion.assertEqualsIgnoreCols(expectedData, actualData, "lessons", ignore);
	}

	@Test
	public void testRemove() throws Exception {
		lesson.setId(3);
		audience.setId(3);
		audience.setNumber(3);
		lesson.setAudience(audience);
		group.setId(3);
		group.setGroupName("Gr-3");
		lesson.setGroup(group);
		teacher.setId(3);
		teacher.setName("Rick");
		lesson.setTeacher(teacher);
		subject.setId(3);
		subject.setName("Math");
		lesson.setSubject(subject);
		lesson.setDate(LocalDate.of(2020, 12, 12));
		lessonTime.setStart(LocalTime.of(10, 00));
		lessonTime.setEnd(LocalTime.of(12, 20));
		lesson.setLessonTime(lessonTime);
		lessonJdbcDao.remove(lesson);
		IDataSet expectedData = new FlatXmlDataSetBuilder()
				.build(new FileInputStream(getFilePath("Lesson-remove.xml")));
		IDataSet actualData = tester.getConnection().createDataSet();
		String[] ignore = { "lesson_id" };

		Assertion.assertEqualsIgnoreCols(expectedData, actualData, "lessons", ignore);
	}

	@Test
	public void testgivenLessonId_whenFindById_thenLesson() {
		lesson.setId(3);
		audience.setId(3);
		audience.setNumber(3);
		lesson.setAudience(audience);
		group.setId(3);
		group.setGroupName("Gr-3");
		lesson.setGroup(group);
		teacher.setId(3);
		teacher.setName("Rick");
		lesson.setTeacher(teacher);
		subject.setId(3);
		subject.setName("Math");
		lesson.setSubject(subject);
		lesson.setDate(LocalDate.of(2020, 12, 12));
		lessonTime.setStart(LocalTime.of(11, 00));
		lessonTime.setEnd(LocalTime.of(12, 20));
		lesson.setLessonTime(lessonTime);

		assertEquals(lesson, lessonJdbcDao.findById(3));
	}

	@Test
	public void testgivenWringLessonId_whenFindById_thenNull() {

		assertNull(lessonJdbcDao.findById(444));
	}

	@Test
	public void testGivenDate_whenFindByDate_thenLessons() {
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
		lessons.add(lesson);

		assertEquals(lessons, lessonJdbcDao.findByDate(LocalDate.of(2019, 12, 12)));
	}

	@Test
	public void testGivenWrongDate_whenFindByDate_thenEmptyLessons() {

		assertEquals(lessons, lessonJdbcDao.findByDate(LocalDate.of(2019, 10, 14)));
	}

	@Test
	public void testGivenGroupAndLessonTimeAndDate_whenFindLessonTime_thenTrue() {
		group.setId(1);
		group.setGroupName("Gr-1");
		lessonTime.setStart(LocalTime.of(8, 00));
		lessonTime.setEnd(LocalTime.of(9, 20));

		assertTrue(lessonJdbcDao.findGroup(group, LocalDate.of(2019, 12, 12), lessonTime));
	}

//
	@Test
	public void testGivenGroupAndLessonTimeAndDate_whenFindLessonTime_thenFalse() {
		group.setId(1);
		group.setGroupName("Gr-1");
		lessonTime.setStart(LocalTime.of(8, 00));
		lessonTime.setEnd(LocalTime.of(9, 20));

		assertFalse(lessonJdbcDao.findGroup(group, LocalDate.of(2019, 10, 14), lessonTime));
	}

//
	@Test
	public void testGivenTeacherAndLessonTimeAndDate_whenFindLessonTime_thenTrue() {
		teacher.setId(1);
		teacher.setName("Shon");
		lessonTime.setStart(LocalTime.of(8, 00));
		lessonTime.setEnd(LocalTime.of(9, 20));

		assertTrue(lessonJdbcDao.findTeacher(teacher, LocalDate.of(2019, 12, 12), lessonTime));
	}

	@Test
	public void testGivenTeacherAndLessonTimeAndDate_whenFindLessonTime_thenFalse() {
		teacher.setId(1);
		teacher.setName("Shon");
		lessonTime.setStart(LocalTime.of(8, 00));
		lessonTime.setEnd(LocalTime.of(9, 20));

		assertFalse(lessonJdbcDao.findTeacher(teacher, LocalDate.of(2019, 10, 14), lessonTime));
	}

	@Test
	public void testGivenAudienceAndLessonTimeAndDate_whenFindLessonTime_thenTrue() {
		audience.setId(1);
		audience.setNumber(1);
		lessonTime.setStart(LocalTime.of(8, 00));
		lessonTime.setEnd(LocalTime.of(9, 20));

		assertTrue(lessonJdbcDao.findAudience(audience, LocalDate.of(2019, 12, 12), lessonTime));
	}

	@Test
	public void testGivenAudienceAndLessonTimeAndDate_whenFindLessonTime_thenFalse() {
		audience.setId(1);
		audience.setNumber(1);
		lessonTime.setStart(LocalTime.of(8, 00));
		lessonTime.setEnd(LocalTime.of(9, 20));

		assertFalse(lessonJdbcDao.findAudience(audience, LocalDate.of(2019, 10, 14), lessonTime));
	}

}