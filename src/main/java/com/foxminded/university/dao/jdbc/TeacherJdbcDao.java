package com.foxminded.university.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.model.Subject;
import com.foxminded.university.model.Teacher;

public class TeacherJdbcDao implements TeacherDao {

	private ConnecctionProvider provider;
	private static final String INSERT = "INSERT INTO teachers(name) values(?)";
	private static final String DELETE = "DELETE FROM teachers WHERE teacher_id=?";
	private static final String INSERTSUBJECT = "INSERT INTO teachers_subjects(teacher_id,subject_id) values(?,?)";
	private static final String UPDATE = "UPDATE teachers SET name=? WHERE teacher_id=?";
	private static final String FINDBYNAME = "SELECT teacher_id, name FROM teachers WHERE name=?";
	private static final String FINDBYID = "SELECT teacher_id, name FROM teachers WHERE teacher_id=?";

	public TeacherJdbcDao(ConnecctionProvider provider) {
		this.provider = provider;
	}

	@Override
	public Teacher add(Teacher teacher) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, teacher.getName());
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			resultSet.next();
			teacher.setId(resultSet.getInt("teacher_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teacher;
	}

	@Override
	public void addSubjectToTeacher(Teacher teacher, Subject subject) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERTSUBJECT)) {
			statement.setInt(1, teacher.getId());
			statement.setInt(2, subject.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(Teacher teacher) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE)) {
			statement.setInt(1, teacher.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Teacher teacher) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE)) {
			statement.setString(1, teacher.getName());
			statement.setInt(2, teacher.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Teacher findByName(String name) {
		Teacher teacher = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(FINDBYNAME)) {
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				teacher = new Teacher();
				teacher.setId(resultSet.getInt("teacher_id"));
				teacher.setName(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teacher;
	}

	@Override
	public Teacher findById(int id) {
		Teacher teacher = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(FINDBYID)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				teacher = new Teacher();
				teacher.setId(resultSet.getInt("teacher_id"));
				teacher.setName(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teacher;
	}
}