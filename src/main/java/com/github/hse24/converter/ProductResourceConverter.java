package com.github.hse24.converter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.lang.Collections;
import com.github.hse24.controller.ProductController;
import com.github.hse24.converter.resource.ProductResource;
import com.github.hse24.entity.Product;
@Component
public class ProductResourceConverter extends ResourceAssemblerSupport<Product, ProductResource> {

	@Autowired
	private CategoryResourceConverter categoryResourceConverter;

	public ProductResourceConverter() {
		super(ProductController.class, ProductResource.class);
	}

	@Override
	protected ProductResource instantiateResource(Product entity) {
		return new ProductResource(entity.getId(),entity.getName(), Product.CURRENCY, entity.getPrice(),
				!Collections.isEmpty(entity.getCategories())
						? categoryResourceConverter.toResources(entity.getCategories())
						: null);
	}

	@Override
	public ProductResource toResource(Product entity) {
		return createResourceWithId(entity.getId(), entity);
	}

}
