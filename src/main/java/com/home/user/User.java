package com.home.user;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public final class User {

	@Id
	private String id;

	@NotNull
	@Indexed(unique = true)
	@Length(min = 8, message = "The field must be at least 8 characters")
	private String username;

	@Length(min = 8, message = "The field must be at least 8 characters")
	@JsonIgnore
	private String password;

	@NotNull
	private String firstname;

	@NotNull
	private String lastname;

	@Past
	private Date dob;

	public User() {
	}

	public User(String username, String password, String firstname, String lastname, Date dob) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", " + " firstname=" + firstname + ", lastname=" + lastname
				+ ", dob=" + dob + "]";
	}

}
