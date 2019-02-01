package org.mycontrib.spectacle.rest;

import org.mycontrib.spectacle.entity.Category;
import org.mycontrib.spectacle.rest.data.SpectacleAddition;
import org.mycontrib.spectacle.service.SpectacleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/spectacle-api/spectacle" , 
                headers="Accept=application/json")
public class AdminSpectacleRestCtrl {
	
	//business service vers lequel déléguer les traitements :
	@Autowired
	private SpectacleService spectacleService; 
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("")
	public ResponseEntity<SpectacleAddition> postSpectacle(@RequestBody SpectacleAddition ajoutSpectacle){
		spectacleService.addSpectacle(ajoutSpectacle.getSpectacle(), ajoutSpectacle.getCategoryId());
		return new ResponseEntity<SpectacleAddition>(ajoutSpectacle,HttpStatus.OK);
	}
	
	//http://localhost:8888/spring-boot-spectacle-ws/spectacle-api/spectacle/category
		//en mode POST
		//avec comme contenu invisible de la partie body de la requete HTTP
		//des données json { "id" : null , "title" : "categorieXyz"  } 
	@PostMapping(value="/category" )
	Category postCategory(@RequestBody Category c){
			spectacleService.addCategory(c);
			return c;
	}
	
	
	
}
