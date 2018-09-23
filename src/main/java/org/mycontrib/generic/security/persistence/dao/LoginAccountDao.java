package org.mycontrib.generic.security.persistence.dao;

import java.util.List;

import org.mycontrib.generic.security.persistence.entity.LoginAccount;
import org.mycontrib.generic.security.persistence.entity.SecurityCtx;

/* DAO/Repository with implicit "throws RuntimeException" */
public interface LoginAccountDao {
    LoginAccount createAccount(LoginAccount loginAccount);//returned with auto_incr userId
    LoginAccount findByLoginId(Long loginId);
    List<LoginAccount> findByUsername(String username);
    List<LoginAccount> findByEmail(String email);
    void updateAccount(LoginAccount userAccount);
    void deleteAccount(Long loginId);
	LoginAccount findByUsernameAndSecurityContext(String username, SecurityCtx sc);
	LoginAccount findByEmailAndSecurityContext(String email, SecurityCtx sc);
}