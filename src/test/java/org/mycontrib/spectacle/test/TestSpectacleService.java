package org.mycontrib.spectacle.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.spectacle.SpectacleSpringBootApp;
import org.mycontrib.spectacle.entity.Category;
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
		Spectacle s1 = this.spectacleService.findSpectacleById(1L);
		Assert.assertTrue(s1.getId()==1L);
		logger.debug("s1="+s1.toString());
	}
	
	@Test
	public void testFindSpectaclesByCategoryId() {
		List<Spectacle> listeSpectacles = this.spectacleService.findSpectaclesByCategoryId(1L);
		Assert.assertNotNull(listeSpectacles);
		Assert.assertTrue(listeSpectacles.size()>=1);
		logger.debug("spectacles de la categorie 1 (theatre):");
		for(Spectacle s : listeSpectacles){
			logger.debug("\t"+s.toString());
		}
	}
	
	@Test
	public void testAddSpectacle() {
		Spectacle s = new Spectacle();
		s.setTitle("nouveau spectacle");
		s.setDuration(60);
		s.setPrice(25.8);
		s.setNbPlaces(435);
		s.setDescription("description qui va bien");
		s=this.spectacleService.addSpectacle(s, 2L);
		Assert.assertNotNull(s.getId());
		logger.debug("s="+s.toString());
		
		List<Spectacle> listeSpectacles = this.spectacleService.findSpectaclesByCategoryId(2L);
		logger.debug("spectacles de la categorie 2:");
		for(Spectacle sp : listeSpectacles){
			logger.debug("\t"+sp.toString());
		}
	}
	
}
