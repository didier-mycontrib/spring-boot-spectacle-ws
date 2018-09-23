package org.mycontrib.spectacle.service;

import org.mycontrib.generic.security.generic.AbstractPersistentLoginService;
import org.mycontrib.generic.security.generic.LoginInfo;
import org.mycontrib.spectacle.dao.LoginDao;
import org.mycontrib.spectacle.dao.PersonDao;
import org.mycontrib.spectacle.entity.Address;
import org.mycontrib.spectacle.entity.Customer;
import org.mycontrib.spectacle.entity.Login;
import org.mycontrib.spectacle.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
//@Qualifier("appSpecific")
public class PersonServiceImpl implements PersonService /*, AbstractPersistentUserService */{
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private LoginDao loginDao;

	@Override
	public Person addNewPerson(Person person) {
		personDao.save(person);
		return person;
	}

	@Override
	public Person findPersonById(Long personId) {
		return personDao.findOne(personId);
	}

	@Override
	public void specifyPersonAddress(Long personId, Address address) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePerson(Person person) {
		personDao.save(person);
	}

	@Override
	public void removePerson(Long personId) {
		personDao.delete(personId);
	}

	@Override
	public void specifyCustomerLogin(Long customerId, Login login) {
		// TODO Auto-generated method stub

	}

	@Override
	public Customer findCustomerByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean checkLoginPassword(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}
/*
	@Override //from AbstractPersistentUserService (for jwt authentication)
	public LoginInfo getUserInfoByUsernameOrId(String usernameOrId) {
		Login login = loginDao.findOne(usernameOrId);
		return login;
	}*/

}
