package com.project.shopHoangCamPro.repository;

import com.project.shopHoangCamPro.models.ProductVariant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {

    List<ProductVariant> findByProductId(int productId);

    @Transactional
    void deleteByProductId(Integer productId);

//    @Query("SELECT v FROM product_variant v ORDER BY v.id ASC")
//    ProductVariant findTopByOrderByIdAsc();



}
