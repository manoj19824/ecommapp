package com.github.hse24.service;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.hse24.entity.Category;
import com.github.hse24.entity.Product;
import com.github.hse24.repository.ProductRepository;
import com.github.hse24.util.CurrencyExchangeCommand;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CurrencyExchangeCommand currencyExchangeCommand;

   
    @Transactional
    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

   
    @Transactional
    @Override
    public Page<Product> getAllProducts(Category category, Pageable page) {
        return productRepository.findByAssociatedWithCategory(category.getId(), page);
    }

    
    @Transactional
    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

   
    @Transactional
    @Override
    public Product createProduct(String name, String currency, double price) {
        if (!Product.CURRENCY.equals(currency)) {
            price = currencyExchangeCommand.convert(currency, Product.CURRENCY, price);
        }

        // Round up only 2 decimals...
        price = (double) Math.round(price * 100) / 100;

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return productRepository.save(product);
    }

   
    @Transactional
    @Override
    public void updateProduct(Product product, String name, String currency, double price) {
        if (!Product.CURRENCY.equals(currency)) {
            price = currencyExchangeCommand.convert(currency, Product.CURRENCY, price);
        }

        // Round up only 2 decimals...
        price = (double) Math.round(price * 100) / 100;

        product.setName(name);
        product.setPrice(price);
        productRepository.save(product);
    }

   
    @Transactional
    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

  
    @Transactional
    @Override
    public boolean hasCategory(Product product, Category category) {
        return product.getCategories().contains(category);
    }


    @Transactional
    @Override
    public void addCategory(Product product, Category category) {
        product.getCategories().add(category);
        productRepository.save(product);
    }

  
    @Transactional
    @Override
    public void removeCategory(Product product, Category category) {
        product.getCategories().remove(category);
        productRepository.save(product);
    }

   
    @Transactional
    @Override
    public boolean hasProductsAssociated(Category category) {
        return productRepository.countByAssociatedWithCategory(category.getId()) > 0;
    }

}
