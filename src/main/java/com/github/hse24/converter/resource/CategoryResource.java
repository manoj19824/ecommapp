package com.github.hse24.converter.resource;

import org.springframework.hateoas.ResourceSupport;

public class CategoryResource extends ResourceSupport {

    private final String name;
    private final Long categoryId;

    public CategoryResource(String name,Long categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

	public Long getCategoryId() {
		return categoryId;
	}
    
}
