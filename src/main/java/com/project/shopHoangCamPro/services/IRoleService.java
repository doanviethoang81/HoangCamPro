package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.models.Role;
import com.project.shopHoangCamPro.models.User;

import java.util.List;

public interface IRoleService {
    List<Role> findAll();
}
