package com.foxminded.university.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.SubjectDao;
import com.foxminded.university.model.Subject;

public class SubjectJdbcDao implements SubjectDao {

	private ConnecctionProvider provider;

	public SubjectJdbcDao(ConnecctionProvider provider) {
		this.provider = provider;
	}

	@Override
	public void add(Subject subject) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLSubject.INSERT.QUERY,
						Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, subject.getName());
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			resultSet.next();
			subject.setId(resultSet.getInt("subject_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Subject remove(Subject subject) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLSubject.DELETE.QUERY)) {
			statement.setInt(1, subject.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subject;
	}

	@Override
	public Subject findByName(String name) {
		Subject subject = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLSubject.FINDBYNAME.QUERY)) {
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("subject_id");
				String subjectName = resultSet.getString("name");
				subject = new Subject();
				subject.setId(id);
				subject.setName(subjectName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subject;
	}

	@Override
	public Subject findById(int id) {
		Subject subject = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLSubject.FINDBYID.QUERY)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				subject = new Subject();
				subject.setId(resultSet.getInt("subject_id"));
				subject.setName(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subject;
	}

	enum SQLSubject {
		INSERT("INSERT INTO subjects(name) values(?)"),
		DELETE("DELETE FROM subjects WHERE subject_id=?"),
		FINDBYNAME("SELECT subject_id,name FROM subjects WHERE name=?"),
		FINDBYID("SELECT subject_id,name FROM subjects WHERE subject_id=?");
		String QUERY;

		SQLSubject(String QUERY) {
			this.QUERY = QUERY;
		}
	}
}