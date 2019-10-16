package com.foxminded.university.service;

import com.foxminded.university.dao.AudienceDao;
import com.foxminded.university.dao.ConnecctionProvider;
import com.foxminded.university.dao.jdbc.AudienceJdbcDao;
import com.foxminded.university.model.Audience;

public class AudienceService {

	private ConnecctionProvider provider = new ConnecctionProvider();
	private AudienceDao audienceDao = new AudienceJdbcDao(provider);

	public void addAudience(Audience audience) {
		audienceDao.add(audience);
	}

	public void removeAudience(Audience audience) {
		audienceDao.remove(audience);
	}

	public Audience findAudienceByNumber(int number) {
		return audienceDao.findByNumber(number);
	}

	public Audience findAudienceById(int id) {
		return audienceDao.findById(id);
	}
}
