package org.mycontrib.spectacle.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.generic.security.service.test.TestLoginAccountService;
import org.mycontrib.spectacle.config.WithAutoConfiguration;
import org.mycontrib.spectacle.entity.Person;
import org.mycontrib.spectacle.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {WithAutoConfiguration.class})
public class TestPersonService {
	
	private static Logger logger = LoggerFactory.getLogger(TestPersonService.class);
	
	@Autowired
	private PersonService personService; //à tester
	
	@Test
	public void testFindPersonById() {
		Person p1 = this.personService.findPersonById(1L);
		Assert.assertTrue(p1.getId()==1L);
		logger.debug("p1="+p1.toString());
	}
	
	
	/*
	@Test
	public void test_crud_person() {
		//Ajout:
		Client nouveauClient = new Client();
		nouveauClient.setNom("NomXy"); nouveauClient.setPrenom("prenomZz");
		nouveauClient.setAdresse("Adresse qui va bien");
		nouveauClient = this.serviceClient.creerClient(nouveauClient);
		Long numCli = nouveauClient.getNumero();
		Assert.assertNotNull(numCli);
		//Verif insertion:
		Client clientRelu = this.serviceClient.rechercherClientParNumero(numCli);
		Assert.assertTrue(clientRelu.getNumero()==numCli);
		System.out.println("clientRelu="+clientRelu.toString());
		//Mise à jour:
		clientRelu.setAdresse("nouvelle adresse");
		this.serviceClient.modifierClient(clientRelu);
		//Verif modification:
		Client clientReluApresModif = this.serviceClient.rechercherClientParNumero(numCli);
		Assert.assertTrue(clientReluApresModif.getAdresse().equals("nouvelle adresse"));
		System.out.println("clientReluApresModif="+clientReluApresModif.toString());
		//Suppression:
		this.serviceClient.supprimerClient(numCli);
		//Verif suppression:
		Client clientSupprime = this.serviceClient.rechercherClientParNumero(numCli);
		Assert.assertNull(clientSupprime);
	}*/
}
