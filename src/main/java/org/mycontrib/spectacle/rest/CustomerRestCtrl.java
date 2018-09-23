package org.mycontrib.spectacle.rest;

import org.mycontrib.generic.security.persistence.entity.SecurityGroup;
import org.mycontrib.spectacle.entity.Person;
import org.mycontrib.spectacle.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/spectacle-api/person" , 
                headers="Accept=application/json")
public class CustomerRestCtrl {
	
	//business service vers lequel déléguer les traitements :
	@Autowired
	private PersonService personService; 
	
	//sera appelé en HTTP / GET avec l' URL suivante:
	// http://localhost:8888/spring-boot-resa-ws/spectacle-api/person/1
	@GetMapping(value="/{personId}" )
	// si les noms de Roles n'ont pas l'ajout ROLE_ alors @PreAuthorize("hasAuthority('USER') or hasAuthority('MEMBER')")//au sens hasRole(roleName) with roleName without ROLE_ prefix
	//@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_MEMBER')") //avec prefixe ROLE_ de SpringSecurity
	@PreAuthorize("hasRole('USER') or hasRole('MEMBER')") //hasRole("XYZ") est à peu près équivalent à hasAuthority("ROLE_XYZ")
	Person personById( @PathVariable("personId") Long personId ){
		return personService.findPersonById(personId);
		//NB: le resultat java de type List<Person>
		//sera automatiquement transformé au format JSON
	}
	
	
}
