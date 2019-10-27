package com.github.hse24.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "category")
@ApiModel(description = "All details about the Category. ")
public class Category extends AbstractEntity {
	
	public Category() {
		super();
	}
	
    @Column(name = "name", nullable = false)
    @ApiModelProperty(notes = "The name of the category")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parentid")
    @JsonIgnore
    @ApiModelProperty(notes = "Parent category")
    private Category parent;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories",cascade = CascadeType.REMOVE)
    @ApiModelProperty(notes = "The List of product belongs to the category")
    private Set<Product> products;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ApiModelProperty(notes = "The List of sub-category belongs to the category")
    private Set<Category> childCategories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(Set<Category> childCategories) {
        this.childCategories = childCategories;
    }

}
