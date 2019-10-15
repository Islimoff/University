package com.foxminded.university.dao;

import com.foxminded.university.model.Audience;

public interface AudienceDao {

	Audience add(Audience audience);

	void remove(Audience audience);

	Audience findByNumber(int number);

	Audience findById(int id);

}
