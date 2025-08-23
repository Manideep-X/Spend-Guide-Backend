package com.manideep.spendguide.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manideep.spendguide.dto.CategoryDTO;
import com.manideep.spendguide.service.CategoryService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    // Save a new Category for the current account user
    @PostMapping
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        
        CategoryDTO newCategoryDTO = null;
        // This will send FORBIDDEN status if the current user not logged-in or token expired
        // And will send CONFLICT if the category is already present for current user
        try {
            newCategoryDTO = categoryService.saveCategoryDTO(categoryDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(newCategoryDTO);

    }

    // Display all categories of the current account user
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {

        List<CategoryDTO> categories;
        // This will send FORBIDDEN status if the current user not logged-in or token expired
        try {
            categories = categoryService.getCategoriesForCurrAcc();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return ResponseEntity.ok(categories);
    }
    
    // Displays all categories of a perticular type for current account user.
    @GetMapping("/{category_type}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByType(@PathVariable("category_type") String type) {

        // This will send FORBIDDEN status if the current user not logged-in or token expired
        List<CategoryDTO> categories;
        try {
            categories = categoryService.getCategoriesByTypeForCurrAcc(type);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return ResponseEntity.ok(categories);
    }
    
    // Updates category by it's Id for current account user.
    @PutMapping("/{category_id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("category_id") Long id, @RequestBody CategoryDTO categoryDTO) {

        // This will send FORBIDDEN status if the current user not logged-in or token expired
        // And will send NOT_FOUND if the category is not present for current user
        try {
            categoryDTO = categoryService.updateCategoryById(id, categoryDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }

}
