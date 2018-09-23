package org.mycontrib.generic.security.service.impl;

import java.util.List;

import org.mycontrib.generic.security.persistence.dao.SecurityRoleDao;
import org.mycontrib.generic.security.persistence.entity.SecurityRole;
import org.mycontrib.generic.security.service.SecurityRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * implementation of DefaultSecurityRoleService
 * wich use SecurityRoleDaoJpa by default
 */

@Service
@Transactional
public class DefaultSecurityRoleServiceImpl implements SecurityRoleService {

	@Autowired
	private SecurityRoleDao securityRoleDao;

	private static Logger logger = LoggerFactory.getLogger(DefaultLoginAccountServiceImpl.class);

	
	@Override
	public SecurityRole findRoleByName(String roleName) {
		return securityRoleDao.findByRoleName(roleName);
	}

	@Override
	public List<SecurityRole> findAllRoles() {
		return securityRoleDao.findAllRoles();
	}

	@Override
	public void createRole(SecurityRole securityRole) {
		securityRoleDao.createRole(securityRole);
	}

	@Override
	public void updateSecurityRole(SecurityRole securityRole) {
		securityRoleDao.updateSecurityRole(securityRole);
	}

	@Override
	public void deleteSecurityRole(String roleName) {
		securityRoleDao.deleteSecurityRole(roleName);
	}

}
