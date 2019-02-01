package org.mycontrib.spectacle.rest;

import java.util.List;

import org.mycontrib.spectacle.entity.Category;
import org.mycontrib.spectacle.entity.Session;
import org.mycontrib.spectacle.entity.Spectacle;
import org.mycontrib.spectacle.service.SpectacleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/spectacle-api/public/spectacle" , 
                headers="Accept=application/json")
public class PublicSpectacleRestCtrl {
	
	//business service vers lequel déléguer les traitements :
	@Autowired
	private SpectacleService spectacleService; 
	
	//sera appelé en HTTP / GET avec l' URL suivante:
	// http://localhost:8888/spring-boot-spectacle-ws/spectacle-api/public/spectacle/1
	@GetMapping(value="/{spectacleId}" )
	Spectacle spectacleById( @PathVariable("spectacleId") Long spectacleId ){
		return spectacleService.findSpectacleById(spectacleId);
		
	}
	
	//sera appelé en HTTP / GET avec l' URL suivante:
		// http://localhost:8888/spring-boot-spectacle-ws/spectacle-api/public/spectacle/sessions?spectacleId=1
	@GetMapping(value="/sessions" )
	List<Session> sessionsOfSpectacles( @RequestParam("spectacleId") Long spectacleId ){
			return spectacleService.findSessionsOfSpectacle(spectacleId);		
	}
	
	//http://localhost:8888/spring-boot-spectacle-ws/spectacle-api/public/spectacle/allCategories
	@GetMapping(value="/allCategories" )
	List<Category> getAllCategories( ){
		return spectacleService.allCategories();
		
	}
	
	
	
	//sera appelé en HTTP / GET avec l' URL suivante:
	// http://localhost:8888/spring-boot-spectacle-ws/spectacle-api/public/spectacle?categoryId=1
	@GetMapping(value="" )
	List<Spectacle> spectacleByCategoryId( @RequestParam("categoryId") Long categoryId ){
		return spectacleService.findSpectaclesByCategoryId(categoryId);
		
	}
	
	
}
