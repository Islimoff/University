package com.foxminded.university.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.model.Subject;
import com.foxminded.university.model.Teacher;

public class TeacherJdbcDao implements TeacherDao {

	private ConnecctionProvider provider;

	public TeacherJdbcDao(ConnecctionProvider provider) {
		this.provider = provider;
	}

	@Override
	public void add(Teacher teacher) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLTeacher.INSERT.QUERY)) {
			statement.setString(1, teacher.getName());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addSubjectToTeacher(Teacher teacher, Subject subject) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLTeacher.INSERTSUBJECT.QUERY)) {
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
				PreparedStatement statement = connection.prepareStatement(SQLTeacher.DELETE.QUERY)) {
			statement.setInt(1, teacher.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Teacher teacher) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLTeacher.UPDATE.QUERY)) {
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
				PreparedStatement statement = connection.prepareStatement(SQLTeacher.FINDBYNAME.QUERY)) {
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
				PreparedStatement statement = connection.prepareStatement(SQLTeacher.FINDBYID.QUERY)) {
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

	enum SQLTeacher {
		INSERT("INSERT INTO teachers(name) values(?)"),
		DELETE("DELETE FROM teachers WHERE teacher_id=?"),
		INSERTSUBJECT("INSERT INTO teachers_subjects(teacher_id,subject_id) values(?,?)"),
		UPDATE("UPDATE teachers SET name=? WHERE teacher_id=?"),
		FINDBYNAME("SELECT teacher_id, name FROM teachers WHERE name=?"),
		FINDBYID("SELECT teacher_id, name FROM teachers WHERE teacher_id=?");
		String QUERY;

		SQLTeacher(String QUERY) {
			this.QUERY = QUERY;
		}
	}
}