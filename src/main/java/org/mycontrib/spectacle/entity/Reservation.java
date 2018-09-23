package org.mycontrib.spectacle.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor 
@Entity
@Table(name="Reservation")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="reservationDate")
	private Date date;
	
	/*
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customerRef")
	private Customer customer;
	*/
	
	/*
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sessionRef")
	private Session session;
	*/
	
	private Double globalAmount;
	
	/*
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="Reservation_Person",   
			  joinColumns = {@JoinColumn(name = "reservationRef")},                    
			  inverseJoinColumns = {@JoinColumn(name = "personRef")})
	private List<Person> participants;
    
	

	
	public void addParticipant(Person p){
		if (participants==null){
			participants = new ArrayList<Person>();
		}
		participants.add(p);
	}
	*/
	

}
