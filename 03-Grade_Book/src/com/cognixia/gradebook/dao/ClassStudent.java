package com.cognixia.gradebook.dao;

/**
 * @author John Larson
 * Represents a student's enrollment in a class and their grade.
 */
public class ClassStudent {
	private int id;
	private int classId;
	private int studentId;
	private int grade;
	
	public ClassStudent(int id, int classId, int studentId, int grade) {
		super();
		this.id = id;
		this.classId = classId;
		this.studentId = studentId;
		this.grade = grade;
	}
	
	public ClassStudent(int classId, int studentId, int grade) {
		super();
		this.id = 0;
		this.classId = classId;
		this.studentId = studentId;
		this.grade = grade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "ClassStudent [id=" + id + ", classId=" + classId + ", studentId=" + studentId + ", grade=" + grade
				+ "]";
	}
	
	
}
