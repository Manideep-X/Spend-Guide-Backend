package com.manideep.spendguide.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.manideep.spendguide.dto.CategoryDTO;

public interface CategoryService {

    // Save the category to the DB.
    CategoryDTO saveCategoryDTO(CategoryDTO categoryDTO) throws UsernameNotFoundException, RuntimeException;

    // Returns all categories of currently logged-in user.
    List<CategoryDTO> getCategoriesForCurrAcc() throws UsernameNotFoundException;

    // Returns all categories of a type of currently logged-in user
    List<CategoryDTO> getCategoriesByTypeForCurrAcc(String type) throws UsernameNotFoundException;

    // Updates a category by it's Id
    CategoryDTO updateCategoryById(Long categoryId, CategoryDTO categoryDTO) throws UsernameNotFoundException, RuntimeException;

}
