package springCrud.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import config.ConnectionDB;
import springCrud.dao.PersonSQLDAO;
import springCrud.models.Person;

//@Component
public class PersonService implements PersonSQLDAO {
	private static int COUNT_PERSON = 0;
	private String tablePerson = "Person"; 
	private static final Logger log = Logger.getLogger(PersonService.class);
	private ConnectionDB connectDB;
	
	public PersonService() {
		connectDB = ConnectionDB.getInstance();
		try(Connection connect = connectDB.getConnection()) {
			DatabaseMetaData dbm = connect.getMetaData();
			try (ResultSet tables = dbm.getTables(null, null, tablePerson, null)) {
				if(tables.next()) {
					log.error("The base has been already created ");
					return;
				} 
				else {
					addTable();
				 }
			} catch(SQLException e) {
				e.printStackTrace();
			}  
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public void firstPerson() {
		String sql = "INSERT INTO Person (id, name,surname,age,email) " +
				"VALUES (?, 'Ivan', 'Ivanov', 34, 'ivanov@mail.ru');";
		try(Connection connect = connectDB.getConnection(); 
			PreparedStatement preparedstatement = connect.prepareStatement(sql)) {
			preparedstatement.setInt(1, ++COUNT_PERSON);
			preparedstatement.executeUpdate();
		} catch(SQLException e) {
		log.error("Added database error", e);
		}		
	}	
	
	@Override
	public void addTable() {
		String sql = "CREATE TABLE Person (id INT NOT NULL, name VARCHAR(15) NOT NULL,"
				+ " surname VARCHAR(20) NOT NULL, age INT NOT NULL, email VARCHAR(30) NOT NULL);";
		try(Connection connect = connectDB.getConnection(); 
			Statement statement = connect.createStatement()) {				
			statement.execute(sql);
			System.out.println("Table successfully created...");	
			firstPerson();
		} catch (SQLException e) {		
			log.error("Creation database error", e);
		}
		
	}

	@Override
	public void addPerson(Person person) {
		String sql = "INSERT INTO Person (id, name,surname,age,email) " +
									"VALUES (?,?,?,?,?);";
		try(Connection connect = connectDB.getConnection();
			PreparedStatement preparedstatement = connect.prepareStatement(sql)) {
			preparedstatement.setInt(1, ++COUNT_PERSON);
			preparedstatement.setString(2, person.getName());
			preparedstatement.setString(3, person.getSurname());
			preparedstatement.setInt(4, person.getAge());
			preparedstatement.setString(5, person.getEmail());
			preparedstatement.executeUpdate();
		} catch(SQLException e) {
			log.error("Added database error", e);
		}		
	}

	@Override
	public List<Person> getAllPeople() {
		String sql = "SELECT id, name, surname, age, email FROM Person;";
		List<Person> listPerson = new ArrayList<>();
		try(Connection connect = connectDB.getConnection(); 
			Statement statement = connect.createStatement(); 
				ResultSet resultSet = statement.executeQuery(sql)) {
				while(resultSet.next()) {
					Person person = new Person();
					person.setId(resultSet.getInt("id"));
					person.setName(resultSet.getString("name"));
					person.setSurname(resultSet.getString("surname"));
					person.setAge(resultSet.getInt("age"));
					person.setEmail(resultSet.getString("email"));
					listPerson.add(person);
				}
 			} catch(SQLException e) {
			log.error("Error getting data from the database", e);
		}
		return listPerson;
	}

	@Override
	public Person getById(int id) {
		String sql = "SELECT id, name, surname, age, email FROM Person WHERE id= ?;";	
		Person person = new Person();
		try(Connection connect = connectDB.getConnection(); 
				PreparedStatement preparedstatement = connect.prepareStatement(sql)){
			preparedstatement.setInt(1, id);
			try (ResultSet resultSet = preparedstatement.executeQuery()) {					
				while(resultSet.next()) {				
					person.setId(resultSet.getInt("id"));
					person.setName(resultSet.getString("name"));
					person.setSurname(resultSet.getString("surname"));
					person.setAge(resultSet.getInt("age"));
					person.setEmail(resultSet.getString("email"));
				}
			}
		} catch(SQLException e) {
			log.error("Error getting data from the database by id", e);
		}
		return person;
	}

	@Override
	public void update(int id,Person person) {
		String sql = "UPDATE Person SET name = ?, surname= ?, age= ?, email = ?" + 
											" WHERE id = ?;";
		try(Connection connect = connectDB.getConnection();
				PreparedStatement preparedstatement = connect.prepareStatement(sql)) {
			preparedstatement.setString(1,person.getName());
			preparedstatement.setString(2,person.getSurname());
			preparedstatement.setInt(3,person.getAge());
			preparedstatement.setString(4,person.getEmail());
			preparedstatement.setInt(5,person.getId());
			preparedstatement.executeUpdate();
		} catch(SQLException e) {
			log.error("Error updating data from the database", e);
		}
	}

	@Override
	public void remove(int id) {
		String sql = "DELETE FROM Person WHERE ID = ?;";		
		try(Connection connect = connectDB.getConnection(); 
				PreparedStatement preparedstatement = connect.prepareStatement(sql)) {	
			preparedstatement.setInt(1, id);			
			preparedstatement.executeUpdate();
		}catch(SQLException e) {
			log.error("Error removing data from the database", e);
		}
	}

}
