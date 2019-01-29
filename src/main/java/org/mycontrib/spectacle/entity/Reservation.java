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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.mycontrib.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Entity
@Table(name="reservation")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="reservation_date")
	private Date date;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customer_id")
	private Customer customer;

	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="session_id")
	private Session session;
	
	@OneToOne(optional=true)
	@JoinColumn(name="payment_ref")
	private Payment payment;
	
	@JoinColumn(name="global_amount")
	private Double globalAmount;
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="reservation_participants",   
			  joinColumns = {@JoinColumn(name = "reservation_id")},                    
			  inverseJoinColumns = {@JoinColumn(name = "person_id")})
	private List<Person> participants;
    
	

	
	public void addParticipant(Person p){
		if (participants==null){
			participants = new ArrayList<Person>();
		}
		participants.add(p);
	}




	@Override
	public String toString() {
		return "Reservation [id=" + id + ", date=" + date + ", globalAmount=" + globalAmount + "]";
	}

	public Reservation() {
		super();
		this.date = new Date();
	}


	public Reservation(Date date, Double globalAmount) {
		super();
		this.date = date;
		this.globalAmount = globalAmount;
	}
	
	public Reservation(String dateAsString, Double globalAmount) {
		super();
		this.date = DateUtil.javaDateFromStringDate(dateAsString);
		this.globalAmount = globalAmount;
	}
	
	

}
