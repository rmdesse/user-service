package com.home.user;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

	public User findByUsername(String username);

	public List<User> findByFirstname(String firstname);

	public List<User> findByLastname(String lastame);

	public void deleteByUsername(String username);

	public Long countByUsername(String username);

}
