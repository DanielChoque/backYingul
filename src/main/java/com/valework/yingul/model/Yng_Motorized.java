package com.valework.yingul.model;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToOne;
@Entity
public class Yng_Motorized {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "motorizedId", nullable = false, updatable = false)
    private Long motorizedId;
	
	@Column(name="motorizedBrand")
	private String motorizedBrand;
	@Column(name="motorizedYear")
	private String motorizedYear;
	@Column(name="motorizedModel")	
	private String motorizedModel;
	@Column(name="motorizedUnicoDue")	
	private String motorizedUnicoDue;
	
	
	@OneToOne 
	private Yng_Item yng_Item;
	
	public Yng_Item getYng_Item() {
		return yng_Item;
	}
	public void setYng_Item(Yng_Item yng_Item) {
		this.yng_Item = yng_Item;
	}
	public Long getMotorizedId() {
		return motorizedId;
	}
	public void setMotorizedId(Long motorizedId) {
		this.motorizedId = motorizedId;
	}
	public String getMotorizedBrand() {
		return motorizedBrand;
	}
	public void setMotorizedBrand(String motorizedBrand) {
		this.motorizedBrand = motorizedBrand;
	}
	public String getMotorizedYear() {
		return motorizedYear;
	}
	public void setMotorizedYear(String motorizedYear) {
		this.motorizedYear = motorizedYear;
	}
	public String getMotorizedModel() {
		return motorizedModel;
	}
	public void setMotorizedModel(String motorizedModel) {
		this.motorizedModel = motorizedModel;
	}
	public String getMotorizedUnicoDue() {
		return motorizedUnicoDue;
	}
	public void setMotorizedUnicoDue(String motorizedUnicoDue) {
		this.motorizedUnicoDue = motorizedUnicoDue;
	}
	@Override
	public String toString() {
		return "Yng_Motorized [motorizedId=" + motorizedId + ", motorizedBrand=" + motorizedBrand + ", motorizedYear="
				+ motorizedYear + ", motorizedModel=" + motorizedModel + ", motorizedUnicoDue=" + motorizedUnicoDue
				+ ", yng_Item=" + yng_Item + "]";
	}

	
	
	

}
