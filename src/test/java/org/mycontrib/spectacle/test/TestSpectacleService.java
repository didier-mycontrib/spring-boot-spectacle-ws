package org.mycontrib.spectacle.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.spectacle.SpectacleSpringBootApp;
import org.mycontrib.spectacle.entity.Category;
import org.mycontrib.spectacle.entity.Session;
import org.mycontrib.spectacle.entity.Spectacle;
import org.mycontrib.spectacle.service.SpectacleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {SpectacleSpringBootApp.class})
public class TestSpectacleService {
	
	private static Logger logger = LoggerFactory.getLogger(TestSpectacleService.class);
	
	@Autowired
	private SpectacleService spectacleService; //à tester
	
	@Test
	public void testCrudCategory() {
		Category c = new Category(); c.setTitle("nouvelle categorie");
		spectacleService.addCategory(c);
		Assert.assertNotNull(c.getId());
		Long newCatId=c.getId();
		logger.debug(c.toString());
		List<Category> listeCategories = spectacleService.allCategories();
		Assert.assertNotNull(listeCategories);
		Assert.assertTrue(listeCategories.size()>=1);
		logger.debug("allCategories:");
		for(Category cat : listeCategories){
			logger.debug("\t"+cat.toString());
		}
		spectacleService.removeCategory(newCatId);
		Category removedCat = spectacleService.findCategoryById(newCatId);
		Assert.assertNull(removedCat);
	}
	
	@Test
	public void testFindSpectacleById() {
		Category categoryTheatre = new Category(null,"theatre"); spectacleService.addCategory(categoryTheatre);
		Spectacle s1 = new Spectacle("theatre 1",null,120,200,20.0); spectacleService.addSpectacle(s1, categoryTheatre.getId());
		Session s1se1 = new Session("2018-09-26","21:00:00",200); spectacleService.addSession(s1.getId(), s1se1);
		Session s1se2 = new Session("2018-10-03","21:00:00",200); spectacleService.addSession(s1.getId(), s1se2);
		Spectacle s1Relu = this.spectacleService.findSpectacleById(s1.getId());
		Assert.assertTrue(s1Relu.getId()==s1.getId());
		logger.debug("s1Relu="+s1Relu.toString());
		List<Session> sessionsDuSpectacleS1 = this.spectacleService.findSessionsOfSpectacle(s1.getId());
		Assert.assertTrue(sessionsDuSpectacleS1.size()>=2);
		for(Session s :  sessionsDuSpectacleS1) {
			logger.debug("session de s1="+s.toString());
		}
	}
	
	@Test
	public void testFindSpectaclesByCategoryId() {
		Category categoryTheatre = new Category(null,"theatre"); spectacleService.addCategory(categoryTheatre);
		Spectacle s1 = new Spectacle("theatre 1",null,120,200,20.0); spectacleService.addSpectacle(s1, categoryTheatre.getId());
		Spectacle s2 = new Spectacle("theatre 2",null,90,180,23.0); spectacleService.addSpectacle(s2, categoryTheatre.getId());
		
		List<Spectacle> listeSpectacles = this.spectacleService.findSpectaclesByCategoryId(categoryTheatre.getId());
		Assert.assertNotNull(listeSpectacles);
		Assert.assertTrue(listeSpectacles.size()>=1);
		logger.debug("spectacles de la categorie (theatre):");
		for(Spectacle s : listeSpectacles){
			logger.debug("\t"+s.toString());
		}
	}
	

	
	@Test
	public void testAddSpectacle() {
		Category categoryTheatre = new Category(null,"theatre"); spectacleService.addCategory(categoryTheatre);
		Category categoryConcert = new Category(null,"concert"); spectacleService.addCategory(categoryConcert);
		Spectacle s1 = new Spectacle("theatre 1",null,120,200,20.0); spectacleService.addSpectacle(s1, categoryTheatre.getId());
		Spectacle s2 = new Spectacle("theatre 2",null,90,180,23.0); spectacleService.addSpectacle(s2, categoryTheatre.getId());
		Spectacle s3 = new Spectacle("concert 1","classique",120,300,22.0); spectacleService.addSpectacle(s3, categoryConcert.getId());
		Spectacle s4 = new Spectacle("concert 2","rock",120,500,25.0); spectacleService.addSpectacle(s4, categoryConcert.getId());
		
		Spectacle s = new Spectacle();
		s.setTitle("nouveau spectacle");
		s.setDuration(60);
		s.setPrice(25.8);
		s.setNbPlaces(435);
		s.setDescription("description qui va bien");
		s=this.spectacleService.addSpectacle(s, categoryConcert.getId());
		Assert.assertNotNull(s.getId());
		logger.debug("s="+s.toString());
		
		List<Spectacle> listeSpectacles = this.spectacleService.findSpectaclesByCategoryId(categoryConcert.getId());
		logger.debug("spectacles de la categorie 2:");
		for(Spectacle sp : listeSpectacles){
			logger.debug("\t"+sp.toString());
		}
	}
	
}
