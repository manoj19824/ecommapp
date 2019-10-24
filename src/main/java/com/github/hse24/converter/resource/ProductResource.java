package com.github.hse24.converter.resource;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class ProductResource extends ResourceSupport {

	private final Long productId;
    private final String name;
    private final String currency;
    private final double price;
    private final List<CategoryResource> categories;
    
    public ProductResource(Long productId,String name, String currency, double price, List<CategoryResource> categories) {
        this.productId = productId;
    	this.name = name;
        this.currency = currency;
        this.price = price;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getPrice() {
        return price;
    }

    public List<CategoryResource> getCategories() {
        return categories;
    }

	public Long getProductId() {
		return productId;
	}
    
 }