package com.github.hse24.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.hse24.converter.CategoryResourceConverter;
import com.github.hse24.entity.Category;
import com.github.hse24.exception.NotFoundException;
import com.github.hse24.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import io.swagger.annotations.ApiResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Manoj Mohapatro
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/categories")
@Api(value="HSE24 Ecommerce System", description="Operations pertaining to categories")
public class CategoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;
	@Autowired
    private CategoryResourceConverter categoryResourceConverter;
	
	@RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Retrive all categories", response = List.class)
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully retrieved list"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
	public ResponseEntity<?> retrieveAllCategories() {
		// Getting all categories in application...
		logger.info("Inside retrieveAllCategories starts");
		final List<Category> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categoryResourceConverter.toResources(categories));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Retrive categorie based on Id", response = Category.class)
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully retrieved list"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
	public ResponseEntity<?> retrieveCategory(@ApiParam(value = "Category id", required = true)@PathVariable Long id) {
		// Getting the requiring category; or throwing exception if not found
		logger.info("Inside retrieveCategory for id {}",id);
		final Category category = categoryService.getCategoryById(id)
				.orElseThrow(() -> new NotFoundException("category"));

		return ResponseEntity.ok(categoryResourceConverter.toResource(category));
	}

	@RequestMapping(method = RequestMethod.POST)
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully created"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
	@ApiOperation(value = "Create a new categorie", response = Category.class)
	public ResponseEntity<?> createCategory(@RequestBody @Valid @ApiParam(value = "Category data needs to be created", required = true)CategoryDto request) {
		// Creating a new category in the application...
		logger.info("Inside createCategory for request {}",request);
		final Category category = categoryService.createCategory(request.getName());

		return ResponseEntity.status(HttpStatus.CREATED).body(categoryResourceConverter.toResource(category));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully updated"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
	@ApiOperation(value = "Update existing categorie", response = Category.class)
	public ResponseEntity<?> updateCategory(@ApiParam(value = "Category id needs to be updated", required = true)@PathVariable Long id, @RequestBody @Valid CategoryDto request) {
		// Getting the requiring category; or throwing exception if not found
		logger.info("Inside updateCategory for request {}",request);
		final Category category = categoryService.getCategoryById(id)
				.orElseThrow(() -> new NotFoundException("category"));

		// Updating a category in the application...
		categoryService.updateCategory(category, request.getName());

		return ResponseEntity.ok(categoryResourceConverter.toResource(category));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully deleted"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
	@ApiOperation(value = "Delete existing categorie based on Id")
	public ResponseEntity<?> deleteCategory(@ApiParam(value = "Category id needs to be deleted", required = true)@PathVariable Long id) {
		// Getting the requiring category; or throwing exception if not found
		logger.info("Inside deleteCategory for id {}",id);
		final Category category = categoryService.getCategoryById(id)
				.orElseThrow(() -> new NotFoundException("category"));

		// Deleting category from the application...
		categoryService.deleteCategory(category);

		return ResponseEntity.noContent().build();
	}

	static class CategoryDto {
		@NotNull(message = "name is required")
		@Size(message = "name must be equal to or lower than 100", min = 1, max = 100)
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}