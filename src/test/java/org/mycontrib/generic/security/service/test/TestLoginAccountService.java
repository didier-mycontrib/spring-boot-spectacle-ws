package org.mycontrib.generic.security.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mycontrib.generic.security.generic.AbstractPersistentLoginService;
import org.mycontrib.generic.security.generic.LoginAccountDetails;
import org.mycontrib.generic.security.generic.LoginInfo;
import org.mycontrib.generic.security.persistence.entity.LoginAccount;
import org.mycontrib.generic.security.service.LoginAccountService;
import org.mycontrib.spectacle.config.WithAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {WithAutoConfiguration.class})
public class TestLoginAccountService {
	
	private static Logger logger = LoggerFactory.getLogger(TestLoginAccountService.class);
	

	@Autowired
	private LoginAccountService loginAccountService; //à tester
	
	@Test
	public void testFindUserAccountById() {
		LoginAccount u1 = this.loginAccountService.findByLoginId(1L);
		Assert.assertTrue(u1.getLoginId()==1L);
		logger.debug("u1="+u1.toString());
		if(u1.getDetail()!=null){
			logger.debug("u1.details="+u1.getDetail());
		}
	}
	
	@Test
	public void testGetLoginInfoByUsername() {
		AbstractPersistentLoginService s = (AbstractPersistentLoginService) loginAccountService;
		//LoginInfo loginInfo = s.getLoginInfo(null,"user1");//ok si un seul "user1"
		LoginInfo loginInfo = s.getLoginInfo("mycompany/myapp/employees","user1");
		Assert.assertNotNull(loginInfo);
		Assert.assertTrue(loginInfo.getUsername().equals("user1"));
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Assert.assertTrue(  passwordEncoder.matches("pwd1", loginInfo.getPassword()) );
		logger.debug("loginInfo="+loginInfo.getUsername()+","+loginInfo.getPassword()
		+"," + loginInfo.getRoleSet());
	}

	
	@Test
    public void testRegisterNewUser(){

        String username="new_user_xy";
        String password="pwd_xy";
        String email="x?y@gmail.com";
        String phoneNumber="0102030405";
        String firstName="xxx";
        String lastName="yyy";
        
        LoginAccount loginAccount = new LoginAccount(username,password);
        
        LoginAccountDetails loginAccountDetails = 
        			new LoginAccountDetails(email,phoneNumber,firstName,lastName);
        loginAccount.setDetail(loginAccountDetails.toJsonString());
        
        
        loginAccount = loginAccountService.createAccountInGroup(loginAccount,
        		         LoginAccountService.DEFAULT_FULL_CONTEXT_NAME);
        Long newLoginId = loginAccount.getLoginId();
        Assert.assertNotNull(newLoginId);
        logger.debug("id of registered new account="+newLoginId);
        
        //Load & check:
        LoginAccount reloadLogin = this.loginAccountService.findByLoginId(newLoginId);
        logger.debug("reloadLogin="+reloadLogin.toString() + " with details=" + reloadLogin.getDetail());
        LoginInfo loginInfo = loginAccountService.getLoginInfo(LoginAccountService.DEFAULT_FULL_CONTEXT_NAME,"new_user_xy");
        logger.debug("loginInfo of registered newUser="+loginInfo.getUsername()+","+loginInfo.getPassword()
		+"," + loginInfo.getRoleSet());
	}
	
}
