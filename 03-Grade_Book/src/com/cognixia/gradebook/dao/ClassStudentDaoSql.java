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
	public List<StudentGrade> getStudentsByClassId(int classId, boolean orderByGrade) {
		List<StudentGrade> studentList = new ArrayList<StudentGrade>();
		String query = "select student.student_name, student.student_id, class_student.class_student_grade "
				+ "FROM class_student INNER JOIN student ON class_student.student_id=student.student_id "
				+ "WHERE class_student.class_id =?";
		if (orderByGrade) {
			query += " ORDER BY class_student.class_student_grade DESC";
		}
		else {
			query += " ORDER BY student.student_name";
		}
		try( PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, classId);
			ResultSet rs = pstmt.executeQuery();
			while( rs.next() ) {
				// iterate through all the values in the row
				String name = rs.getString("student_name");
				int id = rs.getInt("student_id");
				int grade = rs.getInt("class_student_grade");
				
				StudentGrade studentGrade = new StudentGrade(name, id, grade);
				studentList.add(studentGrade);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return studentList;
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
		try (PreparedStatement pstmt = conn.prepareStatement("delete from class_student where class_student_id = ?")) {
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
		try (PreparedStatement pstmt = conn.prepareStatement("update class_student SET class_student_grade=? where class_id=? AND student_id=?")) {
			pstmt.setInt(1, classStudent.getGrade());
			pstmt.setInt(2, classStudent.getClassId());
			pstmt.setInt(3, classStudent.getStudentId());
			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Optional<ClassStudent> getByClassAndStudentId(int classId, int studentId) {
		try (
				PreparedStatement pstmt = conn.prepareStatement("select * from class_student where class_id =? AND student_id =?") 
				) {
			pstmt.setInt(1, classId);
			pstmt.setInt(2, studentId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int classStudentId = rs.getInt("class_student_id");
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

}
