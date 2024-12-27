package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.models.CartDetail;

import java.util.List;
import java.util.Optional;

public interface ICartDetailService {

    void delete(CartDetail cartDetail);

    void deleteById(Integer id);

    long count();

    List<CartDetail> findAllById(List<Integer> id);

    List<CartDetail> findAll();

    boolean existsById(String id);

    Optional<CartDetail> findById(Integer id);

    CartDetail save(CartDetail cartDetail);
}
