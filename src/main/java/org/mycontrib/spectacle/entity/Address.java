package org.mycontrib.spectacle.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Entity ou @Embeddable
@Getter @Setter @NoArgsConstructor 
@Entity 
@Table(name="address")//au sens AddressOfCustomer avec correspondance de pk
public class Address {

	@Id //pas de @GeneratedValue si correspondance de pk 
	@Column(name="id_address_of_person")
	private Long idAddressOfPerson; // si pas @Embeddable
	
	@Column(length=12)
	private String number; //ex: 12 , 14bis ,  ...
	
	private String street; 
	
	@Column(length=12)
	private String zip ; //codePostal
	
	private String town;
	private String country;//optionnal (may be null)
	
	@OneToOne(optional=true)
	@PrimaryKeyJoinColumn
	@JsonIgnore
	private Person person;

	@Override
	public String toString() {
		return "Address [idAddressOfPerson=" + idAddressOfPerson + ", number=" + number + ", street=" + street
				+ ", zip=" + zip + ", town=" + town + ", country=" + country + "]";
	} 
	
	
}
