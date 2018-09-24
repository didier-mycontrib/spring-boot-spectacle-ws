package org.mycontrib.spectacle.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
public class Session {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	
	@Column(name="session_date")
	private Date date;
	
	@Column(name="start_time")
	@Temporal(TemporalType.TIME)
	private Date startTime;
	
	@Column(name="nb_remaining_places")
	private Integer nbRemainingPlaces;
	
	@ManyToOne
	@JoinColumn(name="spectacle_id")
	private Spectacle spectacle;

	@Override
	public String toString() {
		return "Session [id=" + id + ", date=" + date + ", startTime=" + startTime + ", nbRemainingPlaces="
				+ nbRemainingPlaces + "]";
	}
	
	
}
