package com.project.shopHoangCamPro.dao;

import com.project.shopHoangCamPro.models.Order;
import com.project.shopHoangCamPro.models.User;
import org.springframework.data.repository.CrudRepository;

public interface OrderDAO extends CrudRepository<Order, Integer> {
}
