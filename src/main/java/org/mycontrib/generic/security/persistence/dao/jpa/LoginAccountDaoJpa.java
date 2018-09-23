package org.mycontrib.generic.security.persistence.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.mycontrib.generic.security.persistence.dao.LoginAccountDao;
import org.mycontrib.generic.security.persistence.entity.LoginAccount;
import org.mycontrib.generic.security.persistence.entity.SecurityCtx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class LoginAccountDaoJpa implements LoginAccountDao {
	
	private static Logger logger = LoggerFactory.getLogger(LoginAccountDaoJpa.class);

	@PersistenceContext()
	private EntityManager entityManager;
	
	@Override
	public LoginAccount createAccount(LoginAccount loginAccount) {
		entityManager.persist(loginAccount);
		return loginAccount;
	}
	
	@Override
	public LoginAccount findByLoginId(Long loginId) {
		return entityManager.find(LoginAccount.class,loginId);
	}

	@Override
	public List<LoginAccount> findByUsername(String username) {
		return entityManager.createNamedQuery("LoginAccount.findByUsername",LoginAccount.class)
				             .setParameter("username",username)
				             .getResultList();
	}
	
	@Override
	public List<LoginAccount> findByEmail(String email) {
		return entityManager.createNamedQuery("LoginAccount.findByEmail",LoginAccount.class)
				             .setParameter("emailJsonSubpart","%\"email\":\""+email+"\"%")
				             .getResultList();
	}

	@Override
	public void updateAccount(LoginAccount userAccount) {
		entityManager.merge(userAccount);
	}

	@Override
	public void deleteAccount(Long loginId ) {
		LoginAccount userAccountToDelete = entityManager.find(LoginAccount.class,loginId);
		entityManager.remove(userAccountToDelete);
	}

	@Override
	public LoginAccount findByUsernameAndSecurityContext(String username, SecurityCtx sc) {
		LoginAccount loginAccount=null;
		try {
			loginAccount= entityManager.createNamedQuery("LoginAccount.findByUsernameAndContextId",LoginAccount.class)
			         .setParameter("username",username)
			         .setParameter("contextId",sc.getId())
			         .getSingleResult();
		}catch (NoResultException nre) {
			//not found , return null
			logger.debug("findByUsernameAndSecurityContext/not found:" + nre.getMessage());
		} 
		catch (Exception e) {
			logger.error("findByUsernameAndSecurityContext/not found:", e);
		}
		return loginAccount;
	}

	@Override
	public LoginAccount findByEmailAndSecurityContext(String email, SecurityCtx sc) {
		LoginAccount loginAccount=null;
		try {
			loginAccount= entityManager.createNamedQuery("LoginAccount.findByEmailAndContextId",LoginAccount.class)
			         .setParameter("emailJsonSubpart","%\"email\":\""+email+"\"%")
			         .setParameter("contextId",sc.getId())
			         .getSingleResult();
		} 
		catch (NoResultException nre) {
			//not found , return null
			logger.debug("findByEmailAndSecurityContext/not found:" + nre.getMessage());
		}
		catch (Exception e) {
			logger.error("findByEmailAndSecurityContext/not found:", e);
		}
		return loginAccount;
	}
}
