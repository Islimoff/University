package com.foxminded.university.dao;

import java.io.FileInputStream;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

import com.foxminded.config.DBUnitConfig;
import com.foxminded.university.dao.jdbc.SubjectJdbcDao;
import com.foxminded.university.model.Subject;

public class SubjectJdbcDaoTest extends DBUnitConfig {

	private ConnecctionProvider provider;
	private SubjectJdbcDao subjectJdbcDao;
	private Subject subject;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		beforeData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("Subject.xml")));
		tester.setDataSet(beforeData);
		tester.onSetup();
		provider = new ConnecctionProvider();
		subjectJdbcDao = new SubjectJdbcDao(provider);
		subject = new Subject();
	}

	public SubjectJdbcDaoTest(String name) {
		super(name);
	}

	@Test
	public void testAddSubject() throws Exception {
		subject.setName("Chemistry");
		subjectJdbcDao.add(subject);
		IDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("Subject-save.xml")));
		IDataSet actualData = tester.getConnection().createDataSet();
		String[] ignore = { "subject_id" };

		Assertion.assertEqualsIgnoreCols(expectedData, actualData, "subjects", ignore);
	}

	@Test
	public void testRemoveSubject() throws Exception {
		subject.setId(3);
		subject.setName("Math");
		subjectJdbcDao.remove(subject);
		IDataSet expectedData = new FlatXmlDataSetBuilder()
				.build(new FileInputStream(getFilePath("Subject-remove.xml")));
		IDataSet actualData = tester.getConnection().createDataSet();
		String[] ignore = { "subject_id" };

		Assertion.assertEqualsIgnoreCols(expectedData, actualData, "subjects", ignore);
	}

	@Test
	public void testGivenSubjectName_whenFindByName_thenSubject() {
		subject.setName("Java");
		subject.setId(2);

		assertEquals(subject, subjectJdbcDao.findByName("Java"));
	}

	@Test
	public void testGivenWrongSubjectName_whenFindByName_thenNull() {

		assertNull(subjectJdbcDao.findByName("C#"));
	}
}