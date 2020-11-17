package com.meli.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Statistic")
@Table(name = "statistic")
public class Statistic {
	
	@Id 
	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(generator = "increment")
	@Column(name="ID")
	private Integer id;
	
	@Column(name="COUNTRY")
	private String country;
	
	@Column(name="COUNTRY_CODE")
	private String countryCode;
	
	@Column(name="DISTANCE")
	private Integer distance;
	
	@Column(name="INVOCATIONS")
	private Integer invocations;
	
	public Statistic() {}
	
	public Statistic(String contryCode, String country, Integer distance, Integer invocations) {
		this.countryCode = contryCode;
		this.country = country;
		this.distance = distance;
		this.invocations = invocations;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public Integer getInvocations() {
		return invocations;
	}
	public void setInvocations(Integer invocations) {
		this.invocations = invocations;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
