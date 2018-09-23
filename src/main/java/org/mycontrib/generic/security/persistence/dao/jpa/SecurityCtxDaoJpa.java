package org.mycontrib.generic.security.persistence.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mycontrib.generic.security.persistence.dao.SecurityCtxDao;
import org.mycontrib.generic.security.persistence.entity.SecurityCtx;
import org.mycontrib.generic.security.persistence.entity.SecurityDomain;
import org.mycontrib.generic.security.persistence.entity.SecurityGroup;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityCtxDaoJpa implements SecurityCtxDao {
	
	@PersistenceContext()
	private EntityManager entityManager;

	@Override
	public SecurityCtx createGroup(SecurityGroup userGroup, SecurityCtx parent) {
		if(parent!=null){
			userGroup.setParent(parent);
		}
		entityManager.persist(userGroup);
		return userGroup;
	}

	@Override
	public SecurityDomain createDomain(SecurityDomain domain) {
		entityManager.persist(domain);
		return domain;
	}

	@Override
	public SecurityCtx findById(Long id) {
		return entityManager.find(SecurityCtx.class,id);
	}

	@Override
	public void updateSecurityContext(SecurityCtx context) {
		entityManager.merge(context);
	}

	@Override
	public void deleteSecurityContext(Long id) {
		SecurityCtx g = entityManager.find(SecurityCtx.class,id);
		entityManager.remove(g);
	}

	@Override
	public List<SecurityCtx> findByName(String name) {
		return entityManager.createNamedQuery("SecurityContext.findByName", SecurityCtx.class)
				.setParameter("name", name).getResultList();
	}

	@Override
	public List<SecurityDomain> findAllRootDomains() {
		return entityManager.createNamedQuery("SecurityDomain.findAllRoots", SecurityDomain.class)
				.getResultList();
	}

	@Override
	public List<SecurityGroup> findAllGroupsOfContext(Long parentId) {
		return entityManager.createNamedQuery("SecurityContext.findAllGroupsOfContext", SecurityGroup.class)
				.setParameter("parentId", parentId).getResultList();
	}
	
	@Override
	public List<SecurityCtx> findAllChildContexts(Long parentId) {
		return entityManager.createNamedQuery("SecurityContext.findAllChildContexts", SecurityCtx.class)
				.setParameter("parentId", parentId).getResultList();
	}

	@Override
	public List<SecurityDomain> findAllSubDomains(Long domainId) {
		return entityManager.createNamedQuery("SecurityDomain.findAllSubDomains", SecurityDomain.class)
				.setParameter("domainId", domainId).getResultList();
	}
	
	private SecurityCtx findRootContextByName(String rootContextName){
		return entityManager.createNamedQuery("SecurityContext.findByName", SecurityCtx.class)
				.setParameter("name", rootContextName).getSingleResult();
	}
	
	private SecurityCtx findChildContextByName(Long parentContextId, String contextName){
		return entityManager.createNamedQuery("SecurityContext.findChildContextByName", SecurityCtx.class)
				.setParameter("name", contextName)
				.setParameter("parentId", parentContextId)
				.getSingleResult();
	}

	@Override
	public SecurityCtx findSecurityContextByUniqueFullName(String fullContextName) {
		SecurityCtx rootOrParentContext=null, context=null;
		String remainingContextName=fullContextName;
		String subContextName=null;
		int firstSepPos= fullContextName.indexOf("/");
		if(firstSepPos<0){
			//no prefix --> root domain:
			rootOrParentContext = findRootContextByName(fullContextName);
			context=rootOrParentContext;
		}else{
			//a sub context with a parent to check
			do{
				String rootOrParentContextName=remainingContextName.substring(0,firstSepPos);
				rootOrParentContext = findRootContextByName(rootOrParentContextName);
				remainingContextName=remainingContextName.substring(firstSepPos+1);
				firstSepPos= remainingContextName.indexOf("/");
				if(firstSepPos<0){//derniere sous partie
					subContextName=remainingContextName;
				    context=findChildContextByName(rootOrParentContext.getId(), subContextName);
				}
				//else : encore une sous partie et un tout de boucle !					
			}
			while(firstSepPos>0);
		}
		return context;
	}

	

}
