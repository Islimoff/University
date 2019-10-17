package com.foxminded.university.dao;

import com.foxminded.university.model.Audience;

public interface AudienceDao extends GeneralDao<Audience>{
	
	Audience findByNumber(int number);
}
