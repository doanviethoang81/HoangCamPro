package com.project.shopHoangCamPro.services;

import com.project.shopHoangCamPro.dao.OrderDetailDAO;
import com.project.shopHoangCamPro.models.OrderDetail;
import com.project.shopHoangCamPro.models.ProductVariant;
import com.project.shopHoangCamPro.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService{

    @Autowired
    OrderDetailDAO orderDetailDAO;

    private  final OrderDetailRepository orderDetailRepository;
    @Override
    public List<OrderDetail> getAll() {
        return this.orderDetailRepository.findAll();

    }

    @Override
    public void delete(OrderDetail order) {
        orderDetailRepository.delete(order);//1
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<OrderDetail> findAllById(List<String> id) {
        return List.of();
    }

    @Override
    public List<OrderDetail> findAll() {
        return (List<OrderDetail>)orderDetailRepository.findAll(); //2
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public Optional<OrderDetail> findById(Integer id) {
        return orderDetailRepository.findById(id);//3
    }

    @Override
    public OrderDetail save(OrderDetail order) {
        return orderDetailRepository.save(order);
    }

    @Override
    public List<OrderDetail> getOrderDetailByOrderId(int id) throws Exception{
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(id);
        if (orderDetails.isEmpty()) {
            throw new Exception("Không tìm thấy id order = " + id);
        }
        return orderDetails;
    }

    public List<OrderDetail> findOrderDetailByOrderId(Integer id){
        List<OrderDetail> orderDetailList =orderDetailRepository.findByOrderId(id);
        return orderDetailList;
    }
}
