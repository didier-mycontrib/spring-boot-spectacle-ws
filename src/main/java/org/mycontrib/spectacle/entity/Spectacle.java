package org.mycontrib.spectacle.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name="spectacle")
@NamedQueries(
		@NamedQuery(name="Spectacle.findByCategoryId", 
		query="SELECT s FROM Spectacle s WHERE s.category.id = ?1"))
public class Spectacle {
	public Spectacle(String title, String description, Integer duration, Integer nbPlaces, Double price) {
		super();
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.nbPlaces = nbPlaces;
		this.price = price;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	@JsonIgnore
	private Category category;
	
	private String title;
	
	private String description;
	
	private Integer duration; //mn
	
	private Double price; //euros
	
	@Column(name="nb_places")
	private Integer nbPlaces;

	@Override
	public String toString() {
		return "Spectacle [id=" + id + ", title=" + title + ", description=" + description + ", duration=" + duration
				+ ", price=" + price + ", nbPlaces=" + nbPlaces + "]";
	}
	
	
}
