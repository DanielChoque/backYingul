package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Yng_Ubication {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ubicationId", nullable = false, updatable = false)
    private Long ubicationId;
	private String latitud;
	private String longitud;
	private String address;
	@OneToOne
    private Yng_City yng_City;
	@OneToOne
    private Yng_Province yng_Province;
	@OneToOne
    private Yng_Department yng_Department;
	
	public Long getUbicationId() {
		return ubicationId;
	}
	public void setUbicationId(Long ubicationId) {
		this.ubicationId = ubicationId;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public Yng_City getYng_City() {
		return yng_City;
	}
	public void setYng_City(Yng_City yng_City) {
		this.yng_City = yng_City;
	}
	public Yng_Province getYng_Province() {
		return yng_Province;
	}
	public void setYng_Province(Yng_Province yng_Province) {
		this.yng_Province = yng_Province;
	}
	public Yng_Department getYng_Department() {
		return yng_Department;
	}
	public void setYng_Department(Yng_Department yng_Department) {
		this.yng_Department = yng_Department;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Yng_Ubication [ubicationId=" + ubicationId + ", latitud=" + latitud + ", longitud=" + longitud
				+ ", address=" + address + ", yng_City=" + yng_City + ", yng_Province=" + yng_Province
				+ ", yng_Department=" + yng_Department + "]";
	}

	
}
