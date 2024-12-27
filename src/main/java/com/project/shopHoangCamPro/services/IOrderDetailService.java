package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.models.Order;
import com.project.shopHoangCamPro.models.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailService {

//     getProductById(int id) throws Exception;

//     getProductByName(String name) throws Exception;
//
////    Page<ProductResponse> getAllProducts(PageRequest pageRequest);
//
//     updateProduct(long id, ProductDTO productDTO) throws Exception;
//
//    boolean existsByName(String name);

    List<OrderDetail> getAll();
//    ProductImage createProductImage(
//            Long productId,
//            ProductImageDTO productImageDTO) throws Exception;

    void delete(OrderDetail order);

    void deleteById(String id);

    long count();

    List<OrderDetail> findAllById(List<String> id);

    List<OrderDetail> findAll();

    boolean existsById(String id);

    Optional<OrderDetail> findById(Integer id);

    OrderDetail save(OrderDetail order);

    List<OrderDetail> getOrderDetailByOrderId(int id) throws Exception;
}
