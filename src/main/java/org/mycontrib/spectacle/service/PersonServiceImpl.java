package org.mycontrib.spectacle.service;

import org.mycontrib.spectacle.dao.AddressDao;
import org.mycontrib.spectacle.dao.PersonDao;
import org.mycontrib.spectacle.entity.Address;
import org.mycontrib.spectacle.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Override
	public Person addNewPerson(Person person) {
		personDao.save(person);
		return person;
	}

	@Override
	public  Person findPersonById(Long personId) {
		return personDao.findById(personId).orElse(null);
	}

	@Override
	public void specifyPersonAddress(Long personId, Address address) {
		Person p = personDao.findById(personId).get();
		address.setIdAddressOfPerson(personId);
		p.setAddress(address);
		addressDao.save(address);
	}

	@Override
	public void updatePerson(Person person) {
		personDao.save(person);
	}

	@Override
	public void removePerson(Long personId) {
		personDao.deleteById(personId);
	}
}
