package springCrud.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import springCrud.models.Person;
import springCrud.service.PersonMapper;

@Component
public class PersonDao {
	
	private static int COUNT = 1;
	private JdbcTemplate jdbcTemplate;
		
	@Autowired
	public PersonDao(JdbcTemplate jdbcTemplate)  {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Person> index() {
		return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<Person>(Person.class));
	}
	
	
	public Person show(int id) {
		return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?",new PersonMapper(), new Object[] {id})
				.stream().findAny().orElse(null);
	}
	
	public void save(Person person) {		
		jdbcTemplate.update("INSERT INTO Person VALUES(?,?,?,?,?)", COUNT++, person.getName(), person.getSurname(),
				person.getAge(), person.getEmail());
	}

	public void update(int id, Person person) {
		jdbcTemplate.update("UPDATE Person SET name = ?, surname= ?, age= ?, email = ? WHERE id = ?",
			person.getName(), person.getSurname(),person.getAge(), person.getEmail(), id);
	}

	public void remove(int id) {
		jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
		
	}
	
	
	
	
	
	
	
	
	

}
