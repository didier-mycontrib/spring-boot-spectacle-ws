package org.mycontrib.generic.security.spring.security;

import java.util.ArrayList;
import java.util.List;

import org.mycontrib.generic.security.generic.AbstractPersistentLoginService;
import org.mycontrib.generic.security.generic.LoginInfo;
import org.mycontrib.generic.security.service.LoginAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/* DefaultCustomSpringSecurityDetailsService
 * = custom generic implementation of UserDetailsService interface of Spring-security
 * this implementation is based on LoginAccountService (by default)
 */

@Service()
public class DefaultCustomSpringSecurityDetailsService implements UserDetailsService {
	
	private String DEFAULT_SPRING_SECURITY_ROLE_PREFIX="ROLE_";
	
	private static Logger logger = LoggerFactory.getLogger(DefaultCustomSpringSecurityDetailsService.class);
	
	@Value("${app.securityDomain}") // in application.properties
	String appSecurityDomain = "mycompany/myapp"; //by default
	
	@Value("${app.defaultGroup}") // in application.properties
	String appDefaultGroup = "members"; //by default
	
	@Autowired
	private LoginAccountService loginAccountService;
	//userAccountService is used here as a minimalist AbstractPersistentUserService
	
	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	
	protected UserDetails genericLoginInfo2SpringSecurityUserDetails(LoginInfo loginInfo){
        // NB: org.springframework.security.core.userdetails.User
		// is an implementation of org.springframework.security.core.userdetails.UserDetails
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(String roleName : loginInfo.getRoleSet()){
			authorities.add(new SimpleGrantedAuthority(DEFAULT_SPRING_SECURITY_ROLE_PREFIX+roleName));
		}
		
		String username = loginInfo.getUsername();
		if(loginInfo.getCurrentCtxPath()!=null){
			username=loginInfo.getCurrentCtxPath()+"/"+username;
		}
		
		// User(username, password, enabled, accountNonExpired, credentialsNotExpired, accountNonLocked, authorities)
		User user = new User(username, loginInfo.getPassword(), true /*account.isEnabled()*/, true, true, true, authorities);
		return user;		
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("loadUserByUsername() was called with username="+username);
		// Fetch the userInfo corresponding to the given username:
		LoginInfo loginInfo = null;
		String fullContextPath=null;
		String userNameWithoutContext=null;
		
		int sepPos=username.lastIndexOf("/");
		if(sepPos<0){
			fullContextPath=appSecurityDomain+"/"+appDefaultGroup;
			userNameWithoutContext=username;
		}else{
			fullContextPath=username.substring(0, sepPos);
			userNameWithoutContext=username.substring(sepPos+1);
		}
		
		AbstractPersistentLoginService abstractPersistentUserService = (AbstractPersistentLoginService)loginAccountService;
		try {
			loginInfo = abstractPersistentUserService.getLoginInfo(fullContextPath,userNameWithoutContext);
		} catch (RuntimeException e) {}
		logger.debug("login fetch with loginAccountService : loginInfo="+ loginInfo);
		
		
		// If the account doesn't exist
		if (loginInfo == null) {
			logger.info("in loadUserByUsername() loginInfo not found for username="+username);
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		UserDetails user= this.genericLoginInfo2SpringSecurityUserDetails(loginInfo);
		
		detailsChecker.check(user);

		return user;
	}


}
