# create database gradebook;

use gradebook;

CREATE TABLE student (
	student_id int PRIMARY KEY auto_increment,
	student_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

INSERT INTO student VALUES(null, 'Abe Lincoln', 'sixteen@email.com', 'awesome123');
INSERT INTO student VALUES(null, 'Jeff Winger', 'jeffwinger@greendale.edu', '123456');
INSERT INTO student VALUES(null, 'Britta Perry', 'brittaperry@greendale.edu', '123456');
INSERT INTO student VALUES(null, 'Annie Edison', 'annieedison@greendale.edu', '123456');
INSERT INTO student VALUES(null, 'Pierce Hawthorne', 'piercehawthorne@greendale.edu', '123456');
INSERT INTO student VALUES(null, 'Troy Barnes', 'troybarnes@greendale.edu', '123456');
INSERT INTO student VALUES(null, 'Abed Nadir', 'abednadir@greendale.edu', '123456');
INSERT INTO student VALUES(null, 'Shirley Bennett', 'shirleybennett@greendale.edu', '123456');
  
select * from student;

CREATE TABLE teacher (
	teacher_id int PRIMARY KEY auto_increment,
    teacher_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

INSERT INTO teacher VALUES(null, 'Professor Professorsen', 'realprofessor@email.com', '123456');
INSERT INTO teacher VALUES(null, 'Ben Chang', 'benchang@greendale.edu', '123456');
INSERT INTO teacher VALUES(null, 'June Bauer', 'junebauer@greendale.edu', '123456');
INSERT INTO teacher VALUES(null, 'Ian Duncan', 'ianduncan@greendale.edu', '123456');
INSERT INTO teacher VALUES(null, 'Professor Kane', 'kane@greendale.edu', '123456');

select * from teacher;

CREATE TABLE class (
	class_id int PRIMARY KEY auto_increment,
    teacher_id int NOT NULL,
    class_name VARCHAR(50),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
);

INSERT INTO class VALUES(null, 1, "Conspiracy Theories 204");
INSERT INTO class VALUES(null, 2, "Spanish 101");
INSERT INTO class VALUES(null, 3, "Anthropology 101");
INSERT INTO class VALUES(null, 4, "Psychology 230");
INSERT INTO class VALUES(null, 5, "Biology 101");

select * from class;

CREATE TABLE class_student (
	class_student_id int PRIMARY KEY AUTO_INCREMENT,
	class_id int NOT NULL,
    student_id int NOT NULL,
    class_student_grade int,
    FOREIGN KEY (class_id) REFERENCES class(class_id),
    FOREIGN KEY (student_id) REFERENCES student(student_id)
);
  
INSERT INTO class_student VALUES(null, 1, 1, 97);
INSERT INTO class_student VALUES(null, 1, 2, 100);
INSERT INTO class_student VALUES(null, 2, 3, 74);
INSERT INTO class_student VALUES(null, 2, 2, 70);
INSERT INTO class_student VALUES(null, 2, 4, 98);
INSERT INTO class_student VALUES(null, 2, 5, 83);
INSERT INTO class_student VALUES(null, 2, 6, 79);
INSERT INTO class_student VALUES(null, 2, 7, 81); 

INSERT INTO class_student VALUES(null, 4, 4, 95);


SELECT * FROM class_student;

# Select student names and grades from a single class
SELECT 
    student.student_name, student.student_id, class_student.class_student_grade
FROM
    class_student
        INNER JOIN
    student ON class_student.student_id = student.student_id
WHERE
    class_student.class_id = 1
ORDER BY class_student.class_student_grade DESC;