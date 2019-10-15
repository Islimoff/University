package com.foxminded.university.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.model.Student;

public class StudentJdbcDao implements StudentDao {

	private ConnecctionProvider provider;

	public StudentJdbcDao(ConnecctionProvider provider) {
		this.provider = provider;
	}

	@Override
	public void add(Student student) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLStudent.INSERT.QUERY)) {
			statement.setString(1, student.getName());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(Student student) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLStudent.DELETE.QUERY)) {
			statement.setInt(1, student.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Student student) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLStudent.UPDATE.QUERY)) {
			statement.setString(1, student.getName());
			statement.setInt(2, student.getGroupId());
			statement.setInt(3, student.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Student findByName(String name) {
		Student student = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLStudent.FINDBYNAME.QUERY)) {
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				student = new Student();
				student.setId(resultSet.getInt("student_id"));
				student.setGroupId(resultSet.getInt("group_id"));
				student.setName(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	enum SQLStudent {
		INSERT("INSERT INTO students(name) values(?)"),
		DELETE("DELETE FROM students WHERE student_id=?"),
		UPDATE("UPDATE students SET name=?,group_id=? WHERE student_id=?"),
		FINDBYNAME("SELECT student_id, group_id, name FROM students where name=?");
		String QUERY;

		SQLStudent(String QUERY) {
			this.QUERY = QUERY;
		}
	}
}