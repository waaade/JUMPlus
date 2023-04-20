package com.cognixia.gradebook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherDaoSql implements TeacherDao {
	private Connection conn;
	
	

	public TeacherDaoSql(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public List<Teacher> getAllTeacher() {
		List<Teacher> teacherList = new ArrayList<Teacher>();
		
		try( Statement stmt = conn.createStatement(); 
			 ResultSet rs = stmt.executeQuery("select * from teacher");
				) {
			
			while( rs.next() ) {
				// iterate through all the values in the row
				int id = rs.getInt("teacher_id");
				String name = rs.getString("teacher_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				
				Teacher teacher = new Teacher(id, name, email, password);
				
				teacherList.add(teacher);
			}
 			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return teacherList;

	}

	@Override
	public Optional<Teacher> getTeacherById(int id) {
		try (
			PreparedStatement pstmt = conn.prepareStatement("select * from teacher where teacher_id = ?") ) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int teacherId = rs.getInt("teacher_id");
				String name = rs.getString("teacher_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				rs.close();
				Teacher teacher = new Teacher(teacherId, name, email, password);
				Optional<Teacher> teacherFound = Optional.of(teacher);
				return teacherFound;
			}
			else {
				rs.close();
				return Optional.empty();
			}
			
		} catch (SQLException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean createTeacher(Teacher teacher) {
		try (PreparedStatement pstmt = 
				conn.prepareStatement("insert into teacher (teacher_name, email, password) values (?,?,?)")) {
			pstmt.setString(1, teacher.getName());
			pstmt.setString(2, teacher.getEmail());
			pstmt.setString(3, teacher.getPassword());
			int result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("Teacher added to database!");
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
	public boolean deleteTeacher(int id) {
		try (PreparedStatement pstmt = conn.prepareStatement("delete * from teacher where teacher_id = ?")) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.rowDeleted()) {
				rs.close();
				return true;
			} 
			else {
				rs.close();
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		return false;
	}

}
