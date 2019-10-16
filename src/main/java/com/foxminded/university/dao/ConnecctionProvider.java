package com.foxminded.university.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnecctionProvider {

	private Properties prop = new Properties();
	private Connection connection;

	public ConnecctionProvider() {
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("Db.config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(prop.getProperty("db.url"), prop.getProperty("db.username"),
					prop.getProperty("db.password"));
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}