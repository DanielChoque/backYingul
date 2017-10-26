package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_Province {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "provinceId", nullable = false, updatable = false)
    private int provinceId;
	private String name;
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Yng_Province [provinceId=" + provinceId + ", name=" + name + "]";
	}
	
	
}
