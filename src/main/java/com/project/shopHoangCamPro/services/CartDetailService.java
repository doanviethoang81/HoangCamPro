package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.models.Cart;
import com.project.shopHoangCamPro.models.CartDetail;
import com.project.shopHoangCamPro.models.ProductVariant;
import com.project.shopHoangCamPro.repository.CartDetailRepository;
import com.project.shopHoangCamPro.repository.CartRepository;
import com.project.shopHoangCamPro.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor // tu tao ham controuctor tu tuong khi co thuoc tinh final
public class CartDetailService implements ICartDetailService {

    private final CartDetailRepository cartDetailRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartRepository cartRepository;
    @Override
    public void delete(CartDetail cartDetail) {
        cartDetailRepository.delete(cartDetail);
    }

    @Override
    public void deleteById(Integer id) {
        cartDetailRepository.deleteById(id);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<CartDetail> findAllById(List<Integer> id) {
        return (List<CartDetail>)cartDetailRepository.findAllById(id);
    }

    @Override
    public List<CartDetail> findAll() {
        return (List<CartDetail>)cartDetailRepository.findAll();
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public Optional<CartDetail> findById(Integer id) {
        return cartDetailRepository.findById(id);
    }

    @Override
    public CartDetail save(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    public CartDetail findByCartAndProductVariant(Cart cart, Integer productVariantId) {
        Optional<ProductVariant> productVariant = productVariantRepository.findById(productVariantId);
        if (productVariant.isPresent()) {
            return cartDetailRepository.findByCartAndProductVariant(cart, productVariant.get())
                    .orElse(null); // Trả về CartDetail nếu tìm thấy, null nếu không có
        }
        return null; // Nếu không tìm thấy ProductVariant
    }

//    public CartDetail findByCartIdAndUser(Cart cart, Integer cartId){
//        Optional<Cart> cart1 = cartRepository.findById(cartId);
//        if(cart1.isPresent()) {
//            return cartDetailRepository.findByCartIdAndUser(cart, cartId)
//                    .orElse(null);
//        }
//        return null;
//    }
}
