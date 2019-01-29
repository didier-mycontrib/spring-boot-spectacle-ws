package org.mycontrib.generic.security.persistence.entity;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Groupe d'utilisateurs ayant généralement les mêmes roles/habilitations

//relation n-n entre SecurityGroup et Role , rôle(s) facultatif(s).

@Entity
@DiscriminatorValue("group")
@Getter @Setter  @NoArgsConstructor
public class SecurityGroup extends SecurityCtx{
	
	
	@ManyToMany
	@JoinTable(name="security_groups_roles",
	           joinColumns={@JoinColumn(name="group_id")},
	           inverseJoinColumns={@JoinColumn(name="role")})
	private Set<SecurityRole> rolesOfGroup;

	@Override
	public String toString() {
		return "SecurityGroup extends " + super.toString() + "]";
	}

	public SecurityGroup(String name) {
		super(name);
	}

	
	
}
