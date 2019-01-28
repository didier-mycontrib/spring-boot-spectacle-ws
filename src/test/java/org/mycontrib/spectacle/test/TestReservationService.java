package org.mycontrib.spectacle.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.spectacle.SpectacleSpringBootApp;
import org.mycontrib.spectacle.entity.Reservation;
import org.mycontrib.spectacle.service.ReservationService;
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
	private ReservationService reservationService; //à tester
	
	
	@Test
	public void testReservationById() {
		Reservation r1 = this.reservationService.findReservationById(1L);
		Assert.assertTrue(r1.getId()==1L);
		logger.debug("r1="+r1.toString());
		logger.debug("r1.customer="+r1.getCustomer().toString());
		logger.debug("r1.participants="+r1.getParticipants().toString());
		logger.debug("r1.session="+r1.getSession().toString());
		logger.debug("r1.session.spectacle="+r1.getSession().getSpectacle().toString());
		logger.debug("r1.payment="+r1.getPayment().toString());
	}
	
	
	
}
