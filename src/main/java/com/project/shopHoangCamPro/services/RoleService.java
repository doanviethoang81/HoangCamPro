package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.models.Role;
import com.project.shopHoangCamPro.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return (List<Role>)roleRepository.findAll();//15
    }


}
