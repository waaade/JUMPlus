package com.cognixia.movieratingapp;

import java.util.HashMap;

public class User {
	private String email;
	private String password;
	private HashMap<String, Integer> ratings;
	
	public HashMap<String, Integer> getRatings() {
		return ratings;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User() {
		
	}
	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
		this.ratings = new HashMap<String, Integer>();
	}
	
	public void addRating(String movieTitle, int rating) {
		this.ratings.put(movieTitle, rating);
	}
	
	
}
