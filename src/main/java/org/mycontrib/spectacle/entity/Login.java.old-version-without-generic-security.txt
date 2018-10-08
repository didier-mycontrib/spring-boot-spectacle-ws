package org.mycontrib.spectacle.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.mycontrib.generic.security.generic.ClassicSecurityRoles;
import org.mycontrib.generic.security.generic.LoginInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* LoginInfo */

@Getter @Setter  @NoArgsConstructor
@Entity
@Table(name="Login")
@NamedQuery(name="Login.findByUsername",
            query="SELECT l FROM Login l WHERE l.username = :username")
public class Login /*implements LoginInfo*/{
	

	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@OneToOne
	@JoinColumn(name="customer_ref")
	private Customer customer;

	@Override
	public String toString() {
		return "Login [username=" + username + ", password=" + password + "]";
	}

	//@Override //from UserInfo (for jwt authentication)
	public String getUsernameOrId() {
		return this.username;
	}

	//@Override //from UserInfo (for jwt authentication)
	public List<String> getRoleList() {
		List<String> roleList= new ArrayList<String>();
		roleList.add(ClassicSecurityRoles.MEMBER);
		roleList.add(ClassicSecurityRoles.GUEST);
		return roleList;
	}

	
}
