package org.mycontrib.generic.security.service.impl;

import java.util.List;

import org.mycontrib.generic.security.generic.LoginInfo;
import org.mycontrib.generic.security.persistence.dao.LoginAccountDao;
import org.mycontrib.generic.security.persistence.dao.SecurityCtxDao;
import org.mycontrib.generic.security.persistence.entity.LoginAccount;
import org.mycontrib.generic.security.persistence.entity.SecurityCtx;
import org.mycontrib.generic.security.service.LoginAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * implementation of DefaultUserAccountService
 * wich use AccountRepositoryJpa by default
 */

@Service
@Transactional
public class DefaultLoginAccountServiceImpl implements LoginAccountService{
	
	private static Logger logger = LoggerFactory.getLogger(DefaultLoginAccountServiceImpl.class);

	
	@Autowired
	private LoginAccountDao loginAccountDao;
	
	@Autowired
	private SecurityCtxDao securityContextDao;
	
	@Override
	public LoginAccount createAccount(LoginAccount loginAccount) {
		return loginAccountDao.createAccount(loginAccount);
	}
	
	@Override
	public LoginAccount createAccountInGroup(LoginAccount loginAccount,String fullGroupName) {
		loginAccountDao.createAccount(loginAccount);
		if(fullGroupName!=null){
			SecurityCtx g = securityContextDao.findSecurityContextByUniqueFullName(fullGroupName);
			g.getAccounts().add(loginAccount);
		}
		return loginAccount;
	}

	
	@Override
	public LoginAccount findByLoginId(Long loginId) {
		return loginAccountDao.findByLoginId(loginId);
	}


	@Override
	public void updateAccount(LoginAccount loginAccount) {
		loginAccountDao.updateAccount(loginAccount);
	}

	@Override
	public void deleteAccount(Long loginId) {
		loginAccountDao.deleteAccount(loginId);
	}

	@Override
	public LoginInfo getLoginInfoByLoginId(Long loginId) {
		LoginAccount userAccount =  loginAccountDao.findByLoginId(loginId);
		userAccount.loadComptutedGlobalRoleSet();
		return (LoginInfo)userAccount;
	}

	

	
	@Override
	public boolean checkUserNameAvailability(String wishedUsername, String fullContextName) {
		if(fullContextName==null){ 
			//recherche globale en verifiant tout de même unicité:
			List<LoginAccount> loginAccounts =  loginAccountDao.findByUsername(wishedUsername);
			if(loginAccounts==null) return true;
			return (loginAccounts.size()<1); 	
		}else{
			//recherche selon appartenance au contexte:
			SecurityCtx sc = this.securityContextDao.findSecurityContextByUniqueFullName(fullContextName);
			LoginAccount loginAccount= loginAccountDao.findByUsernameAndSecurityContext(wishedUsername,sc);
			return (loginAccount==null);
		}
	}

	@Override
	public boolean alreadyExistsWithEmail(String email, String fullContextName) {
		if(fullContextName==null){ 
			//recherche globale en verifiant tout de même unicité:
			List<LoginAccount> loginAccounts =  loginAccountDao.findByEmail(email);
			if(loginAccounts==null) return true;
			return (loginAccounts.size()>=1);	
		}else{
			//recherche selon appartenance au contexte:
			SecurityCtx sc = this.securityContextDao.findSecurityContextByUniqueFullName(fullContextName);
			LoginAccount loginAccount= loginAccountDao.findByEmailAndSecurityContext(email,sc);
			return (loginAccount!=null);
		}
	}
	
	@Override
	public LoginInfo getLoginInfo(String fullContextPrefix, String username) {
		LoginInfo loginInfo=null;
		LoginAccount loginAccount=null;
		if(fullContextPrefix==null){ 
			//recherche globale en verifiant tout de même unicité:
			List<LoginAccount> loginAccounts =  loginAccountDao.findByUsername(username);
			if(loginAccounts==null) return null;
			if(loginAccounts.size()!=1) return null;
			loginAccount=loginAccounts.get(0);	
		}else{
			//recherche selon appartenance au contexte:
			SecurityCtx sc = this.securityContextDao.findSecurityContextByUniqueFullName(fullContextPrefix);
			loginAccount= loginAccountDao.findByUsernameAndSecurityContext(username,sc);
			loginAccount.setCurrentCtxPath(fullContextPrefix);
		}
		if(loginAccount!=null){
			loginAccount.loadComptutedGlobalRoleSet();
			loginInfo=loginAccount;
		}
		return loginInfo;
	}

	
	

}
