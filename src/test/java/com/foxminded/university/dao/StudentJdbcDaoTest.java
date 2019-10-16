package com.foxminded.university.dao;

import java.io.FileInputStream;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

import com.foxminded.config.DBUnitConfig;
import com.foxminded.university.dao.jdbc.StudentJdbcDao;
import com.foxminded.university.model.Student;

public class StudentJdbcDaoTest extends DBUnitConfig {

	private ConnecctionProvider provider;
	private StudentJdbcDao studentJdbcDao;
	private Student student;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		beforeData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("Student.xml")));
		tester.setDataSet(beforeData);
		tester.onSetup();
		provider = new ConnecctionProvider();
		studentJdbcDao = new StudentJdbcDao(provider);
		student = new Student();
	}

	public StudentJdbcDaoTest(String name) {
		super(name);
	}

	@Test
	public void testAdd() throws Exception {
		student.setGroupId(4);
		student.setName("Ivan");
		studentJdbcDao.add(student);
		IDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("Student-save.xml")));
		IDataSet actualData = tester.getConnection().createDataSet();
		String[] ignore = { "student_id", "group_id" };

		Assertion.assertEqualsIgnoreCols(expectedData, actualData, "students", ignore);
	}

	@Test
	public void testRemove() throws Exception {
		student.setId(3);
		student.setName("Rick");
		studentJdbcDao.remove(student);
		IDataSet expectedData = new FlatXmlDataSetBuilder()
				.build(new FileInputStream(getFilePath("Student-remove.xml")));
		IDataSet actualData = tester.getConnection().createDataSet();
		String[] ignore = { "student_id", "group_id" };

		Assertion.assertEqualsIgnoreCols(expectedData, actualData, "students", ignore);
	}

	@Test
	public void testGivenName_whenFindByName_thenStudent() {
		student.setName("Rick");
		student.setId(3);

		assertEquals(student, studentJdbcDao.findByName("Rick"));
	}

	@Test
	public void testGivenWrongName_whenFindByName_thenNull() {

		assertNull(studentJdbcDao.findByName("Max Ivanov"));
	}
}
