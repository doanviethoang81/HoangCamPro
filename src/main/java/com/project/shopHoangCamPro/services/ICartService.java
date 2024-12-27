package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.models.Cart;
import com.project.shopHoangCamPro.models.Order;

import java.util.List;
import java.util.Optional;

public interface ICartService {

    void delete(Cart cart);

    void deleteById(Integer id);

    long count();

    List<Cart> findAllById(List<Integer> id);

    List<Cart> findAll();

    boolean existsById(String id);

    Optional<Cart> findById(Integer id);

    Cart save(Cart cart);
}
