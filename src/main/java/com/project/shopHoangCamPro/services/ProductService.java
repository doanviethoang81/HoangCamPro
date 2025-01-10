package com.project.shopHoangCamPro.services;

//import com.project.shopHoangCamPro.dtos.ProductDTO;
import com.project.shopHoangCamPro.dao.ProductDAO;
import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.repository.CategoryRepository;
import com.project.shopHoangCamPro.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    ProductDAO productDAO;

    @Override
    public List<Product> getAll(){
        return this.productRepository.findAll();
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);//8
    }

    @Override
    public void deleteById(String id) {
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<Product> findAllById(List<String> id) {
        return List.of();
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>)productRepository.findAll();//9
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public Optional<Product> findById(String name) {
        return productRepository.findById(Integer.valueOf(name));//10
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);//11
    }

    @Override
    public Page<Product> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,10);
        return this.productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getProductsByCategoryId(Long categoryId, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1 ,10);
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Product getProductById(int id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException(
                        "Cannot find produc with id= "+ id));
    }

    @Override
    public Product getProductByName(String name) throws Exception {
        return productRepository.findByName(name)
                .orElseThrow(()-> new UsernameNotFoundException(
                        "Cannot find produc with name= "+ name));
    }


    public List<Product> getProductsByCategoryId(int categoryId) throws Exception {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products;
    }

    public void updateProduct(Integer id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setBrand(updatedProduct.getBrand());
            product.setDescription(updatedProduct.getDescription());
            product.setImageUrl(updatedProduct.getImageUrl());
            product.setCategory(updatedProduct.getCategory());

            productRepository.save(product);
        }
    }

    public void deleteProductById(Integer id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchProductsByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

//
////    @Override
////    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
////        // LẤY danh sách sp theo trang page và giới hạn limit
////        return productRepository
////                .findAll(pageRequest)
////                .map(ProductResponse::fromProduct); // tham chieeuf dden phuowgn thuc static cua class productresponse
////    }
//
//    @Override
//    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
//            Product existingProduct = getProductById(id);
//            if(existingProduct != null){
//                //copy cac thuoc tinh tu DTO sang cho product
//                // co the sd ModelMapper
//                Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
//                        .orElseThrow(()-> new UsernameNotFoundException(
//                                "cannot find category with id "+ productDTO.getCategoryId()));
//                existingProduct.setName(productDTO.getName());
//                existingProduct.setBrand(productDTO.getBrand());
//                existingProduct.setImageUrl(productDTO.getImageUrl());
//                existingProduct.setDescription(productDTO.getDescription());
//                existingProduct.setCategory(existingCategory);
////                existingProduct.setPrice(productDTO.getPrice());
//                existingProduct.setDescription(productDTO.getDescription());
//
//                return productRepository.save(existingProduct);
//
//            }
//            return  null;
//    }
//
//    @Override
//    public void deleteProduct(long id) {
//        Optional<Product> optionalProduct = productRepository.findById(id);
//        optionalProduct.ifPresent(productRepository::delete);
//    }
//
//    @Override
//    public boolean existsByName(String name) {
//        return productRepository.existsByName(name);
//    }
////    private final ProductImageRepository productImageRepository;
//

}