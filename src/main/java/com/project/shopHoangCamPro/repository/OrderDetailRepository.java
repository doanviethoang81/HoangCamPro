package com.project.shopHoangCamPro.repository;

import com.project.shopHoangCamPro.models.OrderDetail;
import com.project.shopHoangCamPro.models.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> findByOrderId(int orderId);


}
