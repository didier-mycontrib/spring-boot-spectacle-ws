package org.mycontrib.generic.security.service.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.generic.security.persistence.entity.SecurityRole;
import org.mycontrib.generic.security.service.SecurityRoleService;
import org.mycontrib.spectacle.config.WithAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {WithAutoConfiguration.class})
public class TestSecurityRoleService {
	
	private static Logger logger = LoggerFactory.getLogger(TestSecurityRoleService.class);
	

	@Autowired
	private SecurityRoleService securityRoleService; //à tester
	
	@Test
	public void testFindAllRoles() {
		List<SecurityRole> listeRoles = this.securityRoleService.findAllRoles();
		Assert.assertNotNull(listeRoles);
		Assert.assertTrue(listeRoles.size()>=5);
		logger.debug("list of all existing roles:");
		for(SecurityRole r  : listeRoles){
			logger.debug("\t"+r);
		}
	}
	
	
}
