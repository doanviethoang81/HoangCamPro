package com.project.shopHoangCamPro.dao;

import com.project.shopHoangCamPro.models.Order;
import com.project.shopHoangCamPro.models.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailDAO extends CrudRepository<OrderDetail, Integer> {
}
