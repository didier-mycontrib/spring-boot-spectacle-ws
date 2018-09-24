package org.mycontrib.spectacle.service;

import java.util.Date;
import java.util.List;

import org.mycontrib.spectacle.dao.CategoryDao;
import org.mycontrib.spectacle.dao.SessionDao;
import org.mycontrib.spectacle.dao.SpectacleDao;
import org.mycontrib.spectacle.entity.Category;
import org.mycontrib.spectacle.entity.Session;
import org.mycontrib.spectacle.entity.Spectacle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpectacleServiceImpl implements SpectacleService {
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private SpectacleDao spectacleDao;
	
	@Autowired
	private SessionDao sessionDao;

	@Override
	public Category addCategory(Category category) {
		categoryDao.save(category);
		return category;
	}

	@Override
	public void removeCategory(Long categoryId) {
		categoryDao.delete(categoryId);
	}

	@Override
	public List<Category> allCategories() {
		return (List<Category>)categoryDao.findAll();
	}

	@Override
	public Spectacle findSpectacleById(Long spectacleId) {
		return spectacleDao.findOne(spectacleId);
	}

	@Override
	public List<Spectacle> findSpectaclesByCategoryId(Long categoryId) {
		return spectacleDao.findByCategoryId(categoryId);
	}

	@Override
	public List<Spectacle> findSpectaclesByCriteria(Date date, Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spectacle addSpectacle(Spectacle spectacle, Long categoryId) {
		Category category = categoryDao.findOne(categoryId);
		spectacle.setCategory(category);
		spectacleDao.save(spectacle);
		return spectacle;
	}

	@Override
	public void removeSpectacle(Long spectacleId) {
		spectacleDao.delete(spectacleId);
	}

	@Override
	public void updateSpectacle(Spectacle spectacle) {
        spectacleDao.save(spectacle);//save or update
	}

	@Override
	public Session addSession(Long spectacleId, Session session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Session> findSessionsOfSpectacle(Long spectacleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session findSessionById(Long sessionId) {
		return sessionDao.findOne(sessionId);
	}

	@Override
	public void removeSession(Long sessionId) {
		sessionDao.delete(sessionId);
	}

	@Override
	public Category findCategoryById(Long categoryId) {
		return categoryDao.findOne(categoryId);
	}

}
