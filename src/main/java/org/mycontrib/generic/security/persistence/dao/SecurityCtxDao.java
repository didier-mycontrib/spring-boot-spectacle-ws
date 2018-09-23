package org.mycontrib.generic.security.persistence.dao;

import java.util.List;

import org.mycontrib.generic.security.persistence.entity.SecurityCtx;
import org.mycontrib.generic.security.persistence.entity.SecurityDomain;
import org.mycontrib.generic.security.persistence.entity.SecurityGroup;

/* DAO/Repository with implicit "throws RuntimeException" */
public interface SecurityCtxDao {
	SecurityCtx createGroup(SecurityGroup userGroup,SecurityCtx parent);//parent may be null
	SecurityDomain createDomain(SecurityDomain domain);
	SecurityCtx findById(Long id);
	List<SecurityCtx> findByName(String name);
    void updateSecurityContext(SecurityCtx context);
    void deleteSecurityContext(Long id);
	List<SecurityDomain> findAllRootDomains();
	List<SecurityGroup> findAllGroupsOfContext(Long parentId);
	List<SecurityDomain> findAllSubDomains(Long domainId);
	List<SecurityCtx> findAllChildContexts(Long domainId);
	SecurityCtx findSecurityContextByUniqueFullName(String fullContextName);
}
