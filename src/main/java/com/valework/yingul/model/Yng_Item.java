package com.valework.yingul.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Yng_Item {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "itemId", nullable = false, updatable = false)
    private Long itemId;
	private String price;
	private String description;
	private String name;
	private String horario;
	@OneToOne
	private Yng_Ubication yng_Ubication;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Yng_User user;

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)  
    private Set<Yng_ItemCategory> itemCategory = new HashSet<>();
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public Yng_User getUser() {
		return user;
	}
	public void setUser(Yng_User user) {
		this.user = user;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Yng_Ubication getYng_Ubication() {
		return yng_Ubication;
	}
	public void setYng_Ubication(Yng_Ubication yng_Ubication) {
		this.yng_Ubication = yng_Ubication;
	}
	public Set<Yng_ItemCategory> getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(Set<Yng_ItemCategory> itemCategory) {
		this.itemCategory = itemCategory;
	}
	@Override
	public String toString() {
		return "Yng_Item [itemId=" + itemId + ", price=" + price + ", description=" + description + ", name=" + name
				+ ", horario=" + horario + ", yng_Ubication=" + yng_Ubication + ", user=" + user + ", itemCategory="
				+ itemCategory + "]";
	}
	

	
}
