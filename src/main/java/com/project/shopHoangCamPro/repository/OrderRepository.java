package com.project.shopHoangCamPro.repository;

import com.project.shopHoangCamPro.models.Cart;
import com.project.shopHoangCamPro.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order getById(Integer id);

    boolean existsByUserId(Integer userId);

    List<Order> findByUserId(Integer id);

}
