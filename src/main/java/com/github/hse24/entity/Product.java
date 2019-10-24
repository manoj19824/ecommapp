package com.github.hse24.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "product")@ApiModel(description = "All details about the Product. ")
public class Product extends AbstractEntity {
	
	public Product() {
		super();
	}

	public static final String CURRENCY = "EUR";

	@Column(name = "name", nullable = false)
	@ApiModelProperty(notes = "The name of the product")
	private String name;

	@ManyToMany()
	@ApiModelProperty(notes = "The product belongs to the categories")
	@JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "productid"), inverseJoinColumns = @JoinColumn(name = "categoryid"))
	@JsonIgnore
	private Set<Category> categories;

	@Column(name = "price", nullable = false)
	@ApiModelProperty(notes = "The price of the product")
	private double price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
