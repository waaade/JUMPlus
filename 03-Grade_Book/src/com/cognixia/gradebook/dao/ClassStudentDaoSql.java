package com.cognixia.gradebook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassStudentDaoSql implements ClassStudentDao {
	private Connection conn;
	
	public ClassStudentDaoSql(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public List<ClassStudent> getAllClassStudent() {
		List<ClassStudent> classStudentList = new ArrayList<ClassStudent>();
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from class_student");
				) {
			while (rs.next()) {
				int id = rs.getInt("class_student_id");
				int classId = rs.getInt("class_id");
				int studentId = rs.getInt("student_id");
				int grade = rs.getInt("class_student_grade");
				
				ClassStudent classStudent = new ClassStudent(id, classId, studentId, grade);
				
				classStudentList.add(classStudent);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classStudentList;
	}

	@Override
	public Optional<ClassStudent> getClassStudentById(int id) {
		try (
				PreparedStatement pstmt = conn.prepareStatement("select * from teacher where class_student_id = ?") 
				) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int classStudentId = rs.getInt("class_student_id");
				int classId = rs.getInt("class_id");
				int studentId = rs.getInt("student_id");
				int grade = rs.getInt("class_student_grade");
				rs.close();
				ClassStudent classStudent = new ClassStudent(classStudentId, classId, studentId, grade);
				Optional<ClassStudent> classStudentFound = Optional.of(classStudent);
				return classStudentFound;
			}
		} catch (SQLException e) {
				e.printStackTrace();
				return Optional.empty();
		}
		return Optional.empty();
	}

	@Override
	public boolean createClassStudent(ClassStudent classStudent) {
		try (PreparedStatement pstmt =
				conn.prepareStatement("insert into class_student (class_id, student_id, class_student_grade) values (?,?,?)")
				) {
			pstmt.setInt(1, classStudent.getClassId());
			pstmt.setInt(2, classStudent.getStudentId());
			pstmt.setInt(3, classStudent.getGrade());
			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteClassStudent(int id) {
		try (PreparedStatement pstmt = conn.prepareStatement("delete * from class_student where class_student_id = ?")) {
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			} 
			else {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateClassStudent(ClassStudent classStudent) {
		// TODO Auto-generated method stub
		return false;
	}

}
