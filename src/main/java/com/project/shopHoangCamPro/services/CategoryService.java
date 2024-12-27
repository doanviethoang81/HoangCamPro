package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.dtos.CategoryDTO;
import com.project.shopHoangCamPro.models.Category;
import com.project.shopHoangCamPro.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor // tu tao ham controuctor tu tuong khi co thuoc tinh final
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;
    @Override
    public Category createCategory(CategoryDTO categoryDTO)  {
        Category newCategory = Category.builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("category not found"));
    }

    @Override
    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
}
