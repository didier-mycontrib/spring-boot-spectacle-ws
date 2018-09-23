package org.mycontrib.spectacle.service;

import java.util.Date;
import java.util.List;

import org.mycontrib.spectacle.entity.Category;
import org.mycontrib.spectacle.entity.Session;
import org.mycontrib.spectacle.entity.Spectacle;
import org.springframework.stereotype.Service;

@Service
public class SpectacleServiceImpl implements SpectacleService {

	@Override
	public Category addCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeCategory(Long categoryId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Category> allCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spectacle findSpectacleById(Long spectacleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Spectacle> findSpectaclesByCategory(Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Spectacle> findSpectaclesByCriteria(Date date, Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spectacle addSpectacle(Spectacle spectacle, Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSpectacle(Long spectacleId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSpectacle(Spectacle spectacle) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSession(Long sessionId) {
		// TODO Auto-generated method stub

	}

}
