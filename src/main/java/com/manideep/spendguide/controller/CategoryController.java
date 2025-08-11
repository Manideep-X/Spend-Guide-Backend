package com.manideep.spendguide.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manideep.spendguide.dto.CategoryDTO;
import com.manideep.spendguide.service.CategoryService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        
        CategoryDTO newCategoryDTO = categoryService.saveCategoryDTO(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategoryDTO);

    }

    // Display all categories of the current account user
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategoriesForCurrAcc());
    }
    
    // Displays all categories of a perticular type for current account user.
    @GetMapping("/{category_type}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByType(@PathVariable("category_type") String type) {
        return ResponseEntity.ok(categoryService.getCategoriesByTypeForCurrAcc(type));
    }
    
    // Updates category by it's Id for current account user.
    @PutMapping("/{category_id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("category_id") Long id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.updateCategoryById(id, categoryDTO));
    }

}
