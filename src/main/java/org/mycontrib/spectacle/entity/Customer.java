package org.mycontrib.spectacle.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter  @NoArgsConstructor 
@Entity 
@DiscriminatorValue("Customer")
//avec dans super classe "Person":
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="personType",discriminatorType=DiscriminatorType.STRING)
public class Customer extends Person{
	
	/*
	//OLD-VERSION WITHOUT GENERIC SECURITY
	//@OneToOne(mappedBy="customer")
	//@JsonIgnore
	//private Login login;
	*/
	
	@Column(name="login_id_ref")
	private Long loginIdRef; //reference vers id of LoginAccount 
	                         //(service LoginAccountService de generic.security)
	private String username; //username in LoginAccount / generic.security

	
	/*
	@OneToMany(mappedBy="client") //Lazy par defaut
	private List<Resa> listeResa;
	 */
	
	@Override
	public String toString() {
		return "Customer " + super.toString();
	}


	public Customer(String lastName, String firstName, String phoneNumber, String email) {
		super(lastName, firstName, phoneNumber, email);

	}

	
	
	
}
