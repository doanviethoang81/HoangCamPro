package com.project.shopHoangCamPro.services;

//import com.project.shopHoangCamPro.dtos.ProductDTO;
import com.project.shopHoangCamPro.models.Category;
import com.project.shopHoangCamPro.models.Product;
//import com.project.shopHoangCamPro.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
//import com.project.shopHoangCamPro.repository.ProductRepository;

public interface IProductService {
//    Product createProduct(ProductDTO productDTO) throws Exception;
//
    Product getProductById(int id) throws Exception;

    Product getProductByName(String name) throws Exception;
//
////    Page<ProductResponse> getAllProducts(PageRequest pageRequest);
//
//    Product updateProduct(long id, ProductDTO productDTO) throws Exception;
//
//    boolean existsByName(String name);

    List<Product> getAll();
//    ProductImage createProductImage(
//            Long productId,
//            ProductImageDTO productImageDTO) throws Exception;

    void delete(Product product);

    void deleteById(String id);

    long count();

    List<Product> findAllById(List<String> id);

    List<Product> findAll();

    boolean existsById(String id);

    Optional<Product> findById(String id);


    Product save(Product product);


}
