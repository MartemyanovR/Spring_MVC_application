package springCrud.dao;

import java.util.List;

import springCrud.models.Person;

public interface PersonSQLDAO {	
	
	//create table
	void addTable();

	//create
	void addPerson(Person person);
	
	//read
	List<Person> getAllPeople();
	
	//get by id
	Person getById(int id);
	
	//update
	void update(int id, Person person);	
	
	//remove
	void remove(int id);
	
	//add first default person
	void firstPerson();
	
	
	
}
