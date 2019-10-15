package com.foxminded.university.dao.jdbc;

import com.foxminded.university.dao.AudienceDao;
import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.model.Audience;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AudienceJdbcDao implements AudienceDao {

	private ConnecctionProvider provider;

	public AudienceJdbcDao(ConnecctionProvider provider) {
		this.provider = provider;
	}

	@Override
	public Audience add(Audience audience) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLAudience.INSERT.QUERY,
						Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, audience.getNumber());
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			resultSet.next();
			audience.setId(resultSet.getInt("audience_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return audience;
	}

	@Override
	public void remove(Audience audience) {
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLAudience.DELETE.QUERY)) {
			statement.setInt(1, audience.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Audience findByNumber(int number) {
		Audience audience = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLAudience.FINDBYNUMBER.QUERY)) {
			statement.setInt(1, number);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				audience = new Audience();
				audience.setId(resultSet.getInt("audience_id"));
				audience.setNumber(resultSet.getInt("number"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return audience;
	}

	@Override
	public Audience findById(int id) {
		Audience audience = null;
		try (Connection connection = provider.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQLAudience.FINDBYID.QUERY)) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				audience = new Audience();
				audience.setId(resultSet.getInt("audience_id"));
				audience.setNumber(resultSet.getInt("number"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return audience;
	}

	enum SQLAudience {
		INSERT("INSERT INTO audiences(number) values(?)"),
		DELETE("DELETE FROM audiences WHERE audience_id=?"),
		FINDBYNUMBER("SELECT audience_id, number FROM audiences where number=?"),
		FINDBYID("SELECT audience_id, number FROM audiences where number=?");
		String QUERY;

		SQLAudience(String QUERY) {
			this.QUERY = QUERY;
		}
	}
}