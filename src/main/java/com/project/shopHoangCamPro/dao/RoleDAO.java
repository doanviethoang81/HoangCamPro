package com.project.shopHoangCamPro.dao;

import com.project.shopHoangCamPro.models.Role;
import com.project.shopHoangCamPro.models.User;
import org.springframework.data.repository.CrudRepository;

public interface RoleDAO extends CrudRepository<Role, Integer> {
}
