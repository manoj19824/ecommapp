package com.github.hse24.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hse24.entity.Category;
import com.github.hse24.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

   
    @Transactional
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

   
    @Transactional
    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

 
    @Transactional
    @Override
    public Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);

        return categoryRepository.save(category);
    }


    @Transactional
    @Override
    public void updateCategory(Category category, String name) {
        category.setName(name);
        categoryRepository.save(category);
    }


    @Transactional
    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

   
    @Transactional
    @Override
    public boolean isChildCategory(Category category, Category parent) {
        return category.getParent().equals(parent);
    }

 
    @Transactional
    @Override
    public void addChildCategory(Category category, Category parent) {
        category.setParent(parent);
        categoryRepository.save(category);
    }


    @Transactional
    @Override
    public void removeChildCategory(Category category, Category parent) {
        category.setParent(null);
        categoryRepository.save(category);
    }
}