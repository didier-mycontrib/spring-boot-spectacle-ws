package org.mycontrib.spectacle.service;

import java.util.Date;
import java.util.List;

import org.mycontrib.spectacle.entity.Category;
import org.mycontrib.spectacle.entity.Session;
import org.mycontrib.spectacle.entity.Spectacle;

public interface SpectacleService {
	
	public Category addCategory(Category category) ; //returned with auto_incr id
	public void removeCategory(Long categoryId);
	public List<Category> allCategories();
	
	public Spectacle findSpectacleById(Long spectacleId);
	public List<Spectacle> findSpectaclesByCategory(Long categoryId);
	public List<Spectacle> findSpectaclesByCriteria(Date date, Long categoryId);//date without time, date or categoryId may be null
	public Spectacle addSpectacle(Spectacle spectacle,Long categoryId); //returned with auto_incr id
	public void removeSpectacle(Long spectacleId);
	public void updateSpectacle(Spectacle spectacle);
	
	public Session addSession(Long spectacleId,Session session); //returned with auto_incr id
	public List<Session> findSessionsOfSpectacle(Long spectacleId);
	public Session findSessionById(Long sessionId);
	public void removeSession(Long sessionId);
   
}
