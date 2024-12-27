package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.dao.OrderDAO;
import com.project.shopHoangCamPro.dao.RoleDAO;
import com.project.shopHoangCamPro.models.Cart;
import com.project.shopHoangCamPro.models.Order;
import com.project.shopHoangCamPro.models.Product;
import com.project.shopHoangCamPro.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    @Autowired
    OrderDAO orderDAO;

    private final OrderRepository orderRepository;

    @Override
    public List<Order> getAll() {
        return this.orderRepository.findAll();
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);//4
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<Order> findAllById(List<String> id) {
        return List.of();
    }

    @Override
    public List<Order> findAll() {
        return (List<Order>)orderRepository.findAll();//5
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);//6
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);//7
    }

    public Order getById(Integer id){
        return  orderRepository.getById(id);
    }

    public boolean isUserIdExists(Integer id) {
        return orderRepository.existsByUserId(id);
    }

    public List<Order> findOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }
}
