package org.mycontrib.generic.security.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//Role (logique) au sein d'un certain domaine (ou organisation)

@Entity
@Getter @Setter  @NoArgsConstructor @ToString
@Table(name="security_role")
@NamedQueries({
	  @NamedQuery(name="SecurityRole.findAll",
			      query="SELECT r FROM SecurityRole r")
	})
public class SecurityRole {
	
	//NB: roles classiques/generics 
	//sous forme de constantes dans interface ClassicSecurityRoles
	
	//Security Role may be attached to a list of privileges (R,W,RW, ...) in a future version
	
	@Id
	private String name; //ex: "USER" , "MEMBER" , "ADMIN" , ...
	                     //ou bien "xyz/admin" , "xyz/user" si spécifique à organisation
	
	private String description; // utilisateur , administrateur , ...
	
	public SecurityRole(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	

}
