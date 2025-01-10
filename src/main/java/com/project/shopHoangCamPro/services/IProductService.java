package com.project.shopHoangCamPro.services;

//import com.project.shopHoangCamPro.dtos.ProductDTO;
import com.project.shopHoangCamPro.models.Category;
import com.project.shopHoangCamPro.models.Product;
//import com.project.shopHoangCamPro.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
//import com.project.shopHoangCamPro.repository.ProductRepository;

public interface IProductService {
    Product getProductById(int id) throws Exception;

    Product getProductByName(String name) throws Exception;

    List<Product> getAll();

    void delete(Product product);

    void deleteById(String id);

    long count();

    List<Product> findAllById(List<String> id);

    List<Product> findAll();

    boolean existsById(String id);

    Optional<Product> findById(String id);


    Product save(Product product);

    Page<Product> getAll(Integer pageNo);

    Page<Product> getProductsByCategoryId(Long categoryId, Integer pageNo);


}
