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
		categoryDao.deleteById(categoryId);
	}

	@Override
	public List<Category> allCategories() {
		return (List<Category>)categoryDao.findAll();
	}

	@Override
	public Spectacle findSpectacleById(Long spectacleId) {
		return spectacleDao.findById(spectacleId).orElse(null);
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
		Category category = categoryDao.findById(categoryId).get();
		spectacle.setCategory(category);
		spectacleDao.save(spectacle);
		return spectacle;
	}

	@Override
	public void removeSpectacle(Long spectacleId) {
		spectacleDao.deleteById(spectacleId);
	}

	@Override
	public void updateSpectacle(Spectacle spectacle) {
        spectacleDao.save(spectacle);//save or update
	}

	@Override
	public Session addSession(Long spectacleId, Session session) {
		Spectacle spectacle = spectacleDao.findById(spectacleId).get();
		session.setSpectacle(spectacle);
		sessionDao.save(session);
		return session;
	}

	@Override
	public List<Session> findSessionsOfSpectacle(Long spectacleId) {
		return sessionDao.findBySpectacleId(spectacleId);
	}

	@Override
	public Session findSessionById(Long sessionId) {
		return sessionDao.findById(sessionId).orElse(null);
	}

	@Override
	public void removeSession(Long sessionId) {
		sessionDao.deleteById(sessionId);
	}

	@Override
	public Category findCategoryById(Long categoryId) {
		return categoryDao.findById(categoryId).orElse(null);
	}

}
