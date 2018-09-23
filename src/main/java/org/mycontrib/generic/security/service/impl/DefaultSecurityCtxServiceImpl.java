package org.mycontrib.generic.security.service.impl;

import java.util.List;
import java.util.Set;

import org.mycontrib.generic.security.persistence.dao.SecurityCtxDao;
import org.mycontrib.generic.security.persistence.dao.SecurityRoleDao;
import org.mycontrib.generic.security.persistence.entity.SecurityCtx;
import org.mycontrib.generic.security.persistence.entity.SecurityDomain;
import org.mycontrib.generic.security.persistence.entity.SecurityGroup;
import org.mycontrib.generic.security.persistence.entity.SecurityRole;
import org.mycontrib.generic.security.service.SecurityCtxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * implementation of DefaultSecurityContextService
 * wich use SecurityContextDaoJpa by default
 */

@Service
@Transactional
public class DefaultSecurityCtxServiceImpl implements SecurityCtxService {

	@Autowired
	private SecurityCtxDao securityContextDao;
	
	@Autowired
	private SecurityRoleDao securityRoleDao;
	
	
	private static Logger logger = LoggerFactory.getLogger(DefaultLoginAccountServiceImpl.class);

	
	@Override
	public SecurityCtx findSecurityContextByFullName(String fullContextName) {
		return securityContextDao.findSecurityContextByUniqueFullName(fullContextName);
	}

	@Override
	public SecurityCtx createGroup(SecurityGroup userGroup,Long parentSecurityContextId) {
		SecurityCtx parent = null;
		if(parentSecurityContextId!=null){
			parent = securityContextDao.findById(parentSecurityContextId);
		}
		return securityContextDao.createGroup(userGroup, parent);
	}

	@Override
	public SecurityDomain createDomain(SecurityDomain domain,Long parentSecurityDomainId) {
		if(parentSecurityDomainId!=null){
			SecurityCtx parent = securityContextDao.findById(parentSecurityDomainId);
			domain.setParent(parent);
		}else{
			domain.setParent(null);
			
		}
		return securityContextDao.createDomain(domain);
	}

	@Override
	public SecurityCtx findById(Long id) {
		return securityContextDao.findById(id);
	}
 
	@Override
	public List<SecurityCtx> findByName(String name) {
		return securityContextDao.findByName(name);
	}

	@Override
	public void updateSecurityContext(SecurityCtx context) {
		securityContextDao.updateSecurityContext(context);
	}

	@Override
	public void deleteSecurityContext(Long id) {
		securityContextDao.deleteSecurityContext(id);
	}

	@Override
	public List<SecurityDomain> findAllRootDomains() {
		return securityContextDao.findAllRootDomains();
	}

	@Override
	public List<SecurityGroup> findAllGroupsOfDomain(Long parentId) {
		return  securityContextDao.findAllGroupsOfContext(parentId);
	}

	@Override
	public Set<SecurityRole> getSecurityRolesOfGroup(Long groupId) {
		SecurityGroup g = (SecurityGroup) securityContextDao.findById(groupId);
		return g.getRolesOfGroup();
	}

	@Override
	public void addSecurityRoleInGroup(Long groupId, String uniqueRoleName) {
		SecurityGroup g = (SecurityGroup) securityContextDao.findById(groupId);
		SecurityRole r = securityRoleDao.findByRoleName(uniqueRoleName);
		g.getRolesOfGroup().add(r);
		//automatic update/merge at the end of transaction (with persistent entities)
	}

	@Override
	public List<SecurityDomain> findAllSubDomains(Long domainId) {
		return  securityContextDao.findAllSubDomains(domainId);
	}

}
