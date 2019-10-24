package com.github.hse24.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.hse24.converter.ProductResourceConverter;
import com.github.hse24.entity.Category;
import com.github.hse24.entity.Product;
import com.github.hse24.exception.NotFoundException;
import com.github.hse24.service.CategoryService;
import com.github.hse24.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author Manoj Mohapatro
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/categories/{categoryid}/products")
@Api(value="HSE24 Ecommerce System", description="Operations pertaining to categories and products")
public class CategoryProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryProductController.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductResourceConverter productResourceConverter;
    @Autowired
    private PagedResourcesAssembler<Product> pagedResourcesAssembler;
  

    @RequestMapping(method = RequestMethod.GET)
    @ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully retrieved list"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
    @ApiOperation(value = "Retrive all products based on category", response = Page.class)
    public ResponseEntity<?> retrieveAllProducts(@ApiParam(value = "Category id for list of products", required = true)@PathVariable Long categoryid, Pageable pageable) {
        // Getting the requiring category; or throwing exception if not found
    	logger.info("Inside retrieveAllProducts for id {}",categoryid);
        final Category category = categoryService.getCategoryById(categoryid)
            .orElseThrow(() -> new NotFoundException("category"));

        // Getting all products in application...
        final Page<Product> products = productService.getAllProducts(category, pageable);

        return ResponseEntity.ok(pagedResourcesAssembler.toResource(products, productResourceConverter));
    }

    @RequestMapping(path = "/{productid}", method = RequestMethod.POST)
    @ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully added"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
    @ApiOperation(value = "Add product under category", response = Product.class)
    public ResponseEntity<?> addProduct(@ApiParam(value = "Category id for adding product", required = true)@PathVariable Long categoryid, @PathVariable Long productid) {
        // Getting the requiring category; or throwing exception if not found
    	logger.info("Inside addProduct for id {}",categoryid);
        final Category category = categoryService.getCategoryById(categoryid)
            .orElseThrow(() -> new NotFoundException("category"));

        // Getting the requiring product; or throwing exception if not found
        final Product product = productService.getProductById(productid)
            .orElseThrow(() -> new NotFoundException("product"));

        // Validating if association does not exist...
        if (productService.hasCategory(product, category)) {
            throw new IllegalArgumentException("product " + product.getId() + " already contains category " + category.getId());
        }

        // Associating product with category...
        productService.addCategory(product, category);

        return ResponseEntity.status(HttpStatus.CREATED).body(productResourceConverter.toResource(product));
    }

    @RequestMapping(path = "/{productid}", method = RequestMethod.DELETE)
    @ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully removed"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
    @ApiOperation(value = "Remove product from category")
    public ResponseEntity<?> removeProduct(@ApiParam(value = "Category id for which product will be deleted", required = true)@PathVariable Long categoryid, @PathVariable Long productid) {
        // Getting the requiring category; or throwing exception if not found
    	logger.info("Inside removeProduct for id {}",categoryid);
        final Category category = categoryService.getCategoryById(categoryid)
            .orElseThrow(() -> new NotFoundException("category"));

        // Getting the requiring product; or throwing exception if not found
        final Product product = productService.getProductById(productid)
            .orElseThrow(() -> new NotFoundException("product"));

        // Validating if association does not exist...
        if (!productService.hasCategory(product, category)) {
            throw new IllegalArgumentException("product " + product.getId() + " does not contain category " + category.getId());
        }

        // Dis-associating product with category...
        productService.removeCategory(product, category);

        return ResponseEntity.noContent().build();
    }

}