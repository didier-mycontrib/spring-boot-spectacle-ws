package org.mycontrib.spectacle.service;

import org.mycontrib.spectacle.dao.CustomerDao;
import org.mycontrib.spectacle.dao.PersonDao;
import org.mycontrib.spectacle.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public Customer addNewCustomer(Customer c) {
		customerDao.save(c);
		return c;
	}

	@Override
	public void specifiyCustomerLogin(Long personId, Long loginIdRef, String username) {
		Customer c = customerDao.findOne(personId);
		c.setLoginIdRef(loginIdRef);
		c.setUsername(username);
	}

	@Override
	public Customer findCustomerByUsername(String username) {
		return customerDao.findByUsername(username);
	}

	@Override
	public Customer findCustomerByLoginId(Long loginId) {
		return customerDao.findByLoginIdRef(loginId);
	}

	@Override
	public Customer findCustomerById(Long personId) {
		return customerDao.findOne(personId);
	}

}
