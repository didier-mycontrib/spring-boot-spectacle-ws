package org.mycontrib.generic.security.generic;

/* cette interface abstraite correspond à la vision partielle minimale
 * d'un service d'accès à un ensemble d'utilisateurs persistants
 * 
 * DefaultLoginAccountService héritant de AbstractPersistentLoginService
 * consistera en l'interface précise du service de persistence par défaut des comptes utilisateurs
 * 
 * On pourra néanmoins envisager d'autres types de services spécifiques à une application
 */
public interface AbstractPersistentLoginService {
	public LoginInfo getLoginInfoByLoginId(Long loginId);
	public LoginInfo getLoginInfo(String fullContextPrefix, String username);
}
