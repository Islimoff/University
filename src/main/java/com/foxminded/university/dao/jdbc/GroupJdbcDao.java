package com.foxminded.university.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.model.Group;
import com.foxminded.university.model.Student;

public class GroupJdbcDao implements GroupDao {

	private ConnecctionProvider provider;

	public GroupJdbcDao(ConnecctionProvider provider) {
		this.provider = provider;
	}

	public void add(Group group) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLGroup.INSERT.QUERY)) {
			statement.setString(1, group.getGroupName());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove(Group group) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLGroup.DELETE.QUERY)) {
			statement.setInt(1, group.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Group findByName(String name) {
		Group group = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLGroup.FINDBYNAME.QUERY)) {
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
				PreparedStatement statement = connection.prepareStatement(SQLGroup.FINDBYID.QUERY)) {
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
				PreparedStatement statement = connection.prepareStatement(SQLGroup.FINDBYSTUDENT.QUERY)) {
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

	enum SQLGroup {
		INSERT("INSERT INTO groups(name) values(?)"), DELETE("DELETE FROM groups WHERE group_id=?"),
		FINDBYNAME("SELECT group_id,name FROM groups where name=?"),
		FINDBYID("SELECT group_id,name FROM groups where group_id=?"),
		FINDBYSTUDENT("SELECT group_id,name FROM groups WHERE group_id= (SELECT group_id FROM students WHERE name=?)");
		String QUERY;

		SQLGroup(String QUERY) {
			this.QUERY = QUERY;
		}
	}
}