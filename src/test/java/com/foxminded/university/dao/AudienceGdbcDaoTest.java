package com.foxminded.university.dao;

import java.io.FileInputStream;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

import com.foxminded.config.DBUnitConfig;
import com.foxminded.university.dao.jdbc.AudienceJdbcDao;
import com.foxminded.university.model.Audience;

public class AudienceGdbcDaoTest extends DBUnitConfig {

	private ConnecctionProvider provider;
	private AudienceJdbcDao audienceJdbcDao;
	private Audience audience;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		beforeData = new FlatXmlDataSetBuilder().build(new FileInputStream(getFilePath("Audience.xml")));
		tester.setDataSet(beforeData);
		tester.onSetup();
		provider = new ConnecctionProvider();
		audience = new Audience();
		audienceJdbcDao = new AudienceJdbcDao(provider);
	}

	public AudienceGdbcDaoTest(String name) {
		super(name);
	}

	@Test
	public void testAdd() throws Exception {
		audience.setNumber(4);
		audienceJdbcDao.add(audience);
		IDataSet expectedData = new FlatXmlDataSetBuilder()
				.build(new FileInputStream(getFilePath("Audience-save.xml")));
		IDataSet actualData = tester.getConnection().createDataSet();
		String[] ignore = { "audience_id" };

		Assertion.assertEqualsIgnoreCols(expectedData, actualData, "audiences", ignore);
	}

	@Test
	public void testRemove() throws Exception {
		audience.setId(3);
		audience.setNumber(3);
		audienceJdbcDao.remove(audience);
		IDataSet expectedData = new FlatXmlDataSetBuilder()
				.build(new FileInputStream(getFilePath("Audience-remove.xml")));
		IDataSet actualData = tester.getConnection().createDataSet();
		String[] ignore = { "audience_id" };

		Assertion.assertEqualsIgnoreCols(expectedData, actualData, "audiences", ignore);
	}

	@Test
	public void testGivenNumber_whenFindByNumber_thenAudience() {
		audience.setId(1);
		audience.setNumber(1);

		assertEquals(audience, audienceJdbcDao.findByNumber(1));
	}

	@Test
	public void testGivenWrongNumber_whenFindNumber_thenNull() {
		int number = 1;
		audience.setNumber(number);

		assertNull(audienceJdbcDao.findByNumber(4));
	}
}