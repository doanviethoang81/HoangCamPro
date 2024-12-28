package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.dao.ProductVariantDAO;
import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.models.ProductVariant;
import com.project.shopHoangCamPro.repository.ProductRepository;
import com.project.shopHoangCamPro.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductVariantService implements IProductVariantService{

    @Autowired
    ProductVariantDAO productVariantDAO;

    private  final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;

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

//    public void updateVariantsForProduct(Integer productId, List<ProductVariant> updatedVariants) {
////        productVariantRepository.deleteByProductId(productId);
////        productVariantRepository.saveAll(newVariants);
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));
//
//        // Lấy tất cả các biến thể hiện tại của sản phẩm từ cơ sở dữ liệu
//        List<ProductVariant> existingVariants = productVariantRepository.findByProductId(productId);
//
//        // Xóa các biến thể không còn trong danh sách cập nhật
//        List<Integer> updatedVariantIds = updatedVariants.stream()
//                .map(ProductVariant::getId) //lấy id của từng biến thể
//                .filter(Objects::nonNull) // Lọc bỏ những id null (biến thể mới chưa có id)
//                .collect(Collectors.toList()); // thu thập các id vào một danh sách updatedVariantIds
//        for (ProductVariant existingVariant : existingVariants) {
//            if (updatedVariantIds.contains(existingVariant.getId())) {
//                productVariantRepository.delete(existingVariant);
//            }
//        }
//
//        // Thêm hoặc cập nhật các biến thể
//        for (ProductVariant updatedVariant : updatedVariants) {
//            if (updatedVariant.getId() == null) {
//                // Thêm biến thể mới
//                updatedVariant.setProduct(product);
//                productVariantRepository.save(updatedVariant);
//            } else {
//                // Cập nhật biến thể đã tồn tại
//                ProductVariant existingVariant = productVariantRepository.findById(updatedVariant.getId())
//                        .orElseThrow(() -> new RuntimeException("Variant not found: " + updatedVariant.getId()));
//                existingVariant.setSku(updatedVariant.getSku());
//                existingVariant.setStorage(updatedVariant.getStorage());
//                existingVariant.setDiscount(updatedVariant.getDiscount());
//                existingVariant.setPrice(updatedVariant.getPrice());
//                productVariantRepository.save(existingVariant);
//            }
//        }
//    }
    public void updateVariantsForProduct(Integer productId, List<ProductVariant> updatedVariants) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        // Xóa tất cả biến thể hiện tại của sản phẩm
        productVariantRepository.deleteByProductId(productId);

        for (ProductVariant updatedVariant : updatedVariants) {
            updatedVariant.setProduct(product); //gán sản phẩm cho biến thể
            productVariantRepository.save(updatedVariant);
        }
    }


    public void deleteByProductId(Integer productId) {
        productVariantRepository.deleteByProductId(productId);
    }

    public ProductVariant getFirstVariantByProductId(Integer productId) {
        List<ProductVariant> variants =
                productVariantRepository.findByProductId(productId);
        return variants.isEmpty() ? null : variants.get(0); // Trả về biến thể đầu tiên nếu có
    }

}
