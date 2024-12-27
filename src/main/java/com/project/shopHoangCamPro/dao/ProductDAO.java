package com.project.shopHoangCamPro.dao;

import com.project.shopHoangCamPro.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductDAO extends CrudRepository<Product, Integer> {
}
