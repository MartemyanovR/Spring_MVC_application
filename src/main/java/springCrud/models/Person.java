package springCrud.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class Person {
	
	private int id;
	
	@NotNull
	@Size(min = 2, max = 15 , message = "Name should be between 2 and 30 characters")	
	private String name;
		
	@NotNull(message="Not empty")
	@Size(min = 2, max = 20 , message = "Name should be between 2 and 30 characters")
	private String surname;
	

	@Min(value = 0, message = "Age should be greater than 0")
	private int age;
	
	@NotNull(message="Not empty")
	@javax.validation.constraints.Email(message = "Email should be valid")
	private String email;
	
	public Person() {
		
	}
	
	public Person(int id, String name, String surname, int age, String email) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.email = email;
	}
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 	

	
	
}
