package com.foxminded.university.dao;

import java.io.FileInputStream;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

import com.foxminded.config.DBUnitConfig;
import com.foxminded.university.dao.jdbc.TeacherJdbcDao;
import com.foxminded.university.model.Teacher;

public class TeacherJdbcDaoTest extends DBUnitConfig {

	private ConnecctionProvider provider;
	private TeacherJdbcDao teacherJdbcDao;
	private Teacher teacher;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		beforeData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("Teacher.xml")));
		tester.setDataSet(beforeData);
		tester.onSetup();
		provider = new ConnecctionProvider();
		teacherJdbcDao = new TeacherJdbcDao(provider);
		teacher = new Teacher();
	}

	public TeacherJdbcDaoTest(String name) {
		super(name);
	}

	@Test
	public void testAddTeacher() throws Exception {
		teacher.setName("Walter");
		teacherJdbcDao.add(teacher);
		IDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("Teacher-save.xml")));
		IDataSet actualData = tester.getConnection().createDataSet();
		String[] ignore = { "teacher_id" };

		Assertion.assertEqualsIgnoreCols(expectedData, actualData, "teachers", ignore);
	}

	@Test
	public void testRemoveTeacher() throws Exception {
		teacher.setId(3);
		teacher.setName("Rick");
		teacherJdbcDao.remove(teacher);
		IDataSet expectedData = new FlatXmlDataSetBuilder()
				.build(new FileInputStream(getFilePath("Teacher-remove.xml")));
		IDataSet actualData = tester.getConnection().createDataSet();
		String[] ignore = { "teacher_id" };

		Assertion.assertEqualsIgnoreCols(expectedData, actualData, "teachers", ignore);
	}

	@Test
	public void testGivenName_whenFindByName_thenTeacher() {
		teacher.setName("Rick");
		teacher.setId(3);

		assertEquals(teacher, teacherJdbcDao.findByName("Rick"));
	}

	@Test
	public void testGivenWrongName_whenFindByName_thenNull() {
		teacher.setName("Walter White");
		teacherJdbcDao.add(teacher);

		assertNull(teacherJdbcDao.findByName("Walter Black"));
	}
}
