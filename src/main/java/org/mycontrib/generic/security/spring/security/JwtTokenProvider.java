package org.mycontrib.generic.security.spring.security;

import java.util.ArrayList;
import java.util.List;

import org.mycontrib.generic.security.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

/* Un peu comme JwtUtil mais sous forme de composant Spring */

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    
    private String DEFAULT_SPRING_SECURITY_ROLE_PREFIX="ROLE_";

    @Value("${app.jwtSecret}") // in application.properties
    private String jwtSecret = "MyJWTSuperSecretKey"; //by default (example)

    @Value("${app.jwtExpirationInMs}") // in application.properties
    private int jwtExpirationInMs =  30*60*1000 ;//pour 30 minutes (example) par defaut

    public String generateToken(Authentication authentication) {

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
           
        List<String> roleNameList=new ArrayList<String>();
        for(GrantedAuthority ga : userPrincipal.getAuthorities()){
        	String springSecurityRoleName=ga.getAuthority();
        	String roleName=springSecurityRoleName;
        	//ou bien roleName = springSecurityRoleName moins le préfixe "ROLE_" (affaire de préférence)
        	/*
        	if(roleName.startsWith(DEFAULT_SPRING_SECURITY_ROLE_PREFIX)){
        		roleName = roleName.substring(DEFAULT_SPRING_SECURITY_ROLE_PREFIX.length());
        	}*/
        	roleNameList.add(roleName);        	
        }
        return buildToken(userPrincipal.getUsername(),roleNameList);
    }
        
    public String buildToken(String userNameOrId,List<String> roleNameList) {
        return JwtUtil.buildToken(userNameOrId, jwtExpirationInMs, jwtSecret,roleNameList);
    }
    
    //si pas de roles dans jwt claims:
    public String getUserNameOrIdFromJWT(String token) {
        return JwtUtil.extractClaimsFromJWT(token, jwtSecret).getSubject();
    }
    
    //si roles dans jwt claims:
    public UserDetails getUserDetailsFromJWT(String token) {
    	Claims jwtClaims = JwtUtil.extractClaimsFromJWT(token, jwtSecret);
        String username = jwtClaims.getSubject();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        Object rolesInClaim = jwtClaims.get(JwtUtil.ROLES_AUTHORITIES_CLAIM); 
		if(rolesInClaim!=null){
			String rolesInClaimAsString = (String) rolesInClaim.toString();
			//exemples: "[]" ou [USER] ou [USER,ADMIN]
			if(rolesInClaimAsString.length()>2){
				rolesInClaimAsString = rolesInClaimAsString.substring(1, rolesInClaimAsString.length()-1);//sans'['ni']'
				String[] tabOfRoleNames = rolesInClaimAsString.split(",");
		        for(String roleName : tabOfRoleNames){
		        	roleName=roleName.trim();
		        	logger.debug("in jwt claims, found roleName="+roleName);
		        	String springSecurityRoleName = roleName;
		        	//ou bien springSecurityRoleName = "ROLE_" + roleName; si besoin :
		        	if(!(springSecurityRoleName.startsWith(DEFAULT_SPRING_SECURITY_ROLE_PREFIX))){
		        		springSecurityRoleName=DEFAULT_SPRING_SECURITY_ROLE_PREFIX + roleName;
		        	}
					authorities.add(new SimpleGrantedAuthority(springSecurityRoleName));
				}
			}
		}
		// User(username, password, enabled, accountNonExpired, credentialsNotExpired, accountNonLocked, authorities)
		User user = new User(username, "unknown_in_jwt_claims_but_already_check", true /*account.isEnabled()*/, true, true, true, authorities);
		return user;
    }

    public boolean validateToken(String authToken) {
        return JwtUtil.validateToken(authToken, jwtSecret);
    }
}
