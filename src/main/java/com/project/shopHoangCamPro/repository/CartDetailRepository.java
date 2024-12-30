package com.project.shopHoangCamPro.repository;

import com.project.shopHoangCamPro.models.Cart;
import com.project.shopHoangCamPro.models.CartDetail;
import com.project.shopHoangCamPro.models.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
//JpaRepository là một interface trong Spring Data JPA cung cấp các phương thức sẵn có để thao tác với cơ sở dữ liệu
public interface CartDetailRepository extends JpaRepository<CartDetail,Integer> {

    Optional<CartDetail> findByCartAndProductVariant(Cart cart, ProductVariant productVariant);

//    Optional<CartDetail> findByCartIdAndUser(Cart cart, Integer cartId );

    List<CartDetail> findByCartId(Integer cartId);


}
