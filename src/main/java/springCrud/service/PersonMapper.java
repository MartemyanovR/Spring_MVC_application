package springCrud.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import springCrud.models.Person;

public class PersonMapper implements RowMapper<Person> {

	@Override
	public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Person person = new Person();
		person.setId(resultSet.getInt("id"));
		person.setName(resultSet.getString("name"));
		person.setSurname(resultSet.getString("surname"));
		person.setAge(resultSet.getInt("age"));
		person.setEmail(resultSet.getString("email"));
		return person;
	}

}
