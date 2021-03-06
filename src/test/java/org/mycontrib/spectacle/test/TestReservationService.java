package org.mycontrib.spectacle.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.spectacle.SpectacleSpringBootApp;
import org.mycontrib.spectacle.entity.Address;
import org.mycontrib.spectacle.entity.Category;
import org.mycontrib.spectacle.entity.Customer;
import org.mycontrib.spectacle.entity.Payment;
import org.mycontrib.spectacle.entity.Person;
import org.mycontrib.spectacle.entity.Reservation;
import org.mycontrib.spectacle.entity.Session;
import org.mycontrib.spectacle.entity.Spectacle;
import org.mycontrib.spectacle.service.CustomerService;
import org.mycontrib.spectacle.service.PersonService;
import org.mycontrib.spectacle.service.ReservationService;
import org.mycontrib.spectacle.service.SpectacleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {SpectacleSpringBootApp.class})
public class TestReservationService {
	
	private static Logger logger = LoggerFactory.getLogger(TestReservationService.class);
	
	@Autowired
	private SpectacleService spectacleService;
	
	@Autowired
	private PersonService personService;  
	
	@Autowired
	private CustomerService customerService; 
	
	
	@Autowired
	private ReservationService reservationService; //à tester
	
	
	@Test
	public void testReservationById() {
		
		Customer c1 = new Customer("alex", "Therieur"  , "alex-therieur@iciOulaBas.fr" , "0102030405");
		customerService.addNewCustomer(c1);
	
		
		Person p3 = new Person("p3", "NomQuiVaBien"  , "alex-therieur@iciOulaBas.fr" , "p1@xyz.fr");
		personService.addNewPerson(p3);
		   
		personService.specifyPersonAddress(c1.getId(), new Address("12","rue elle","75001","Paris" , "France"));
	
		
		Category categoryTheatre = new Category(null,"theatre"); spectacleService.addCategory(categoryTheatre);
	
		
		Spectacle s1 = new Spectacle("theatre 1",null,120,200,20.0); spectacleService.addSpectacle(s1, categoryTheatre.getId());
		
		Session s1se1 = new Session("2018-09-26","21:00:00",200); spectacleService.addSession(s1.getId(), s1se1);
		
		
		List<Long> r1Participants  = Arrays.asList(c1.getId() , p3.getId() );
		Reservation r1 = reservationService.makeReservation(c1.getId(), s1se1.getId(), r1Participants);
		Payment pa1 = new Payment("pa1","ok via Paypal"); reservationService.attachPayment(r1.getId(), pa1);
		
		Reservation r1Relu = this.reservationService.findReservationById(r1.getId());
		Assert.assertTrue(r1Relu.getId()==r1.getId());
		logger.debug("r1="+r1Relu.toString());
		logger.debug("r1.customer="+r1Relu.getCustomer().toString());
		logger.debug("r1.participants="+r1Relu.getParticipants().toString());
		logger.debug("r1.session="+r1Relu.getSession().toString());
		logger.debug("r1.session.spectacle="+r1Relu.getSession().getSpectacle().toString());
		logger.debug("r1.payment="+r1Relu.getPayment().toString());
	}
	
	
	
}
