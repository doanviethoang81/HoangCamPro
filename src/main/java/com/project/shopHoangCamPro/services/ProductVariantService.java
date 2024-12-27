package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.dao.ProductVariantDAO;
import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.models.ProductVariant;
import com.project.shopHoangCamPro.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductVariantService implements IProductVariantService{

    @Autowired
    ProductVariantDAO productVariantDAO;

    private  final ProductVariantRepository productVariantRepository;

    @Override
    public ProductVariant getProductVariantById(int id) throws Exception {
        return productVariantRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException(
                        "Cannot find product variant with id= "+ id));
    }

    public List<ProductVariant> getProductsByIds(List<Integer> ids) {
        return productVariantRepository.findAllById(ids);
    }

    @Override
    public ProductVariant getProductVariantByName(String name) throws Exception {
//        return productVariantRepository.findByName(name)
//                .orElseThrow(()-> new UsernameNotFoundException(
//                        "Cannot find produc with name= "+ name));
        return null;
    }

    @Override
    public List<ProductVariant> getProductVariantsByProductId(int id) throws Exception{
        List<ProductVariant> productVariants = productVariantRepository.findByProductId(id);
        if (productVariants.isEmpty()) {
            throw new Exception("No products found for product = " + id);
        }
        return productVariants;
    }

    @Override
    public Optional<ProductVariant> findById(int id) {
        return productVariantRepository.findById(id);
    }

    @Override
    public ProductVariant save(ProductVariant productVariant) {
        return productVariantRepository.save(productVariant);//12
    }

    @Override
    public List<ProductVariant> saveAll(List<ProductVariant> productVariants) {
        return (List<ProductVariant>)productVariantRepository.saveAll(productVariants);//13
    }

    public void updateVariantsForProduct(Integer productId, List<ProductVariant> newVariants) {
        productVariantRepository.deleteByProductId(productId);
        productVariantRepository.saveAll(newVariants);
    }

    public void deleteByProductId(Integer productId) {
        productVariantRepository.deleteByProductId(productId);
    }

}
