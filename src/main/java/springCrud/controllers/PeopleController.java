package springCrud.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import springCrud.dao.PersonDao;
import springCrud.models.Person;
import springCrud.service.PersonService;

@Controller
@RequestMapping("/people")
public class PeopleController {
	
	//private final PersonService personDao;
	private final PersonDao personDao;
	
	//DI
	public PeopleController(PersonDao personDao) {		
		this.personDao = personDao;
	}

	/*
	 * возвращает список из людей из DAO, и передает на отображение в представление
	 */
	@GetMapping()
	public String index(Model model) {
		model.addAttribute("peoples", personDao.index());
		return "/people/index";
	}
	
	/*
	 * получим  одного человека из дао и передадим на  отображение в представление
	 */
	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model) {
		model.addAttribute("person", personDao.show(id));
		return "/people/show";
	}
	
/*	@GetMapping("/new") //по этому адресу нам вернется html форма для создания нового человека
	public String newPerson(Model model) {		
		model.addAttribute("person", new Person());  //создаем пустой обьект и передаем через модель в шаблон		
		
		return "/people/new";  //это адрес формы для создания new person
	}	*/
	//так же можно использовать @ModelAttribute
	@GetMapping("/new")
	public String newPerson(@ModelAttribute("person") Person person) {
		
		return "/people/new";
	}	
	
	@PostMapping //по запросу "/people" мы попадаем в данный метод
	public String create(@ModelAttribute("person") @Valid Person person, BindingResult binding) {  //получаем данные из формы , создаем обьект person, и заполняем поля
		if(binding.hasErrors()) {
			return "/people/new";
		}
		personDao.save(person);  //добавление в бд
		
		return "redirect:/people";  //переход на старницу после передачи значений полям обьекта person
	}	
	
	/*	//устаревший способ получения данных из формы
	@PostMapping
	public String create(@RequestParam("name") String name, 
							@RequestParam("surname") String surname,
									@RequestParam("email") String email,
																Model model) {
		Person person = new Person();
		person.setName(name);
		person.setSurname(surname);
		person.setEmail(email);		
		model.addAttribute("person", person);
		personDao.save(person);
		return "redirect:/people";							   
	} */
	
	
	//создаем страницу для обновления человека
	//данный метод возвращает страницу для редактирования человека
	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable("id") int id) {
		model.addAttribute("person", personDao.show(id));	
		return "/people/edit";
	}
	
	//ищем нужного человека и меняем значения
	@PatchMapping("/{id}")
	public String update(@ModelAttribute("person") @Valid Person person, BindingResult binding, @PathVariable("id") int id) {
		if(binding.hasErrors()) {
			return "/people/new";
		}		
		personDao.update(id,person);
		return "redirect:/people";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		personDao.remove(id);
		return "redirect:/people";
	}
	
	
	








}
