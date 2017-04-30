package com.home.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Query;

import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class UserRepositoryImpl implements UserRepositoryCustom {
	@Autowired
	MongoTemplate mongoTemplate;

	static interface Properties {
		String ID = "_id";
		String USERNAME = "username";
		String PASSWORD = "password";
		String FIRSTNAME = "firstname";
		String LASTNAME = "lastname";
		String DOB = "dob";
	}

	@Override
	public User updateUserById(String id, User user) {

		Update update = new Update();
		update.set(Properties.FIRSTNAME, user.getFirstname());
		update.set(Properties.LASTNAME, user.getLastname());
		update.set(Properties.PASSWORD, user.getPassword());
		update.set(Properties.DOB, user.getDob());

		return mongoTemplate.findAndModify(new Query(where(Properties.ID).is(id)), update,
				FindAndModifyOptions.options().upsert(false).returnNew(true), User.class);
	}

	@Override
	public User updateUserByUsername(String username, User user) {

		Update update = new Update();
		update.set(Properties.FIRSTNAME, user.getFirstname());
		update.set(Properties.LASTNAME, user.getLastname());
		update.set(Properties.PASSWORD, user.getPassword());
		update.set(Properties.DOB, user.getDob());

		return mongoTemplate.findAndModify(new Query(where(Properties.USERNAME).is(username)), update,
				FindAndModifyOptions.options().upsert(false).returnNew(true), User.class);
	}

}
