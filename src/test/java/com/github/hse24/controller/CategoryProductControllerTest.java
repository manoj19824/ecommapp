package com.github.hse24.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.github.hse24.converter.CategoryResourceConverter;
import com.github.hse24.converter.ProductResourceConverter;
import com.github.hse24.entity.Category;
import com.github.hse24.entity.Product;
import com.github.hse24.service.CategoryService;
import com.github.hse24.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CategoryProductController.class, secure = false)
@EnableSpringDataWebSupport
public class CategoryProductControllerTest {

    private static final String URL = "/categories/{categoryid}/products";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    @SpyBean
    private CategoryResourceConverter categoryResourceConverter;
    @SpyBean
    private ProductResourceConverter productResourceConverter;
    @SpyBean
    private PagedResourcesAssembler<Product> pagedResourcesAssembler;
    @MockBean
    private ProductService productService;

    @Test
    public void testRetrieveAllProducts() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("C1");
        Product product1 = new Product();
        product1.setId(10L);
        product1.setName("P1");
        product1.setPrice(100.00);
        product1.setCategories(Collections.singleton(category));
        Product product2 = new Product();
        product2.setId(11L);
        product2.setName("P2");
        product2.setPrice(130.67);
        product2.setCategories(Collections.singleton(category));
       
        Mockito.when(categoryService.getCategoryById(Mockito.eq(1L))).thenReturn(Optional.of(category));
        Mockito.when(productService.getAllProducts(Mockito.eq(category), Mockito.any())).thenReturn(
            new PageImpl<>(Arrays.asList(product1, product2), PageRequest.of(2, 20), 100)
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL, "1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$._links.first.href", Matchers.is("http://localhost/categories/1/products?page=0&size=20")))
            .andExpect(MockMvcResultMatchers.jsonPath("$._links.prev.href", Matchers.is("http://localhost/categories/1/products?page=1&size=20")))
             .andExpect(MockMvcResultMatchers.jsonPath("$.page.size", Matchers.is(20)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements", Matchers.is(100)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalPages", Matchers.is(5)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.page.number", Matchers.is(2)));
    }

    @Test
    public void testAddProduct() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("C1");
        Product product = new Product();
        product.setId(10L);
        product.setName("P1");
        product.setPrice(20.18);
     
        Mockito.when(categoryService.getCategoryById(Mockito.eq(1L))).thenReturn(Optional.of(category));

        // Testing success scenario
        Mockito.when(productService.getProductById(Mockito.eq(10L))).thenReturn(Optional.of(product));
        Mockito.when(productService.hasCategory(Mockito.eq(product), Mockito.eq(category))).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post(URL + "/{productid}", "1", "10"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("P1")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.currency", Matchers.is("EUR")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.is(20.18)))
            .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", Matchers.is("http://localhost/products/10")));
        Mockito.verify(productService).addCategory(Mockito.eq(product), Mockito.eq(category));

        Mockito.reset(productService);

        // Testing fail scenario: product is already associated with category
        Mockito.when(productService.getProductById(Mockito.eq(10L))).thenReturn(Optional.of(product));
        Mockito.when(productService.hasCategory(Mockito.eq(product), Mockito.eq(category))).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post(URL + "/{productid}", "1", "10"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(productService, Mockito.never()).addCategory(Mockito.eq(product), Mockito.eq(category));
    }

    @Test
    public void testRemoveProduct() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("C1");
        Product product = new Product();
        product.setId(10L);
        product.setName("P1");
        product.setPrice(20.18);

        Mockito.when(categoryService.getCategoryById(Mockito.eq(1L))).thenReturn(Optional.of(category));

        // Testing success scenario
        Mockito.when(productService.getProductById(Mockito.eq(10L))).thenReturn(Optional.of(product));
        Mockito.when(productService.hasCategory(Mockito.eq(product), Mockito.eq(category))).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{productid}", "1", "10"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNoContent());
        Mockito.verify(productService).removeCategory(Mockito.eq(product), Mockito.eq(category));

        Mockito.reset(productService);

        // Testing fail scenario: product is not associated with category
        Mockito.when(productService.getProductById(Mockito.eq(10L))).thenReturn(Optional.of(product));
        Mockito.when(productService.hasCategory(Mockito.eq(product), Mockito.eq(category))).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{productid}", "1", "10"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(productService, Mockito.never()).removeCategory(Mockito.eq(product), Mockito.eq(category));
    }

    @Test
    public void testResourceNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL, "0"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.post(URL + "/{productid}", "0", "1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/{productid}", "0", "1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}