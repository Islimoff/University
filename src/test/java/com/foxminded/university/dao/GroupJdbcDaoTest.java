package com.foxminded.university.dao;

import java.io.FileInputStream;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

import com.foxminded.config.DBUnitConfig;
import com.foxminded.university.dao.jdbc.GroupJdbcDao;
import com.foxminded.university.dao.jdbc.StudentJdbcDao;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;

public class GroupJdbcDaoTest extends DBUnitConfig {

	private ConnecctionProvider provider;
	private GroupJdbcDao groupJdbcDao;
	private Group group;
	private Student student;
	private StudentJdbcDao studentJdbcDao;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		beforeData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("Group.xml")));
		tester.setDataSet(beforeData);
		tester.onSetup();
		provider = new ConnecctionProvider();
		groupJdbcDao = new GroupJdbcDao(provider);
		group = new Group();
		student = new Student();
		studentJdbcDao = new StudentJdbcDao(provider);
	}

	public GroupJdbcDaoTest(String name) {
		super(name);
	}

	@Test
	public void testAdd() throws Exception {
		group.setGroupName("Gr-4");
		groupJdbcDao.add(group);
		IDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("Group-save.xml")));
		IDataSet actualData = tester.getConnection().createDataSet();
		String[] ignore = { "group_id" };

		Assertion.assertEqualsIgnoreCols(expectedData, actualData, "groups", ignore);
	}

	@Test
	public void testRemove() throws Exception {
		group.setId(3);
		group.setGroupName("Gr-3");
		groupJdbcDao.remove(group);
		IDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("Group-remove.xml")));
		IDataSet actualData = tester.getConnection().createDataSet();
		String[] ignore = { "group_id" };

		Assertion.assertEqualsIgnoreCols(expectedData, actualData, "groups", ignore);
	}

	@Test
	public void testGivenGroupName_whenFindByName_thenGroup() {
		group.setId(1);
		group.setGroupName("Gr-1");
		assertEquals(group, groupJdbcDao.findByName("Gr-1"));
	}

	@Test
	public void testGivenWrongGroupName_whenFindByName_thenNull() {

		assertNull(groupJdbcDao.findByName("Gr-5"));
	}

	@Test
	public void testGivenStudent_whenFindByStudent_thenGroup() {
		student.setName("Max");
		student.setGroupId(1);
		group.setGroupName("Gr-1");
		group.setId(1);

		assertEquals(group, groupJdbcDao.findByStudent(student));

		studentJdbcDao.remove(student);
	}

	@Test
	public void testGivenWrongStudent_whenFindByStudent_thenNull() {

		assertNull(groupJdbcDao.findByStudent(student));
	}
}