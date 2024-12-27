package com.project.shopHoangCamPro.repository;

import com.project.shopHoangCamPro.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    boolean existsByUserId(Integer userId);

    Cart findByUserId(Integer id);

}
