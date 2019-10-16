package com.foxminded.university.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;

public class GroupJdbcDao implements GroupDao {

	private ConnecctionProvider provider;
	private static final String INSERT = "INSERT INTO groups(name) values(?)";
	private static final String DELETE = "DELETE FROM groups WHERE group_id=?";
	private static final String FINDBYNAME = "SELECT group_id,name FROM groups where name=?";
	private static final String FINDBYID = "SELECT group_id,name FROM groups where group_id=?";
	private static final String FINDBYSTUDENT = "SELECT group_id,name FROM groups WHERE group_id= (SELECT group_id FROM students WHERE name=?)";

	public GroupJdbcDao(ConnecctionProvider provider) {
		this.provider = provider;
	}

	public Group add(Group group) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, group.getGroupName());
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			resultSet.next();
			group.setId(resultSet.getInt("group_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}

	public void remove(Group group) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE)) {
			statement.setInt(1, group.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Group findByName(String name) {
		Group group = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(FINDBYNAME)) {
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				group = new Group();
				group.setId(resultSet.getInt("group_id"));
				group.setGroupName(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}

	public Group findById(int id) {
		Group group = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(FINDBYID)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				group = new Group();
				group.setId(resultSet.getInt("group_id"));
				group.setGroupName(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}

	public Group findByStudent(Student student) {
		Group group = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(FINDBYSTUDENT)) {
			statement.setString(1, student.getName());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				group = new Group();
				group.setId(resultSet.getInt("group_id"));
				group.setGroupName(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}
}