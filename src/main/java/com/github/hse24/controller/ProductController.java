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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.github.hse24.exception.NotFoundException;
import com.github.hse24.converter.ProductResourceConverter;
import com.github.hse24.entity.Product;
import com.github.hse24.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Manoj Mohapatro
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/products")
@Api(value="HSE24 Ecommerce System", description="Operations pertaining to products")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductResourceConverter productResourceAssembler;
    @Autowired
    private PagedResourcesAssembler<Product> pagedResourcesAssembler;
  
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Retrive all products", response = Page.class)
    @ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully retrieved list"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
    public ResponseEntity<?> retrieveAllProducts(Pageable pageable) {
        // Getting all products in application...
    	logger.info("Inside retrieveAllProducts starts");
        final Page<Product> products = productService.getAllProducts(pageable);

        return ResponseEntity.ok(pagedResourcesAssembler.toResource(products, productResourceAssembler));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully retrieved"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
    @ApiOperation(value = "Retrive product details based on id", response = Product.class)
    public ResponseEntity<?> retrieveProduct(@ApiParam(value = "Product id", required = true)@PathVariable Long id) {
        // Getting the requiring product; or throwing exception if not found
    	logger.info("Inside retrieveProduct for id {}",id);
        final Product product = productService.getProductById(id)
            .orElseThrow(() -> new NotFoundException("product"));

        return ResponseEntity.ok(productResourceAssembler.toResource(product));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully created"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
    @ApiOperation(value = "Create product", response = Product.class)
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDto request) {
        // Gets the current logged-in User...
    	logger.info("Inside createProduct for request {}",request);
        // Creating a new product in the application...
        final Product product = productService.createProduct(request.getName(), request.getCurrency(), request.getPrice());

        return ResponseEntity.status(HttpStatus.CREATED).body(productResourceAssembler.toResource(product));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully updated"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
    @ApiOperation(value = "Update existing product based on id", response = Product.class)
    public ResponseEntity<?> updateProduct(@ApiParam(value = "Product id for update", required = true)@PathVariable Long id, @RequestBody @Valid ProductDto request) {
        // Getting the requiring product; or throwing exception if not found
    	logger.info("Inside updateProduct for request {}",request);
        final Product product = productService.getProductById(id)
            .orElseThrow(() -> new NotFoundException("product"));

        // Updating a product in the application...
        productService.updateProduct(product, request.getName(), request.getCurrency(), request.getPrice());

        return ResponseEntity.ok(productResourceAssembler.toResource(product));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Successfully deleted"),
		    @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		})
    @ApiOperation(value = "Delete product based on id")
    public ResponseEntity<?> deleteProduct(@ApiParam(value = "Product id for delete", required = true)@PathVariable Long id) {
        // Getting the requiring product; or throwing exception if not found
    	logger.info("Inside deleteProduct for id {}",id);
        final Product product = productService.getProductById(id)
            .orElseThrow(() -> new NotFoundException("product"));

        // Deleting product from the application...
        productService.deleteProduct(product);

        return ResponseEntity.noContent().build();
    }

    static class ProductDto {
        @NotNull(message = "name is required")
        @Size(message = "name must be equal to or lower than 300", min = 1, max = 300)
        private String name;
        @NotNull
        @Size(message = "Currency must be in ISO 4217 format", min = 3, max = 3)
        private String currency;
        @NotNull(message = "name is required")
        @Min(0)
        private Double price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }

}