package com.cognixia.gradebook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.cognixia.gradebook.dao.ClassDaoSql;
import com.cognixia.gradebook.dao.ClassStudent;
import com.cognixia.gradebook.dao.ClassStudentDaoSql;
import com.cognixia.gradebook.dao.SchoolClass;
import com.cognixia.gradebook.dao.StudentDaoSql;
import com.cognixia.gradebook.dao.StudentGrade;
import com.cognixia.gradebook.dao.Teacher;
import com.cognixia.gradebook.dao.TeacherDaoSql;
import com.cognixia.gradebook.dao.Student;


public class GradebookRunner {

	public static void main(String[] args) {
		try {
			Connection connection = ConnectionManager.getConnection();
			
			boolean run = true;
			Scanner scanner = new Scanner(System.in);
			System.out.println("Welcome to the Grade Book.");
			TeacherDaoSql td = new TeacherDaoSql(connection);
			while (run) {
				System.out.println("1. Teacher Login");
				System.out.println("2. Student Login (Coming Soon)");
				System.out.println("3. Create a New Account");
				System.out.println("4. Quit");
				System.out.println("Enter a command:");
				String command = scanner.nextLine();
				switch(command) {
				case "1":
					System.out.println("Email:");
					String email = scanner.nextLine();
					System.out.println("Password:");
					String password = scanner.nextLine();
					Optional<Teacher> teacher = td.getTeacherByLogin(email, password);
					if (teacher.isEmpty()) {
						System.out.println("Invalid credentials. Please attempt login again, or create an account.");
					}
					else {
						System.out.println("Login successful!");
						displayTeacherMenu(teacher.get(), connection);
					}
					break;
				case "2":
					System.out.println("Student login coming soon. Sorry!");
					break;
				case "3":
					System.out.println("CREATE ACCOUNT");
					System.out.println("Your Name:");
					String newName = scanner.nextLine();
					System.out.println("Your Email (You will use this to log in):");
					String newEmail = scanner.nextLine();
					System.out.println("Password:");
					String newPassword = scanner.nextLine();
					Teacher newTeacher = new Teacher(newName, newEmail, newPassword);
					td.createTeacher(newTeacher);
					break;
				case "4":
					System.out.println("Quitting. Goodbye!");
					run = false;
					break;
				default:
					System.out.println("Invalid Command. Please try again or enter 4 to quit.");
				}
				
			}
			scanner.close();
		
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
	
	private static void displayTeacherMenu(Teacher userTeacher, Connection connection) {
		boolean run = true;
		ClassDaoSql cd = new ClassDaoSql(connection);
		List<SchoolClass> myClasses = cd.getClassByTeacherId(userTeacher.getId());
		
		while (run) {
			System.out.println("YOUR CLASSES:");
			System.out.println("----------------------------");
			int num = 0;         // used for the menu numbers
			if (!myClasses.isEmpty()) {
				for (int i = 0; i < myClasses.size(); i++) {
					num = i + 1;
					System.out.println(num + ". " + myClasses.get(i).getClassName());
				}
			}
			else {
				System.out.println("You have no classes yet!");
			}
			
			System.out.println("----------------------------");
			num++;
			System.out.println(num + ". CREATE NEW CLASS");
			num++;
			System.out.println(num + ". LOGOUT");
			
			System.out.println("Select an option:");
			Scanner scanner = new Scanner(System.in);
			int choice = 0;
			choice = scanner.nextInt();
			scanner.nextLine();
			
			if (choice == (num - 1)) {
				System.out.println("CREATE NEW CLASS");
				System.out.println("Enter class name:");
				String name = scanner.nextLine();
				SchoolClass newClass = new SchoolClass(userTeacher.getId(), name);
				cd.createClass(newClass);
				myClasses = cd.getClassByTeacherId(userTeacher.getId());
			}
			else if (choice == num) {
				System.out.println("Logging out.");
				run = false;
			}
			else if (choice < 1 || choice > num) {
				System.out.println("Invalid command.");
			}
			else {
				int classId = myClasses.get(choice - 1).getId();
				displayClassMenu(classId, connection);
			}
		}
	}

	private static void displayClassMenu(int classId, Connection connection) {
		boolean orderByGrade = false; // The user can toggle between ordering by name or grade.
		ClassStudentDaoSql csd = new ClassStudentDaoSql(connection);
		
		boolean run = true;
		Scanner scanner = new Scanner(System.in);
		while (run) {
			List<StudentGrade> classGrades = csd.getStudentsByClassId(classId, orderByGrade);
			int choice = 0;
			System.out.println("YOUR CLASS:");
			System.out.println("----------------------------");
			int num = 0;         // used for the menu numbers
			if (!classGrades.isEmpty()) {
				for (int i = 0; i < classGrades.size(); i++) {
					num = i + 1;
					System.out.println(num + ". " + String.format("%-22s", classGrades.get(i).getName())
							 + classGrades.get(i).getGrade() );
				}
			} else {
				System.out.println("No students yet!");
			}
			System.out.println("----------------------------");
			System.out.println();
			num++;
			if (orderByGrade) {
				System.out.println(num + ". SORT BY NAME (A-Z)");
			} else {
				System.out.println(num + ". SORT BY GRADE (HIGHEST TO LOWEST)");
			}
			System.out.println((num + 1) + ". VIEW CLASS AVERAGE");
			System.out.println((num + 2) + ". VIEW CLASS MEDIAN");
			
			System.out.println((num + 3) + ". ADD STUDENT");
			System.out.println((num + 4) + ". GO BACK");
			System.out.println();
			System.out.println("Select an option:");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			// Order by
			if (choice == num) {
				orderByGrade = !orderByGrade;
				classGrades = csd.getStudentsByClassId(classId, orderByGrade);
			}
			// Average
			else if (choice == (num + 1)) {
				if (!classGrades.isEmpty()) {
					System.out.println("The average grade of this class is " + getClassAverage(classGrades) + ".");
					System.out.println("Press enter to continue.");
					scanner.nextLine();
				}
				else {
					System.out.println("No students in class!");
				}
				
			}
			// Median
			else if (choice == (num + 2)) {
				if (!orderByGrade) {
					classGrades = csd.getStudentsByClassId(classId, true); // Must be ordered by grade to get median
				}
				System.out.println("The median grade of this class is " + getClassMedian(classGrades) + ".");
				System.out.println("Press enter to continue.");
				scanner.nextLine();
			}
			// Add student
			else if (choice == (num + 3)) {
				addStudentToClass(classId, connection);
			}
			// Back
			else if (choice == (num + 4)) {
				System.out.println("Returning to class list.");
				run = false;
			}
			// Error
			else if (choice < 1 || choice > (num + 4)) {
				System.out.println("Invalid command.");
			}
			
			else {
				int studentId = classGrades.get(choice - 1).getStudentId();
				String studentName = classGrades.get(choice - 1).getName();
				displayStudentGradeMenu(studentId, classId, studentName, connection);
			}
		}
		
		
	}

	private static void displayStudentGradeMenu(int studentId, int classId, String studentName, Connection connection) {
		ClassStudentDaoSql csd = new ClassStudentDaoSql(connection);
		Optional<ClassStudent> classStudent = csd.getByClassAndStudentId(classId, studentId);
		if (classStudent.isPresent()) {
			System.out.println("STUDENT: " + studentName);
			System.out.println("GRADE: " + classStudent.get().getGrade());
			System.out.println();
			System.out.println("1. Update Grade");
			System.out.println("2. Remove Student From This Class");
			System.out.println("3. Go Back");
			Scanner scanner = new Scanner(System.in);
			String choice = scanner.nextLine();
			
			switch (choice) {
			case "1":
				System.out.println("Enter a new grade for " + studentName + ":");
				int newGrade;
				newGrade = scanner.nextInt();
				scanner.nextLine();
				ClassStudent updatedCs = new ClassStudent(classId, studentId, newGrade);
				if (csd.updateClassStudent(updatedCs)) {
					System.out.println("Updated Grade.");
				}
				else {
					System.out.println("A problem occured updating this grade.");
				}
				break;
			case "2":
				System.out.println("Confirm: Do you want to remove student " + studentName + " from this class?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				String confirm = scanner.nextLine();
				if (confirm.equals("1")) {
					csd.deleteClassStudent(classStudent.get().getId());
					System.out.println("Student removed from class.");
				}
				break;
			case "3":
				System.out.println("Returning.");
			}
			
		}
		else {
			System.out.println("A problem occured finding this student's records.");
		}
		
	}

	private static float getClassAverage(List<StudentGrade> classGrades) {
		int total = 0;
		for (int i = 0; i < classGrades.size(); i++) {
			total += classGrades.get(i).getGrade();
		}
		
		return ((float)total / (float)classGrades.size());
	}
	
	private static int getClassMedian(List<StudentGrade> classGrades) {
		int middleIndex = (classGrades.size() / 2) - 1;
		return (classGrades.get(middleIndex).getGrade());
	}
	
	private static void addStudentToClass(int classId, Connection connection) {
		StudentDaoSql sd = new StudentDaoSql(connection);
		ClassStudentDaoSql csd = new ClassStudentDaoSql(connection);
		Scanner scanner = new Scanner(System.in);
		boolean run = true;
		
		while (run) {
			System.out.println("Choose a student to add to this class:");
			
			// List of all students, can select which to add
			List<Student> allStudents = sd.getAllStudent();
			int num = 0;
			for (int i = 0; i < allStudents.size(); i++) {
				num = i + 1;
				System.out.println(num + ". " + allStudents.get(i).getName());
			}
			System.out.println();
			System.out.println((num + 1) + ". ADD NEW STUDENT");
			System.out.println((num + 2) + ". GO BACK");
			
			System.out.println("Enter a command:");
			
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			if (choice == num + 1) {
				// Option to create new student (user must still add manually to class)
				System.out.println("Enter the student's name:");
				String name = scanner.nextLine();
				System.out.println("Student's email:");
				String email = scanner.nextLine();
				System.out.println("Password for student:");
				String password = scanner.nextLine();
				
				Student newStudent = new Student(name, email, password);
				sd.createStudent(newStudent);
			}
			else if (choice == num + 2) {
				System.out.println("Returning.");
				run = false;
			}
			else {
				// Add a student to this class
				Student studentToAdd = allStudents.get(choice - 1);
				System.out.println("Adding student " + studentToAdd.getName() + " to this class.");
				System.out.println("What is their current grade? (0 - 100)");
				int grade = scanner.nextInt();
				scanner.nextLine();
				ClassStudent enrollment = new ClassStudent(classId, studentToAdd.getId(), grade);
				csd.createClassStudent(enrollment);
				System.out.println("Student added to class!");
			}
		}
	}
}
