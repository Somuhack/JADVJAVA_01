package com.example.usermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // Say to spring boot this class is a Table
@Table(name = "users") // if you need to change table name expected to give class name as a table name

public class User {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String name ;
    public User (){}
	public User(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	
	}
	
	@Column(unique = true)
	private String email;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}
	

	
}
