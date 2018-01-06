package com.valework.yingul.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Yng_Buy {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "buyId", nullable = false, updatable = false)
    private Long buyId;
	private double cost;
	private String money;
	private int quantity;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Yng_User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Yng_Item yng_item;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentMethod_id")
    private Yng_PaymentMethod yng_PaymentMethod;

	public Long getBuyId() {
		return buyId;
	}

	public void setBuyId(Long buyId) {
		this.buyId = buyId;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Yng_User getUser() {
		return user;
	}

	public void setUser(Yng_User user) {
		this.user = user;
	}

	public Yng_Item getYng_item() {
		return yng_item;
	}

	public void setYng_item(Yng_Item yng_item) {
		this.yng_item = yng_item;
	}

	public Yng_PaymentMethod getYng_PaymentMethod() {
		return yng_PaymentMethod;
	}

	public void setYng_PaymentMethod(Yng_PaymentMethod yng_PaymentMethod) {
		this.yng_PaymentMethod = yng_PaymentMethod;
	}
	
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Yng_Buy [buyId=" + buyId + ", cost=" + cost + ", money=" + money + ", quantity=" + quantity + ", user="
				+ user + ", yng_item=" + yng_item + ", yng_PaymentMethod=" + yng_PaymentMethod + "]";
	}

	//daniel actualizas el to string despues de poner la relacion de envio
	
}
