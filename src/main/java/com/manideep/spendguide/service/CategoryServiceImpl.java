package com.manideep.spendguide.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.manideep.spendguide.dto.CategoryDTO;
import com.manideep.spendguide.entity.CategoryEntity;
import com.manideep.spendguide.entity.ProfileEntity;
import com.manideep.spendguide.mapper.CategoryMapper;
import com.manideep.spendguide.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProfileService profileService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
            ProfileService profileService) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.profileService = profileService;
    }

    // save the category to the DB
    @Override
    public CategoryDTO saveCategoryDTO(CategoryDTO categoryDTO) {

        ProfileEntity profileEntity = profileService.getCurrentAccount();
        CategoryEntity newCategoryEntity = null;

        // SQL will throw exception if the category already exists
        try {
            newCategoryEntity = categoryMapper.dtoToEntity(categoryDTO, profileEntity);
            newCategoryEntity = categoryRepository.save(newCategoryEntity);
        } catch (Exception e) {
            throw new RuntimeException("This category already exists!");
        }

        return categoryMapper.entityToDto(newCategoryEntity);

    }

    // Returns all categories of currently logged-in user.
    @Override
    public List<CategoryDTO> getCategoriesForCurrAcc() {

        ProfileEntity profileEntity = profileService.getCurrentAccount();
        List<CategoryEntity> categories = categoryRepository.findByProfileId(profileEntity.getId());

        // convert each category from entity to DTO and returns the list.
        return categories
                .stream()
                .map(category -> categoryMapper.entityToDto(category))
                .toList();

    }

    // Returns all categories of similar type for currently logged-in user.
    @Override
    public List<CategoryDTO> getCategoriesByTypeForCurrAcc(String type) {

        ProfileEntity profileEntity = profileService.getCurrentAccount();
        List<CategoryEntity> categories = categoryRepository.findByTypeAndProfileId(type, profileEntity.getId());

        // convert each category from entity to DTO and returns the list.
        return categories
                .stream()
                .map(category -> categoryMapper.entityToDto(category))
                .toList();

    }

    // Updates a category by taking it's Id and object for currently logged-in user.
    @Override
    public CategoryDTO updateCategoryById(Long categoryId, CategoryDTO categoryDTO) {
        
        ProfileEntity profileEntity = profileService.getCurrentAccount();
        CategoryEntity categoryEntity = categoryRepository.findByIdAndProfileId(categoryId, profileEntity.getId()).orElseThrow(
            () -> new RuntimeException("Category doesn't exists or restricted to access!")
        );

        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setIconUrl(categoryDTO.getIconUrl());
        categoryEntity.setType(categoryDTO.getType());

        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.entityToDto(categoryEntity);

    }

}
