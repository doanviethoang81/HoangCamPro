package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.models.ProductVariant;

import java.util.List;
import java.util.Optional;

public interface IProductVariantService {

    ProductVariant getProductVariantById(int id) throws Exception;

    ProductVariant getProductVariantByName(String name) throws Exception;


    public List<ProductVariant> getProductVariantsByProductId(int id) throws  Exception;

    Optional<ProductVariant> findById(int id);

    ProductVariant save(ProductVariant productVariant);

    List<ProductVariant> saveAll(List<ProductVariant> productVariants);
}
