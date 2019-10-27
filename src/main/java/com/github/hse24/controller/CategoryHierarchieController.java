package com.github.hse24.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.hse24.entity.Category;
import com.github.hse24.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/categories-hierarchie")
@Api(value = "HSE24 Ecommerce System", description = "Operations pertaining to categories")
public class CategoryHierarchieController {
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Retrive all categories", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<?> retrieveAllCategories() {
		// Getting all categories in application...
		final List<Category> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}

}
