package com.cognixia.movieratingapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveRatingMain {
	static HashMap<String, User> userList = new HashMap<String, User>();
	static ArrayList<Movie> movieList = new ArrayList<Movie>();
	
	public static void main(String[] args) {
		User currentUser = null;
		User user2 = new User("guy@email.com", "password");
		userList.put(user2.getEmail(), user2);
		
		Movie movie1 = new Movie("Napoleon Dynamite", 5, 1);
		Movie movie2 = new Movie("The Empire Strikes Back", 150, 30);
		Movie movie3 = new Movie("Coco", 0, 0);
		Movie movie4 = new Movie("Howl's Moving Castle", 45, 10);
		
		
		movieList.add(movie1);
		movieList.add(movie2);
		movieList.add(movie3);
		movieList.add(movie4);
		
		
		currentUser = loginMenu();
		
		if (currentUser != null) {
			movieSelectionMenu();
		}
	}
	
	private static User loginMenu() {
		boolean runGuest = true;
		User currentUser = null;
		
		Scanner scanner = new Scanner(System.in);
		do {
			String choice = "";
			System.out.println("+===========================================================+");
			System.out.println("|  1. REGISTER                                             |");
			System.out.println("|  2. LOGIN                                                |");
			System.out.println("|  3. VIEW MOVIES                                          |");
			System.out.println("|  4. EXIT                                                 |");
			System.out.println("+===========================================================+");
			System.out.println("Enter a command:");
			choice = scanner.nextLine();
			switch(choice) {
			case "1":
				System.out.println("REGISTER - Enter Email Address:");
				String newEmail = scanner.nextLine();
				if (validateEmail(newEmail) && !userList.containsKey(newEmail)) {
					System.out.println("Your email is " + newEmail + ". Enter a password:");
					String newPassword = scanner.nextLine();
					currentUser = new User(newEmail, newPassword);
					userList.put(newEmail, currentUser);
					System.out.println("Account created! Login to rate movies!");
				}
				else {
					System.out.println("Invalid email, or email is already registered. Please try again.");
				}
				break;
			case "2":
				// Attempt Login
				System.out.println("LOGIN - Enter Email Address: ");
				String email = scanner.nextLine();
				if (userList.containsKey(email)) {
					System.out.println("Enter Password: ");
					String password = scanner.nextLine();
					if (userList.get(email).getPassword().equals(password)) {
						currentUser = userList.get(email);
						System.out.println("Logged in!");
						runGuest = false;
					} else {
						System.out.println("Incorrect Password for user " + email);
					}
				} else {
					System.out.println("Username not found. Try again or REGISTER.");
				}
				break;
			case "3":
				for (int i = 0; i < movieList.size(); i++) {
					System.out.println(movieList.get(i).getTitle());
				}
				break;
			case "4":
				System.out.println("Exiting. Goodbye!");
				runGuest = false;
				break;
			default:
				System.out.println("Invalid input. Please enter a number from 1-4.");
			}
		} while (runGuest);
		
		return currentUser;
	}
	
	// may be expanded for guest ratings in the future
	private static void movieSelectionMenu() {
		boolean run = true;
		Scanner scanner = new Scanner(System.in);
		
		do {
			int choice = 0;
			
			// Header
			System.out.println("+===========================================================+");
			System.out.println("|  Movie                       Avg. Rating    # of Ratings |");
			
			// Movie List
			for (int i = 0; i < movieList.size(); i++) {
				Movie current = movieList.get(i);
				if (current.getNumOfRatings() > 0) {
					System.out.println("|  " + (i + 1) + ". " + 
							String.format("%-24s %-14.2f %-13s", 
							current.getTitle(), 
							current.getAverageRating(),
							current.getNumOfRatings()) + "|");
				} else {
					System.out.println("|  " + (i + 1) + ". " + 
							String.format("%-24s %-14s %-13s", 
							current.getTitle(), 
							"N/A",
							current.getNumOfRatings()) + "|");
				}
				
			}
			
			// The final option is to exit, but the number varies based on how many movies are in memory
			Integer exitCommandNum = (Integer)(movieList.size() + 1);
			String exitCommandString = exitCommandNum.toString(); 
			System.out.println("|  " + exitCommandString + ". " + String.format("%-53s", "EXIT") + "|");
			System.out.println("+===========================================================+");
			System.out.println("Enter the number of a movie you want to rate, or " + exitCommandString + " to exit.");
			choice = scanner.nextInt();
			if (choice == exitCommandNum) {
				System.out.println("Exiting. Goodbye!");
				run = false;
			} else if (choice < 1 || choice > exitCommandNum) {
				System.out.println("Invalid command.");
			} else {
				rateMovie(choice - 1); // can't forget to subtract 1 to match array index
			}
		} while (run);
		scanner.close();
		
	}

	private static void rateMovie(int choice) {
		Movie movieToRate = movieList.get(choice);
		System.out.println("+===========================================================+");
		System.out.println("|  Movie: " + String.format("%-48s", movieToRate.getTitle()) + "|");
		System.out.println("|                                                         |");
		System.out.println("|  Rating:                                                |");
		System.out.println("|  0. Awful                                               |");
		System.out.println("|  1. Bad                                                 |");
		System.out.println("|  2. Not good                                            |");
		System.out.println("|  3. Good                                                |");
		System.out.println("|  4. Great                                               |");
		System.out.println("|  5. Fantastic                                           |");
		System.out.println("|                                                         |");
		System.out.println("|  6. EXIT                                                |");
		System.out.println("+===========================================================+");
		
		int rating;
		Scanner scanner = new Scanner(System.in);
		
		rating = scanner.nextInt();
		
		movieToRate.addRating(rating);
		movieList.set(choice, movieToRate);
		System.out.println("Rating added.");
	}

	private static boolean validateEmail(String email) {
		Pattern emailPattern = Pattern.compile("^\\S+@\\S+$");
		Matcher emailMatcher = emailPattern.matcher(email);
		return emailMatcher.find();
	}
}
