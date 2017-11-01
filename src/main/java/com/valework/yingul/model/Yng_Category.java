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

@Entity
public class Yng_Category {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoryId", nullable = false, updatable = false)
	private int categoryId;
	private String name;
	private int level;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "father_id")
    private Yng_Category yng_Category;
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Yng_ItemCategory> itemCategory = new HashSet<>();
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Yng_Category getYng_Category() {
		return yng_Category;
	}
	public void setYng_Category(Yng_Category yng_Category) {
		this.yng_Category = yng_Category;
	}
	public Set<Yng_ItemCategory> getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(Set<Yng_ItemCategory> itemCategory) {
		this.itemCategory = itemCategory;
	}
	@Override
	public String toString() {
		return "Yng_Category [categoryId=" + categoryId + ", name=" + name + ", level=" + level + ", yng_Category="
				+ yng_Category + ", itemCategory=" + itemCategory + "]";
	}
	
	
	
}
