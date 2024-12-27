package com.project.shopHoangCamPro.repository;

import com.project.shopHoangCamPro.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
