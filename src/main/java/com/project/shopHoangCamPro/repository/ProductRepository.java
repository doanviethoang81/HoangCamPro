package com.project.shopHoangCamPro.repository;

import com.project.shopHoangCamPro.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);

    List<Product> findByCategoryId(int categoryId);

    Optional<Product> findByName(String name);

    List<Product> findByNameContainingIgnoreCase(String name);

//    Page<Product> findAll(Pageable pageable);    //phaan trang san pham
}
