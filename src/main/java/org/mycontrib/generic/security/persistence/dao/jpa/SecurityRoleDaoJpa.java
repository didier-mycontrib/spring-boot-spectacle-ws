package org.mycontrib.generic.security.persistence.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mycontrib.generic.security.persistence.dao.SecurityRoleDao;
import org.mycontrib.generic.security.persistence.entity.SecurityRole;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityRoleDaoJpa implements SecurityRoleDao {
	
	@PersistenceContext()
	private EntityManager entityManager;

	@Override
	public void createRole(SecurityRole securityRole) {
		entityManager.persist(securityRole);
	}

	@Override
	public SecurityRole findByRoleName(String roleName) {
		return entityManager.find(SecurityRole.class, roleName);
	}

	@Override
	public void updateSecurityRole(SecurityRole securityRole) {
		entityManager.merge(securityRole);
	}

	@Override
	public void deleteSecurityRole(String roleName) {
		SecurityRole role = entityManager.find(SecurityRole.class, roleName);
		entityManager.remove(role);
	}

	@Override
	public List<SecurityRole> findAllRoles() {
		return entityManager.createNamedQuery("SecurityRole.findAll",SecurityRole.class)
				           .getResultList();
	}

}
