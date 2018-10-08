package org.mycontrib.spectacle.service;

import org.mycontrib.spectacle.entity.Address;
import org.mycontrib.spectacle.entity.Person;

/**
 * 
 * CustomerService = service metier (business service)
 * avec gestion des transactions (commit, rollback)
 *    + traitements specifiques au metier (...)
 *    avec certains sous traitements qui seront delegues au(x) DAO(s)
 */

public interface PersonService {
	public Person addNewPerson(Person person); //returned with auto-incr id
	public Person findPersonById(Long personId);
	public void specifyPersonAddress(Long personId,Address address);
	public void updatePerson(Person person); 
	public void removePerson(Long personId);//removing login also if customer
}
