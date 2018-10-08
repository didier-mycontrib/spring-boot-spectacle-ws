package org.mycontrib.spectacle.rest;

import org.mycontrib.spectacle.entity.Reservation;
import org.mycontrib.spectacle.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/spectacle-api/reservation" , 
                headers="Accept=application/json")
public class ReservationRestCtrl {
	
	//business service vers lequel déléguer les traitements :
	@Autowired
	private ReservationService reservationService; 
	
	//sera appelé en HTTP / GET avec l' URL suivante:
	// http://localhost:8888/spring-boot-resa-ws/spectacle-api/reservation/1
	@GetMapping(value="/{personId}" )
	@PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER')") //hasRole("XYZ") est à peu près équivalent à hasAuthority("ROLE_XYZ")
	Reservation reservationById( @PathVariable("resaId") Long resaId ){
		
		//A AJOUTER ULTERIEUREMENT:
		// checkConfidentialAccess( (loginId) ==> ????) return error status if ....
		// check if the request is emmited by the right customer (owner of his data).
		// loginId or username will be  extracted from SecurityContext (after token filter auth)
		// a lambda callback may return null if ok or errorStringMessage instead
		
		return reservationService.findReservationById(resaId);
		//NB: le resultat java de type Reservation
		//sera automatiquement transformé au format JSON
	}
	
	
}
