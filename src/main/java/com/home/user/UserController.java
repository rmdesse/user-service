package com.home.user;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/user")
public final class UserController {

	@Autowired
	private UserRepository repository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	User create(@RequestBody @Valid User userEntry) {
		logger.info(userEntry.toString());
		return repository.insert(userEntry);
	}

	@RequestMapping(value = "/username/{username}", method = RequestMethod.DELETE)
	@CacheEvict(value = "user", key = "#username")
	void deleteByUsername(@PathVariable("username") String username) {
		repository.deleteByUsername(username);
	}

	@RequestMapping(method = RequestMethod.GET)
	List<User> findAll() {
		return repository.findAll();
	}

	@RequestMapping(value = "username/{username}", method = RequestMethod.GET)
	@Cacheable(value = "user", key = "#username")
	User findByUserName(@PathVariable("username") String userName) {
		return repository.findByUsername(userName);
	}

	@RequestMapping(value = "firstname/{firstname}", method = RequestMethod.GET)
	List<User> findByFirstName(@PathVariable("firstname") String firstName) {
		return repository.findByFirstname(firstName);
	}

	@RequestMapping(value = "lastname/{lastname}", method = RequestMethod.GET)
	List<User> findByLastName(@PathVariable("lastname") String lastName) {
		return repository.findByLastname(lastName);
	}

	@RequestMapping(value = "username/{username}", method = RequestMethod.PUT)
	@CachePut(value = "user", key = "#username")
	User updateByUsername(@PathVariable("username") String username, @RequestBody @Valid User userEntry) {
		return repository.updateUserByUsername(username, userEntry);
	}

	@ExceptionHandler(org.springframework.dao.DuplicateKeyException.class)
	@ResponseStatus(HttpStatus.LOCKED)
	public void handleDuplicateUserInsert(Exception ex) {

		logger.info("User already exists!");

	}

	@ExceptionHandler()
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleUserNotFound(Exception ex) {

		logger.info("General Error!");
		ex.printStackTrace();

	}

}
