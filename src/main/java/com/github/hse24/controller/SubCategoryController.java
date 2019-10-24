package com.github.hse24.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.hse24.converter.CategoryResourceConverter;
import com.github.hse24.entity.Category;
import com.github.hse24.entity.Product;
import com.github.hse24.exception.NotFoundException;
import com.github.hse24.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Set;

/**
 * 
 * @author Manoj Mohapatro
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/categories/{parentid}/subcategories")
@Api(value="HSE24 Ecommerce System", description="Operations pertaining to sub-category")
public class SubCategoryController {
	private static final Logger logger = LoggerFactory.getLogger(SubCategoryController.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryResourceConverter categoryResourceConverter;
 

    @RequestMapping(method = RequestMethod.GET)
    @ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully retrieved list"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
    @ApiOperation(value = "Retrive all sub-category from category", response = Set.class)
    public ResponseEntity<?> retrieveAllSubcategories(@ApiParam(value = "Category id", required = true)@PathVariable Long parentid) {
        // Getting the requiring category; or throwing exception if not found
    	logger.info("Inside retrieveAllSubcategories for parentid {}",parentid);
        final Category parent = categoryService.getCategoryById(parentid)
            .orElseThrow(() -> new NotFoundException("parent category"));

        // Getting all categories in application...
        final Set<Category> subcategories = parent.getChildCategories();

        return ResponseEntity.ok(categoryResourceConverter.toResources(subcategories));
    }

    @RequestMapping(path = "/{childid}", method = RequestMethod.POST)
    @ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully added"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
    @ApiOperation(value = "Add sub-category under category", response = Category.class)
    public ResponseEntity<?> addSubcategory(@ApiParam(value = "category id", required = true)@PathVariable Long parentid, @PathVariable Long childid) {
        // Getting the requiring category; or throwing exception if not found
    	logger.info("Inside addSubcategory for parentid {}{}",parentid,childid);
        final Category parent = categoryService.getCategoryById(parentid)
            .orElseThrow(() -> new NotFoundException("parent category"));

        // Getting the requiring category; or throwing exception if not found
        final Category child = categoryService.getCategoryById(childid)
            .orElseThrow(() -> new NotFoundException("child category"));

        // Validating if association does not exist...
        if (categoryService.isChildCategory(child, parent)) {
            throw new IllegalArgumentException("category " + parent.getId() + " already contains subcategory " + child.getId());
        }

        // Associating parent with subcategory...
        categoryService.addChildCategory(child, parent);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResourceConverter.toResource(parent));
    }

    @RequestMapping(path = "/{childid}", method = RequestMethod.DELETE)
    @ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully removed"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
    @ApiOperation(value = "Delete sub-category from category")
    public ResponseEntity<?> removeSubcategory(@ApiParam(value = "category id", required = true)@PathVariable Long parentid, @PathVariable Long childid) {
        // Getting the requiring category; or throwing exception if not found
    	logger.info("Inside removeSubcategory for parentid {}{}",parentid,childid);
        final Category parent = categoryService.getCategoryById(parentid)
            .orElseThrow(() -> new NotFoundException("parent category"));

        // Getting the requiring category; or throwing exception if not found
        final Category child = categoryService.getCategoryById(childid)
            .orElseThrow(() -> new NotFoundException("child category"));

        // Validating if association exists...
        if (!categoryService.isChildCategory(child, parent)) {
            throw new IllegalArgumentException("category " + parent.getId() + " does not contain subcategory " + child.getId());
        }

        // Dis-associating parent with subcategory...
        categoryService.removeChildCategory(child, parent);

        return ResponseEntity.noContent().build();
    }

}
