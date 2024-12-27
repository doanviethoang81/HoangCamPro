package com.project.shopHoangCamPro.dao;

import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Integer> {
}
