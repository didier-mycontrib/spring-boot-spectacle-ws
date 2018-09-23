package org.mycontrib.spectacle.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter  @NoArgsConstructor 
@Entity 
@DiscriminatorValue("Customer")
//avec dans super classe "Person":
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="personType",discriminatorType=DiscriminatorType.STRING)
public class Customer extends Person{
	
	
	@OneToOne(mappedBy="customer")
	@JsonIgnore
	private Login login;
	

	
	/*
	@OneToMany(mappedBy="client") //Lazy par defaut
	private List<Resa> listeResa;
*/
	
	@Override
	public String toString() {
		return "Customer " + super.toString();
	}

	
	
	
	
	
}
