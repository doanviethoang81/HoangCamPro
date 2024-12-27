package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.dtos.CategoryDTO;
import com.project.shopHoangCamPro.models.Category;

import java.util.List;

public interface ICategoryService {

    Category createCategory(CategoryDTO category);
    Category getCategoryById(long id);

    List<Category> getAllCategorys();

    Category updateCategory(long categoryId, CategoryDTO category);

    void deleteCategory(long id);

    Category getCategoryByName(String name);

}