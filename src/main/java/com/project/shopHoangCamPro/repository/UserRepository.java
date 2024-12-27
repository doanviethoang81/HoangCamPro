package com.project.shopHoangCamPro.repository;

import com.project.shopHoangCamPro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByPhone(String phone);

    boolean existsByPhone(String phone);

    User getUserByName(String name);

    User getUserById(Integer id);
    // Kiểm tra số điện thoại đã tồn tại ngoại trừ một người dùng cụ thể
    boolean existsByPhoneAndIdNot(String phone, Integer id);

}
