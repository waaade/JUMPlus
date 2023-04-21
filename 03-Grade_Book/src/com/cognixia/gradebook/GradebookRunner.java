package com.cognixia.gradebook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.cognixia.gradebook.dao.ClassDaoSql;
import com.cognixia.gradebook.dao.SchoolClass;
import com.cognixia.gradebook.dao.Teacher;
import com.cognixia.gradebook.dao.TeacherDaoSql;


public class GradebookRunner {

	public static void main(String[] args) {
		try {
			Connection connection = ConnectionManager.getConnection();
			System.out.println("Connection made!");
//			Teacher testTeacher = new Teacher("Buzz Hickey", "buzzhickey@greendale.edu", "123456");
//			TeacherDaoSql td = new TeacherDaoSql(connection);
//			td.createTeacher(testTeacher);
			
//			List<Teacher> allTeachers = td.getAllTeacher();
//			for (int i = 0; i < allTeachers.size(); i++) {
//				System.out.println(allTeachers.get(i));
//			}
			
		
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
