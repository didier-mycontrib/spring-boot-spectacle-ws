package org.mycontrib.generic.security.persistence.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//a username read in a specific application GUI sould be prefixed with a specific context
//(prefix add by application , not seen by user)

//Exemple (fullUsername) : XyCompanyDomainContext\appliAbc\groupZz\Username

//username should be unique within a specific context
//A loginAccount is often in just one context of sub-type "SecurityGroup".accounts of a same securityGroup often share the same list of role (rolesOfGroup). SecurityGroup often in SecurityDomain
 
//a securityGroup may be nested in SecurityDomain .
//a SecurityDomain is never inside SecurityGroup

//relation n-n (souvent 1-n) entre SecurityGroup et LoginAccount .

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="context_type",discriminatorType=DiscriminatorType.STRING)
@Getter @Setter  @NoArgsConstructor
@Table(name="security_context")
@NamedQueries({
	  @NamedQuery(name="SecurityContext.findByName",
			      query="SELECT c FROM SecurityCtx c WHERE c.name = :name"),
	  @NamedQuery(name="SecurityContext.findAllGroupsOfContext",
                  query="SELECT g FROM SecurityGroup g WHERE g.parent.id= :parentId"),
	  @NamedQuery(name="SecurityContext.findAllChildContexts",
      			  query="SELECT c FROM SecurityCtx c WHERE c.parent.id= :parentId"),
	  @NamedQuery(name="SecurityContext.findChildContextByName",
	  			  query="SELECT c FROM SecurityCtx c WHERE c.parent.id= :parentId AND c.name = :name")
	})
public abstract class SecurityCtx {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=64)
	private String name;
	
	@Column(length=128)
	private String description;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="parent")
	private SecurityCtx parent;
	
	@ManyToMany
	@JoinTable(name="security_contexts_accounts",
    		joinColumns={@JoinColumn(name="context_id")},
    		inverseJoinColumns={@JoinColumn(name="account_id")})
	private List<LoginAccount> accounts;

	@Override
	public String toString() {
		return "SecurityContext [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
	
}
