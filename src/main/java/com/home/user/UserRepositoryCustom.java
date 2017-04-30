package com.home.user;

public interface UserRepositoryCustom {

	public User updateUserById(String id, User user);

	public User updateUserByUsername(String username, User user);

}
