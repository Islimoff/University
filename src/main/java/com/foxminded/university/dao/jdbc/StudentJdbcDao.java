package com.foxminded.university.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.model.Student;

public class StudentJdbcDao implements StudentDao {

	private ConnecctionProvider provider;
	private static final String INSERT = "INSERT INTO students(name) values(?)";
	private static final String DELETE = "DELETE FROM students WHERE student_id=?";
	private static final String UPDATE = "UPDATE students SET name=?,group_id=? WHERE student_id=?";
	private static final String FINDBYNAME = "SELECT student_id, group_id, name FROM students where name=?";
	private static final String FINDBYID = "SELECT student_id, name FROM students WHERE student_id=?";

	public StudentJdbcDao(ConnecctionProvider provider) {
		this.provider = provider;
	}

	@Override
	public Student add(Student student) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, student.getName());
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			resultSet.next();
			student.setId(resultSet.getInt("student_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	@Override
	public void remove(Student student) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE)) {
			statement.setInt(1, student.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Student update(Student student) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE)) {
			statement.setString(1, student.getName());
			statement.setInt(2, student.getGroupId());
			statement.setInt(3, student.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	@Override
	public Student findByName(String name) {
		Student student = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(FINDBYNAME)) {
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				student = mapToStudent(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	@Override
	public Student findById(int id) {
		Student student = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(FINDBYID)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				student = mapToStudent(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	private Student mapToStudent(ResultSet resultSet) throws SQLException {
		Student student = new Student();
		student.setId(resultSet.getInt("student_id"));
		student.setGroupId(resultSet.getInt("group_id"));
		student.setName(resultSet.getString("name"));
		return student;
	}
}