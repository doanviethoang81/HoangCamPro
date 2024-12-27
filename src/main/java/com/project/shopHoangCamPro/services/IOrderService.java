package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.models.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {

//    Order getProductById(int id) throws Exception;

//    Product getByName(String name) throws Exception;
//
////    Page<ProductResponse> getAllProducts(PageRequest pageRequest);
//
//    Order updateProduct(long id, ProductDTO productDTO) throws Exception;
//
//    boolean existsByName(String name);

    List<Order> getAll();
//    ProductImage createProductImage(
//            Long productId,
//            ProductImageDTO productImageDTO) throws Exception;

    void delete(Order order);

    void deleteById(String id);

    long count();

    List<Order> findAllById(List<String> id);

    List<Order> findAll();

    boolean existsById(String id);

    Optional<Order> findById(Integer id);

    Order save(Order order);
}
