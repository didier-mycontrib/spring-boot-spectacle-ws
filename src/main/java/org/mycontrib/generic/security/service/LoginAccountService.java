package org.mycontrib.generic.security.service;

import org.mycontrib.generic.security.generic.AbstractPersistentLoginService;
import org.mycontrib.generic.security.persistence.entity.LoginAccount;

/* DefaultUserAccountService = traditionnal business Service for UserAccount */
public interface LoginAccountService extends AbstractPersistentLoginService {
	//methodes héritées: public LoginInfo getLoginInfoByLoginId(Long loginId);
	//                   public LoginInfo getLoginInfo(String fullContextPrefix, String username);
	
	public static String DEFAULT_MEMBER_GROUP_NAME="members";//default group name for role=MEMBER
	public static String DEFAULT_ADMIN_GROUP_NAME="administrators";//default group name for role=ADMIN
	public static String DEFAULT_USER_GROUP_NAME="employees";//default group name for role=USER
	
	public static String DEFAULT_DOMAIN_NAME="mycompany/myapp";
	public static String DEFAULT_FULL_CONTEXT_NAME=DEFAULT_DOMAIN_NAME+"/"+DEFAULT_MEMBER_GROUP_NAME;
	
	LoginAccount createAccount(LoginAccount userAccount);
	LoginAccount createAccountInGroup(LoginAccount userAccount, String groupName);
	LoginAccount findByLoginId(Long loginId);
	
	void addSpecificSecurityRoleForUser(Long loginId,String uniqueRoleName);
	
    void updateAccount(LoginAccount loginAccount);
    void deleteAccount(Long loginId);
    boolean checkUserNameAvailability(String wishedUsername, String fullContextName);
	//boolean existsByUsername(String username);
	boolean alreadyExistsWithEmail(String email, String fullContextName);
}
