package org.mycontrib.generic.security.persistence.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.mycontrib.generic.security.generic.LoginInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* LoginAccount correspond au format (par defaut et personnalisable) 
 * des comptes utilisateurs stockés en base de données
 * pour l'authentification .
 * En tant qu'entité persistante , UserAccount sera utilisé par le dao/repository "UserAccountDao"
 * UserAccount sera utilisé par le service métier DefaultUserAccountService .
 * 
 * NB: UserAccount implémente l'interface UserInfo volontairement dissociée de spring-security
 * pour une éventuelle utilisation dans une application JEE/CDI/EJB3/JAX-RS
 * 
 * Dans le contexte spécifique de Spring-security, UserAccount implémentant UserInfo 
 * DefaultUserAccountService seront utilisé par DefaultCustomSpringSecurityDetailsService
 * Ce service transformera "UserAccount implements UserInfo" en "User implements UserDetails" qu'il est prévu de stocké
 * en tant que principal dans l'objet central "Authentication" de Spring Security
 * 
 * penser si besoin à @EntityScan(basePackages= { "org.mycontrib.generic.security.persistence.entity" , ... }
 */

@Getter @Setter  @NoArgsConstructor
@Entity
@NamedQueries({
	  @NamedQuery(name="LoginAccount.findByUsername",
			      query="SELECT u FROM LoginAccount u WHERE u.username = :username"),
	  @NamedQuery(name="LoginAccount.findByUsernameAndContextId",
      				query="SELECT u FROM LoginAccount u INNER JOIN u.contexts ctx WHERE u.username = :username AND ctx.id = :contextId"),
	  @NamedQuery(name="LoginAccount.findByEmail",
      			  query="SELECT u FROM LoginAccount u WHERE u.detail LIKE :emailJsonSubpart"),
	  @NamedQuery(name="LoginAccount.findByEmailAndContextId",
      			 query="SELECT u FROM LoginAccount u INNER JOIN u.contexts ctx WHERE u.detail LIKE :emailJsonSubpart AND ctx.id = :contextId")
	})
@Table(name="login_account")
public class LoginAccount implements LoginInfo{
	
	private static Logger logger = LoggerFactory.getLogger(LoginAccount.class);


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="login_id")
	private Long loginId;
	
	@Column(length=64)
	private String username;//unique dans un certain contexte
	//NB ce username pourra programmatiquement/sémantiquement 
	//être préfixé par un contexte complet pour former
	//un "fullUsername" calculé de type
	// XyCompanyDomainContext\appliAbc\groupZz\Username
	
	
	@Column(length=64)
	@JsonIgnore
	private String password;
	
	private Boolean disabled;
	
	/*
	 * A generic userAccount is a predefined account bind to (userGroup + Role)
	 * which could be SHARED by several people
	 */
	private Boolean generic;
	
	@Transient
	private String currentCtxPath;
	
    
	@ManyToMany(mappedBy="accounts") //n-n mais souvent n-1
	private List<SecurityCtx> contexts;
	//NB: complete list of roles of user = 	specificRolesOfUser + sum( group.rolesOfGroup )
	
	@ManyToMany
	@JoinTable(name="security_accounts_roles",
    		joinColumns={@JoinColumn(name="account_id")},
    		inverseJoinColumns={@JoinColumn(name="role")})
	private List<SecurityRole> specificRolesOfUser;
	
	//NB: la liste complete des roles d'un utilisateur peut/doit tenir compte
	//des rôles associés à tout un groupe d'utilisateurs
	
	@Transient
	private Set<String> computedGlobalRoleSet = new HashSet<String>();;
	
	private String detail;//result of LoginAccountDetails / .toJsonString()

	public LoginAccount(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserAccount [username=" + username + ", password=" + password + "]";
	}
	
	public void loadComptutedGlobalRoleSet(){
		this.computedGlobalRoleSet = new HashSet<String>();
		//eventuellement peaufinable (avec groupes et sous groupes)
		for(SecurityCtx sc : this.contexts){
			if(sc instanceof SecurityGroup){
				SecurityGroup g = (SecurityGroup) sc;
				for(SecurityRole roleOfGroup : g.getRolesOfGroup()){
					computedGlobalRoleSet.add(roleOfGroup.getName());
				}
			}
		}
		for(SecurityRole specificRoleOfUser : this.specificRolesOfUser){
			computedGlobalRoleSet.add(specificRoleOfUser.getName());
		}
	}

	@Override //from LoginInfo interface
	public Set<String> getRoleSet() {
		return computedGlobalRoleSet;
	}

	@Override //from LoginInfo interface , ne fait que retourner ce qui est éventuellement préalablement set...()
	public String getCurrentCtxPath() {
		return this.currentCtxPath;
	}
	
	public void setCurrentCtxPath(String currentCtxPath){
		this.currentCtxPath=currentCtxPath;
	}
    
	

}
