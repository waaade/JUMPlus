package com.cognixia.gradebook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class GradebookRunner {

	public static void main(String[] args) {
		try {
			Connection connection = ConnectionManager.getConnection();
			System.out.println("Connection made!");
		
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't load detail for connection, can't make connection");
		} catch (ClassNotFoundException e) {
			System.out.println("Couldn't load driver, can't make connection");
		} catch (IOException e) {
			System.out.println("Couldn't load connection details, can't make connection");
		} catch (SQLException e) {
			System.out.println("Couldn't connect to the db");
		}
	}

}
