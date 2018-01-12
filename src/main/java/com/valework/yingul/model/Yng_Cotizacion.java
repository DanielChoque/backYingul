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


	private double tarifa;

	private String called;
	private String respond;
	private String fecha;
	private String idUser;
	private String itemId;

}
