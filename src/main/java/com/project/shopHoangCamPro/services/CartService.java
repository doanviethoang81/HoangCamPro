package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.models.Cart;
import com.project.shopHoangCamPro.models.CartDetail;
import com.project.shopHoangCamPro.models.CustomUserDetail;
import com.project.shopHoangCamPro.models.Order;
import com.project.shopHoangCamPro.repository.CartDetailRepository;
import com.project.shopHoangCamPro.repository.CartRepository;
import com.project.shopHoangCamPro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // tu tao ham controuctor tu tuong khi co thuoc tinh final
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public void deleteById(Integer id) {
        cartRepository.deleteById(id);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<Cart> findAllById(List<Integer> id) {
        return (List<Cart>)cartRepository.findAllById(id);
    }

    @Override
    public List<Cart> findAll() {
        return (List<Cart>)cartRepository.findAll();
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public Optional<Cart> findById(Integer id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public boolean isUserIdExists(Integer id) {
        return cartRepository.existsByUserId(id);
    }

    public Cart findCartByUserId(Integer userId) {
        return cartRepository.findByUserId(userId);
    }

    // Lấy danh sách sản phẩm trong giỏ hàng theo cartId
    public List<CartDetail> findCartDetailsByCartId(Integer cartId) {
        return cartDetailRepository.findByCartId(cartId);
    }
}
