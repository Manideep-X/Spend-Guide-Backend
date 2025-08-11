package com.manideep.spendguide.service;

import java.util.List;

import com.manideep.spendguide.dto.CategoryDTO;

public interface CategoryService {

    // Save the category to the DB.
    CategoryDTO saveCategoryDTO(CategoryDTO categoryDTO);

    // Returns all categories of currently logged-in user.
    List<CategoryDTO> getCategoriesForCurrAcc();

    // Returns all categories of a type of currently logged-in user
    List<CategoryDTO> getCategoriesByTypeForCurrAcc(String type);

    // Updates a category by it's Id
    CategoryDTO updateCategoryById(Long categoryId, CategoryDTO categoryDTO);

}
