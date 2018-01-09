package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yng_Cotizacion {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cotizacionId", nullable = false, updatable = false)
    private Long cotizacionId;

	private String tarifa;
	private String called;
	private String respond;
	private String fecha;
	private String idUser;
	private String userId;
	private String ip;
	private String branchId;
	@Override
	public String toString() {
		return "Yng_Cotizacion [cotizacionId=" + cotizacionId + ", tarifa=" + tarifa + ", called=" + called
				+ ", respond=" + respond + ", fecha=" + fecha + ", idUser=" + idUser + ", userId=" + userId + ", ip="
				+ ip + ", branchId=" + branchId + "]";
	}
	public Long getCotizacionId() {
		return cotizacionId;
	}
	public void setCotizacionId(Long cotizacionId) {
		this.cotizacionId = cotizacionId;
	}
	public String getTarifa() {
		return tarifa;
	}
	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	public String getCalled() {
		return called;
	}
	public void setCalled(String called) {
		this.called = called;
	}
	public String getRespond() {
		return respond;
	}
	public void setRespond(String respond) {
		this.respond = respond;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	

	
	
	
	
}
