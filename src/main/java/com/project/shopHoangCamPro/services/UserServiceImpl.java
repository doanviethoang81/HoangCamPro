package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.dao.ProductDAO;
import com.project.shopHoangCamPro.dao.UserDAO;
import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.models.User;
import com.project.shopHoangCamPro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    private UserRepository userReponsitory;

    @Autowired
    UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User findByPhone(String phone) {
        return userReponsitory.findByPhone(phone);
    }

    @Override
    public List<User> findAllById(List<String> id) {
        return List.of();
    }

    @Override
    public List<User> findAll() {
        return (List<User>)userRepository.findAll();//16
    }

//    @Override
//    public List<User> getAll() {
//        return (List<User>)userDAO.getA
//    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(Integer.valueOf(id));//17
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);//18
    }

    @Override
    public User getByUserName(String name) {
        return userRepository.getUserByName(name);
    }

    @Override
    public Page<User> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo -1,5);
        return this.userRepository.findAll(pageable);
    }

    public boolean isPhoneExists(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public boolean isPhoneExistsEdit(String phone, Integer userId) {
        // Kiểm tra xem số điện thoại đã tồn tại hay chưa, ngoại trừ số của người dùng hiện tại
        return userRepository.existsByPhoneAndIdNot(phone, userId);
    }

    public User getUserByName(String name) {
        return userRepository.getUserByName(name);
    }

    public User getUserById(Integer id){
        return userRepository.getUserById(id);
    }

    public void updateRoleUser(Integer id, User updateRoleUser) {
        //user theo id
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User không tồn tại với ID: " + id));
        existingUser.setRole_id(updateRoleUser.getRole_id());
        userRepository.save(existingUser);
    }

    public void updateProfile(Integer id, User updateProfile) {
        //user theo id
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User không tồn tại với ID: " + id));
        existingUser.setName(updateProfile.getName());
        existingUser.setPhone(updateProfile.getPhone());
        existingUser.setAddress(updateProfile.getAddress());
        existingUser.setDateOfBirth(updateProfile.getDateOfBirth());
        if (updateProfile.getPassword() != null && !updateProfile.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updateProfile.getPassword()));
        }
        userRepository.save(existingUser);
    }

    public void lockUser(Integer id){
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User không tồn tại với id: "+ id));
        existingUser.setIsActive(false);
        userRepository.save(existingUser);
    }

    public void unblockUser(Integer id){
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User không tồn tại với id: "+ id));
        existingUser.setIsActive(true);
        userRepository.save(existingUser);
    }
}
