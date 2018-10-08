package org.mycontrib.spectacle.service;

import org.mycontrib.spectacle.entity.Customer;

/**
 * 
 * CustomerService = service metier (business service)
 * avec gestion des transactions (commit, rollback)
 *    + traitements specifiques au metier (...)
 *    avec certains sous traitements qui seront delegues au(x) DAO(s)
 */

public interface CustomerService {
	//NB: Customer hérite de Person et peut en partie être manipulé via personService
	
	public Customer addNewCustomer(Customer c); //return customer with auto_incr pk (personId)
	
	//public void specifyCustomerLogin(Long customerId,Login login);//old version without generic security
	
	public void specifiyCustomerLogin(Long personId,Long loginIdRef,String username);//attach generic security
	
	public Customer findCustomerByUsername(String username);
	public Customer findCustomerByLoginId(Long loginId);
	public Customer findCustomerById(Long personId);
	
	//public Boolean checkLoginPassword(String username, String password); //old version , now in generic security
}
