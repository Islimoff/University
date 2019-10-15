package com.foxminded.config;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;

import com.foxminded.university.Main;

public class DBUnitConfig extends DBTestCase {
	private Properties prop;
	protected IDatabaseTester tester;
	protected IDataSet beforeData;

	@Before
	public void setUp() throws Exception {
		tester = new JdbcDatabaseTester(prop.getProperty("db.driver"), prop.getProperty("db.url"),
				prop.getProperty("db.username"), prop.getProperty("db.password"));
	}

	public DBUnitConfig(String name) {
		super(name);
		prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, prop.getProperty("db.driver"));
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, prop.getProperty("db.url"));
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, prop.getProperty("db.username"));
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, prop.getProperty("db.password"));
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return beforeData;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.DELETE_ALL;
	}

	public static String getFilePath(String fileName) {
		return new File(Main.class.getClassLoader().getResource(fileName).getFile()).getAbsolutePath();
	}
}
