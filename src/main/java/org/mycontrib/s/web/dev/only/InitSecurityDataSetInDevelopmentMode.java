package org.mycontrib.s.web.dev.only;

import javax.annotation.PostConstruct;

import org.mycontrib.generic.security.persistence.entity.LoginAccount;
import org.mycontrib.generic.security.persistence.entity.SecurityDomain;
import org.mycontrib.generic.security.persistence.entity.SecurityGroup;
import org.mycontrib.generic.security.persistence.entity.SecurityRole;
import org.mycontrib.generic.security.service.LoginAccountService;
import org.mycontrib.generic.security.service.SecurityCtxService;
import org.mycontrib.generic.security.service.SecurityRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("complex-security")
public class InitSecurityDataSetInDevelopmentMode {
	
	@Autowired
	private SecurityRoleService securityRoleService; 
	
	@Autowired
	private SecurityCtxService securityCtxService;
	
	@Autowired
	private LoginAccountService loginAccountService; 
	
	private static boolean initialized=false;
	
	@PostConstruct //pour compenser base réinitialisée au démarrage en mode jpa "drop-and-create"
	public void initialiserJeuxDeDonneesDeSecuriteEnModeDeveloppement() {
		//code déclenché seulement si profile spring "web.dev" activé
		System.out.println("in web.dev profile only");
		initJeuxDeDonneesDeSecuriteEnModeDeveloppement(securityRoleService,securityCtxService,loginAccountService);
	}
	
	
	//version "static" simplement invocable depuis test JUnit (@Before ou @BeforeClass)
	public static void initJeuxDeDonneesDeSecuriteEnModeDeveloppement(SecurityRoleService securityRoleService,
			                                                                 SecurityCtxService securityCtxService,
			                                                                 LoginAccountService loginAccountService) {
		 if(initialized) return;
		//code déclenché seulement si profile spring "web.dev" activé
		System.out.println("init Security DataSet ...");
		
		SecurityRole srGuest = new SecurityRole("GUEST","guest with account (not anonymous)"); securityRoleService.createRole(srGuest);
		SecurityRole srUser = new SecurityRole("USER","ordinary user of application (ex: employee)"); securityRoleService.createRole(srUser);
		SecurityRole srMember = new SecurityRole("MEMBER","member/customer with valid account"); securityRoleService.createRole(srMember);
		SecurityRole srAdmin = new SecurityRole("ADMIN","Administrator with lot of privileges"); securityRoleService.createRole(srAdmin);
		SecurityRole srManager = new SecurityRole("MANAGER","Manager of part of company / ..."); securityRoleService.createRole(srManager);
		
		// domain / OwnerOrg / company :
		SecurityDomain sdMyCompany = new SecurityDomain("mycompany",null);
		securityCtxService.createDomain(sdMyCompany, null /*parent*/);
		
		// application sub-domain  exemple:(name="myapp" , computed fullName="mycompany/myapp")
		SecurityDomain sdMyApp = new SecurityDomain("myapp",null);
		securityCtxService.createDomain(sdMyApp, sdMyCompany.getId() /*parent*/);
		
		SecurityDomain sdOtherApp = new SecurityDomain("otherapp",null);
		securityCtxService.createDomain(sdOtherApp, sdMyCompany.getId() /*parent*/);

		// user_Groups :
		// exemple : name="members" , computed fullName="mycompany/myapp/members" 
		//		                     computed fullName="mycompany/otherapp/members" 
		SecurityGroup sgEmployees = new SecurityGroup("employees");
		securityCtxService.createGroup(sgEmployees, sdMyApp.getId() /* parent*/);
		securityCtxService.addSecurityRoleInGroup(sgEmployees.getId(),"USER");
		
		SecurityGroup sgAdministrators = new SecurityGroup("administrators");
		securityCtxService.createGroup(sgAdministrators, sdMyApp.getId() /* parent*/);
		securityCtxService.addSecurityRoleInGroup(sgAdministrators.getId(),"USER");
		securityCtxService.addSecurityRoleInGroup(sgAdministrators.getId(),"ADMIN");
		
		SecurityGroup sgMembers = new SecurityGroup("members");
		securityCtxService.createGroup(sgMembers, sdMyApp.getId() /* parent*/);
		securityCtxService.addSecurityRoleInGroup(sgMembers.getId(),"MEMBER");
		
		SecurityGroup sgMembersOfOtherApp = new SecurityGroup("members");
		securityCtxService.createGroup(sgMembersOfOtherApp, sdOtherApp.getId() /* parent*/);

	
		// login/accounts:
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// exemple : (username="user1" , computed fullName="mycompany/myapp/employees/user1")
		LoginAccount laUser1 = new LoginAccount("user1" , passwordEncoder.encode("pwd1"),"{\"firstName\":\"alex\",\"lastName\":\"Therieur\",\"phoneNumber\":\"0102030405\",\"email\":\"alex.therieur@gmail.com\"}");
		loginAccountService.createAccountInGroup(laUser1, "mycompany/myapp/" + sgEmployees.getName()); 
		
		LoginAccount laUser2 = new LoginAccount("user2" , passwordEncoder.encode("pwd2"),"{\"firstName\":\"jean\",\"lastName\":\"Bon\",\"phoneNumber\":\"0504030201\",\"email\":\"jean.bon@gmail.com\"}");
		loginAccountService.createAccountInGroup(laUser2, "mycompany/myapp/" + sgEmployees.getName()); 
		loginAccountService.addSpecificSecurityRoleForUser(laUser2.getLoginId(),"MANAGER");
		
		LoginAccount laAdmin = new LoginAccount("admin" , passwordEncoder.encode("admin"),"{\"firstName\":\"guy\",\"lastName\":\"Bol\",\"phoneNumber\":\"0504030203\",\"email\":\"guy.bol@gmail.com\"}");
		loginAccountService.createAccountInGroup(laAdmin, "mycompany/myapp/" + sgAdministrators.getName()); 
		
		LoginAccount laGuest = new LoginAccount("guest" , passwordEncoder.encode("guest"));
		loginAccountService.createAccountInGroup(laGuest, "mycompany/myapp/" + sgMembers.getName()); 
		loginAccountService.addSpecificSecurityRoleForUser(laGuest.getLoginId(),"GUEST");
		
		LoginAccount laMember1 = new LoginAccount("member1" , passwordEncoder.encode("pwd1"),"{\"firstName\":\"olie\",\"lastName\":\"Condor\",\"phoneNumber\":\"0102030405\",\"email\":\"olie.condor@gmail.com\"}");
		loginAccountService.createAccountInGroup(laMember1, "mycompany/myapp/" + sgMembers.getName()); 
		
		LoginAccount laMember2 = new LoginAccount("member2" , passwordEncoder.encode("pwd2"),"{\"firstName\":\"axelle\",\"lastName\":\"Aire\",\"phoneNumber\":\"0504030201\",\"email\":\"axelle.aire@gmail.com\"}");
		loginAccountService.createAccountInGroup(laMember2, "mycompany/myapp/" + sgMembers.getName()); 
		initialized=true;
	}

}
