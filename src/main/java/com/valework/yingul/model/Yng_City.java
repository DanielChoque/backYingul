package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_City {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "provinceId", nullable = false, updatable = false)
	private int cityId;
	private String name;
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Yng_City [cityId=" + cityId + ", name=" + name + "]";
	}
	
}
