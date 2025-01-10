package com.project.shopHoangCamPro.repository;

import com.project.shopHoangCamPro.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);

    List<Product> findByCategoryId(int categoryId);

    Optional<Product> findByName(String name);

    List<Product> findByNameContainingIgnoreCase(String name);

    Optional<Product> findById(Integer id);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

//    Page<Product> findAll(Pageable pageable);    //phaan trang san pham
}
