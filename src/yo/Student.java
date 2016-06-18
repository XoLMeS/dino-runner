package yo;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;

public class Student {

	String name,
	last_name,
	faculty;
	
	int age,
	course;
	
	public Student(String name, String last_name,String faculty, int age, int course){
		this.name = name;
		this.last_name = last_name;
		this.faculty = faculty;
		this.age = age;
		this.course = course;
	}
	
	public Entity getStudentEntity(){
		Entity student = new Entity("Student");
		student.setProperty("Name", name);
		student.setProperty("Last_Name", last_name);
		student.setProperty("Faculty", faculty);
		student.setProperty("Age", age);
		student.setProperty("course", course);
		Date date = new Date();
		student.setProperty("date", date.getDate());
		return student;
	}
}
