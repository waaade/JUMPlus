package com.cognixia.gradebook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDaoSql implements StudentDao {
	private Connection conn;

	public StudentDaoSql(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public List<Student> getAllStudent() {
		List<Student> studentList = new ArrayList<Student>();
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from student");
				) {
			while (rs.next()) {
				int id = rs.getInt("student_id");
				String name = rs.getString("student_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				
				Student student = new Student(id, name, email, password);
				studentList.add(student);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return studentList;
	}

	@Override
	public Optional<Student> getStudentById(int id) {
		try (
				PreparedStatement pstmt = conn.prepareStatement("select * from student where student_id = ?")
				) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int studentId = rs.getInt("student_id");
				String name = rs.getString("student_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				
				Student student = new Student(studentId, name, email, password);
				Optional<Student> studentFound = Optional.of(student);
				return studentFound;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.empty();
	}

	@Override
	public boolean createStudent(Student student) {
		try (PreparedStatement pstmt = 
				conn.prepareStatement("insert into student (student_name, email, password) values (?,?,?)")) {
			pstmt.setString(1, student.getName());
			pstmt.setString(2, student.getEmail());
			pstmt.setString(3, student.getPassword());
			int result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("Student added to database!");
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteStudent(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateStudent(Student student) {
		// TODO Auto-generated method stub
		return false;
	}

}
