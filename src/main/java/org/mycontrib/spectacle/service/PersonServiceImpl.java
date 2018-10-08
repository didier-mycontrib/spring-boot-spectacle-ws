package org.mycontrib.spectacle.service;

import org.mycontrib.spectacle.dao.PersonDao;
import org.mycontrib.spectacle.entity.Address;
import org.mycontrib.spectacle.entity.Customer;
import org.mycontrib.spectacle.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonDao personDao;
	
	@Override
	public Person addNewPerson(Person person) {
		personDao.save(person);
		return person;
	}

	@Override
	public  Person findPersonById(Long personId) {
		return personDao.findOne(personId);
	}

	@Override
	public void specifyPersonAddress(Long personId, Address address) {
		Person p = personDao.findOne(personId);
		p.setAddress(address);
	    //automatic update/merge in persistent state
	}

	@Override
	public void updatePerson(Person person) {
		personDao.save(person);
	}

	@Override
	public void removePerson(Long personId) {
		personDao.delete(personId);
	}
}
