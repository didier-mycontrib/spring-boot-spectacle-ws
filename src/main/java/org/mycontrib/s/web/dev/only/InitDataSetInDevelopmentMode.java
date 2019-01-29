package org.mycontrib.s.web.dev.only;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitDataSetInDevelopmentMode {
	
	@Autowired
	private SpectacleService spectacleService;
	
	@Autowired
	private PersonService personService;  
	
	@Autowired
	private CustomerService customerService; 
	
	@Autowired
	private ReservationService reservationService; 
	
	@PostConstruct //pour compenser base réinitialisée au démarrage en mode jpa "drop-and-create"
	public void initialiserJeuxDeDonneesEnModeDeveloppement() {
		//code déclenché seulement si profile spring "web.dev" activé
		System.out.println("init DataSet in web.dev profile only");
		
		Customer c1 = new Customer("alex", "Therieur"  , "alex-therieur@iciOulaBas.fr" , "0102030405");
		customerService.addNewCustomer(c1);
		Customer c2 = new Customer("alain", "Therieur"  , "alain-therieur@xyz.fr" , "0123456789");
		customerService.addNewCustomer(c2);
		
		Person p3 = new Person("p3", "NomQuiVaBien"  , "alex-therieur@iciOulaBas.fr" , "p1@xyz.fr");
		personService.addNewPerson(p3);
		   
		personService.specifyPersonAddress(c1.getId(), new Address("12","rue elle","75001","Paris" , "France"));
		personService.specifyPersonAddress(c2.getId(), new Address("14bis","rue serpentine","69000","Lyon", "France"));
		
		Category categoryTheatre = new Category(null,"theatre"); spectacleService.addCategory(categoryTheatre);
		Category categoryConcert = new Category(null,"concert"); spectacleService.addCategory(categoryConcert);
		
		Spectacle s1 = new Spectacle("theatre 1",null,120,200,20.0); spectacleService.addSpectacle(s1, categoryTheatre.getId());
		Spectacle s2 = new Spectacle("theatre 2",null,90,180,23.0); spectacleService.addSpectacle(s2, categoryTheatre.getId());
		Spectacle s3 = new Spectacle("concert 1","classique",120,300,22.0); spectacleService.addSpectacle(s3, categoryConcert.getId());
		Spectacle s4 = new Spectacle("concert 2","rock",120,500,25.0); spectacleService.addSpectacle(s4, categoryConcert.getId());
	
		Session s1se1 = new Session("2018-09-26","21:00:00",200); spectacleService.addSession(s1.getId(), s1se1);
		Session s1se2 = new Session("2018-10-03","21:00:00",200); spectacleService.addSession(s2.getId(), s1se2);
		
		 
		List<Long> r1Participants  = Arrays.asList(c2.getId() , p3.getId() );
		Reservation r1 = reservationService.makeReservation(c1.getId(), s1se1.getId(), r1Participants);
		List<Long> r2Participants = Arrays.asList(c2.getId());
		Reservation r2 = reservationService.makeReservation(c2.getId(), s1se2.getId(), r2Participants);
		
		Payment pa1 = new Payment("pa1","ok via Paypal"); reservationService.attachPayment(r1.getId(), pa1);
		Payment pa2 = new Payment("pa2","ok via Bank Xy"); reservationService.attachPayment(r2.getId(), pa2);
		
		
	}

}
