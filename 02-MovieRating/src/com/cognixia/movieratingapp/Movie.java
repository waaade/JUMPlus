package com.cognixia.movieratingapp;

public class Movie {
	private String title;
	private int ratings;
	private int numOfRatings;
	
	
	public Movie(String title) {
		this.title = title;
		this.ratings = 0;
		this.numOfRatings = 0;
	}
	
	public Movie(String title, int ratings, int numOfRatings) {
		super();
		this.title = title;
		this.ratings = ratings;
		this.numOfRatings = numOfRatings;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRatings() {
		return ratings;
	}

	public void addRating(int rating) {
		this.ratings += rating;
		this.numOfRatings++;
	}

	public int getNumOfRatings() {
		return numOfRatings;
	}

	public void setNumOfRatings(int numOfRatings) {
		this.numOfRatings = numOfRatings;
	}
	
	public float getAverageRating() {
		return (this.numOfRatings > 0) ? ((float)this.ratings / (float)this.numOfRatings) : -1.0f;
	}

	@Override
	public String toString() {
		return "Movie [title=" + title + ", ratings=" + ratings + ", numOfRatings=" + numOfRatings + "]";
	}
	
	
	
}
