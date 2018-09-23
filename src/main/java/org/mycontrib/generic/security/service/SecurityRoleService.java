package org.mycontrib.generic.security.service;

import java.util.List;

import org.mycontrib.generic.security.persistence.entity.SecurityRole;

public interface SecurityRoleService {
	SecurityRole findRoleByName(String roleName);
	List<SecurityRole> findAllRoles();
	void createRole(SecurityRole securityRole);
    void updateSecurityRole(SecurityRole securityRole);
    void deleteSecurityRole(String roleName);
}
