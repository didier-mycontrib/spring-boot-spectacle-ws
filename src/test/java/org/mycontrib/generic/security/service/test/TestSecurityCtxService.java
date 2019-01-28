package org.mycontrib.generic.security.service.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.generic.security.persistence.entity.SecurityCtx;
import org.mycontrib.generic.security.persistence.entity.SecurityDomain;
import org.mycontrib.generic.security.persistence.entity.SecurityGroup;
import org.mycontrib.generic.security.service.SecurityCtxService;
import org.mycontrib.spectacle.SpectacleSpringBootApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {SpectacleSpringBootApp.class})
public class TestSecurityCtxService {
	
	private static Logger logger = LoggerFactory.getLogger(TestSecurityCtxService.class);
	

	@Autowired
	private SecurityCtxService securityCtxService; //à tester
	
	@Test
	public void testFindAllRootDomains_and_browse_groups() {
		List<SecurityDomain> rootDomains = this.securityCtxService.findAllRootDomains();
		Assert.assertNotNull(rootDomains);
		Assert.assertTrue(rootDomains.size()>=1);
		logger.debug("list of all existing domains , subdomains and groups:");
		for(SecurityDomain d  : rootDomains){
			logger.debug("\t"+d);
			List<SecurityDomain> subDomains = securityCtxService.findAllSubDomains(d.getId());
			for(SecurityDomain sd  : subDomains){
				logger.debug("\t\t"+sd);
				List<SecurityGroup> listeGroups = securityCtxService.findAllGroupsOfDomain(sd.getId());
				for(SecurityGroup g  : listeGroups){
					logger.debug("\t\t\t"+g);
				}
			}
		}
	}
	
	@Test
	public void testFindContextsByFullNames() {
		SecurityCtx c1= securityCtxService.findSecurityContextByFullName("mycompany");
		Assert.assertNotNull(c1);
		Assert.assertNull(c1.getParent());
		Assert.assertEquals(c1.getName(),"mycompany");
		logger.debug("c1="+c1);
		
		SecurityCtx c2= securityCtxService.findSecurityContextByFullName("mycompany/myapp");
		Assert.assertNotNull(c2);
		Assert.assertNotNull(c2.getParent());
		Assert.assertEquals(c2.getParent().getName(),"mycompany");
		Assert.assertEquals(c2.getName(),"myapp");
		logger.debug("c2="+c2);
		
		SecurityCtx c3= securityCtxService.findSecurityContextByFullName("mycompany/myapp/members");
		Assert.assertNotNull(c3);
		Assert.assertNotNull(c3.getParent());
		Assert.assertEquals(c3.getParent().getName(),"myapp");
		Assert.assertEquals(c3.getName(),"members");
		logger.debug("c3="+c3);
		
		SecurityCtx c4= securityCtxService.findSecurityContextByFullName("mycompany/otherapp/members");
		Assert.assertNotNull(c4);
		Assert.assertNotNull(c4.getParent());
		Assert.assertEquals(c4.getParent().getName(),"otherapp");
		Assert.assertEquals(c4.getName(),"members");
		logger.debug("c4="+c4);
	    
	}
	
	
}
