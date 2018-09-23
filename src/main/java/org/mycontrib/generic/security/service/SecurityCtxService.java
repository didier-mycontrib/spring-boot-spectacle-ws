package org.mycontrib.generic.security.service;

import java.util.List;
import java.util.Set;

import org.mycontrib.generic.security.persistence.entity.SecurityCtx;
import org.mycontrib.generic.security.persistence.entity.SecurityDomain;
import org.mycontrib.generic.security.persistence.entity.SecurityGroup;
import org.mycontrib.generic.security.persistence.entity.SecurityRole;

public interface SecurityCtxService {
	SecurityCtx findSecurityContextByFullName(String fullContextName);
	SecurityCtx createGroup(SecurityGroup userGroup,Long parentSecurityContextId);//parentSecurityContextId may be null
	SecurityDomain createDomain(SecurityDomain domain,Long parentSecurityDomainId);//parentSecurityDomainId may be null
	SecurityCtx findById(Long id);
	List<SecurityCtx> findByName(String contextName);//unique seulement dans le contexte d'un même parent
	List<SecurityDomain> findAllRootDomains();
	List<SecurityDomain> findAllSubDomains(Long domainId);
	List<SecurityGroup> findAllGroupsOfDomain(Long domainId);
	//findAllSubGroups(group)
	Set<SecurityRole> getSecurityRolesOfGroup(Long groupId);
	void addSecurityRoleInGroup(Long groupId,String uniqueRoleName);
    void updateSecurityContext(SecurityCtx context);
    void deleteSecurityContext(Long id);
}
