package org.mycontrib.spectacle.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.spectacle.SpectacleSpringBootApp;
import org.mycontrib.spectacle.entity.Address;
import org.mycontrib.spectacle.entity.Customer;
import org.mycontrib.spectacle.entity.Person;
import org.mycontrib.spectacle.service.CustomerService;
import org.mycontrib.spectacle.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {SpectacleSpringBootApp.class})
public class TestPersonService {
	
	private static Logger logger = LoggerFactory.getLogger(TestPersonService.class);
	
	@Autowired
	private PersonService personService; //à tester
	
	@Autowired
	private CustomerService customerService; //à tester
	
	@Test
	public void testFindPersonById() {
		Person p = new Person("p1", "NomQuiVaBien"  , "alex-therieur@iciOulaBas.fr" , "p1@xyz.fr");
		personService.addNewPerson(p);
		Long idPers = p.getId();
		
		Person p1 = this.personService.findPersonById(idPers);
		Assert.assertTrue(p1.getId()==idPers);
		logger.debug("p1="+p1.toString());
	}
	
	
	
	@Test
	public void test_crud_person_customer() {
		//Ajout:
		Customer nouveauClient = new Customer();
		nouveauClient.setLastName("NomXy"); nouveauClient.setFirstName("prenomZz");
		nouveauClient.setEmail("a.b@gmail.com");
		nouveauClient.setPhoneNumber("0102030405");
		nouveauClient = this.customerService.addNewCustomer(nouveauClient);
		Long numCli = nouveauClient.getId();
		Assert.assertNotNull(numCli);
		personService.specifyPersonAddress(numCli, new Address("12","rue elle","75001","Paris" , "France"));
		//Verif insertion:
		Customer clientRelu = this.customerService.findCustomerById(numCli);
		Assert.assertTrue(clientRelu.getId()==numCli);
		System.out.println("clientRelu="+clientRelu.toString());
		System.out.println("adresse du clientRelu="+clientRelu.getAddress().toString());
		//Mise à jour:
		nouveauClient.setEmail("p.n@gmail.com");
		this.personService.updatePerson(nouveauClient);
		//Verif modification:
		Customer clientReluApresModif = this.customerService.findCustomerById(numCli);
		Assert.assertTrue(clientReluApresModif.getEmail().equals("p.n@gmail.com"));
		System.out.println("clientReluApresModif="+clientReluApresModif.toString());
		
		//Suppression:
		this.personService.removePerson(numCli);
		//Verif suppression:
		Customer clientSupprime = this.customerService.findCustomerById(numCli);
		Assert.assertNull(clientSupprime);
		
	}
}
