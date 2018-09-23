package org.mycontrib.spectacle.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor 
@Entity 
@Table(name="Person")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="person_type",discriminatorType=DiscriminatorType.STRING )
@DiscriminatorValue("Person") //Personne ordinaire par defaut
@NamedQueries({
@NamedQuery(name="Person.findByEmail",
				query="SELECT p FROM Person p WHERE p.email = :email")
})
public class Person {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	
	private String email;
	
	@OneToOne(mappedBy="person") 
	private Address address;
	
	private Date birthday; //optionnal , may be null

	@Override
	public String toString() {
		return "Person [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", address=" + address + ", birthday=" + birthday + "]";
	}
	

}
