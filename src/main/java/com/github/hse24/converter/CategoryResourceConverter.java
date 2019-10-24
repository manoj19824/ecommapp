package com.github.hse24.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.hse24.controller.CategoryController;
import com.github.hse24.controller.CategoryProductController;
import com.github.hse24.controller.SubCategoryController;
import com.github.hse24.converter.resource.CategoryResource;
import com.github.hse24.entity.Category;
import com.github.hse24.service.ProductService;
@Component
public class CategoryResourceConverter extends ResourceAssemblerSupport<Category, CategoryResource> {

    @Autowired
    private ProductService productService;

    public CategoryResourceConverter() {
        super(CategoryController.class, CategoryResource.class);
    }

    @Override
    protected CategoryResource instantiateResource(Category entity) {
        return new CategoryResource(entity.getName());
    }

    @Override
    public CategoryResource toResource(Category entity) {
        CategoryResource resource = createResourceWithId(entity.getId(), entity);
        if (entity.getParent() != null) {
            resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CategoryController.class).retrieveCategory(entity.getParent().getId())).withRel("parent"));
        }
        if (entity.getChildCategories() != null) {
            resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SubCategoryController.class).retrieveAllSubcategories(entity.getId())).withRel("subcategories"));
        }
        if (productService.hasProductsAssociated(entity)) {
            resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(CategoryProductController.class).retrieveAllProducts(entity.getId(), null)).withRel("products"));
        }

        return resource;
    }

}
