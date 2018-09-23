package org.mycontrib.generic.security.generic;

import java.util.Set;

/*
 * L'interface générique LoginInfo (pas liée à Spring ni autre )
 * correpond à la vision minimale des infos liées à un utilisateur à authentifier
 * 
 * Les informations extraites via cette interface pourront en autres  être transposées en
 * l'équivalent spécifique "Spring" : interface org.springframework.security.core.userdetails.UserDetails .
 */

public interface LoginInfo {
	public String getUsername();
	public Long getLoginId();
	public String getPassword();
	public Set<String> getRoleSet();
	public String getCurrentCtxPath(); //may be null (transient)
}
