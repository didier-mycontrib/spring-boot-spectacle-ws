package org.mycontrib.spectacle.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.mycontrib.util.DateUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="session")
@NamedQueries({
	@NamedQuery(name="Session.findBySpectacleId" , query="SELECT s FROM Session s WHERE s.spectacle.id = ?1")
})
public class Session {
	public Session(Date date, Date startTime, Integer nbRemainingPlaces) {
		super();
		this.date = date;
		this.startTime = startTime;
		this.nbRemainingPlaces = nbRemainingPlaces;
	}
	
	public Session(String dateAsString, String startTimeAsString, Integer nbRemainingPlaces) {
		super();
		this.date = DateUtil.javaDateFromStringDate(dateAsString);
		this.startTime = DateUtil.javaDateFromStringTime(startTimeAsString);
		this.nbRemainingPlaces = nbRemainingPlaces;
	}

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
