package org.mycontrib.generic.security.persistence.dao;

import java.util.List;

import org.mycontrib.generic.security.persistence.entity.SecurityRole;

public interface SecurityRoleDao {
	SecurityRole findByRoleName(String roleName);
	void createRole(SecurityRole securityRole);
    void updateSecurityRole(SecurityRole securityRole);
    void deleteSecurityRole(String roleName);
	List<SecurityRole> findAllRoles();
}
