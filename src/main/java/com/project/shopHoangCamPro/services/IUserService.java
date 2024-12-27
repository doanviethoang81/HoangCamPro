package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User findByPhone(String phone);

    List<User> findAllById(List<String> id);

    List<User> findAll();

//    List<User> getAll();

    boolean existsById(String id);

    Optional<User> findById(String id);


    User save(User user);

    User getByUserName(String name);

}
