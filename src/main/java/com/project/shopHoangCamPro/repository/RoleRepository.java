package com.project.shopHoangCamPro.repository;

import com.project.shopHoangCamPro.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
        Role findByName(String name);
}
