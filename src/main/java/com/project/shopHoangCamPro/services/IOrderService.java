package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.models.Order;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IOrderService {

    List<Order> getAll();

    void delete(Order order);

    void deleteById(String id);

    long count();

    List<Order> findAllById(List<String> id);

    List<Order> findAll();

    boolean existsById(String id);

    Optional<Order> findById(Integer id);

    Order save(Order order);

    Page<Order> getAll(Integer pageNo);
}
