package com.project.shopHoangCamPro.services;

//import com.project.shopHoangCamPro.dtos.CategoryDTO;
import com.project.shopHoangCamPro.models.Category;
import com.project.shopHoangCamPro.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor // tu tao ham controuctor tu tuong khi co thuoc tinh final
public class CategoryService implements ICategoryService{//implements kees thu tu interface

    private final CategoryRepository categoryRepository;

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
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
}
