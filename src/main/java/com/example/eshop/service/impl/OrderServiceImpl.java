package com.example.eshop.service.impl;

import com.example.eshop.dto.OrderDTO;
import com.example.eshop.dto.OrderDetailDTO;
import com.example.eshop.model.Order;
import com.example.eshop.model.OrderDetail;
import com.example.eshop.repository.OrderDetailRepository;
import com.example.eshop.repository.OrderRepository;
import com.example.eshop.repository.ProductRepository;
import com.example.eshop.service.OrderService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* Created by nghiatdh on 5/18/21 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  OrderDetailRepository orderDetailRepository;

  @Autowired
  ProductRepository productRepository;

  @Override
  public long addOrder(OrderDTO orderDTO) {
    try {
      if (checkExistAndProductQuantity(orderDTO.getOrderDetailList())) {
        Order order = Order.builder()
            .orderUserName(orderDTO.getFullName())
            .email(orderDTO.getEmail())
            .phoneNumber(orderDTO.getPhoneNumber())
            .userAddress(orderDTO.getAddress())
            .build();

        long orderID = orderRepository.save(order).getId();

        for (OrderDetailDTO orderDetailDTO : orderDTO.getOrderDetailList()
        ) {
          OrderDetail orderDetail = OrderDetail.builder()
              .orderID(orderID)
              .quantity(orderDetailDTO.getQuantity())
              .productID(orderDetailDTO.getProductID())
              .build();
          orderDetailRepository.save(orderDetail);
        }
        return orderID;
      } else {
        return -1;
      }
    } catch (Exception e) {
      log.error("Exception {}", e);
      return -1;
    }
  }

  private boolean checkExistAndProductQuantity(List<OrderDetailDTO> orderDetailDTOList) {
    try {
      for (OrderDetailDTO orderDetailDTO : orderDetailDTOList
      ) {
        int currentQuantity = productRepository
            .getQuantityByProductID(orderDetailDTO.getProductID());
        if (orderDetailDTO.getQuantity() > currentQuantity) {
          return false;
        } else {
          int newQuantity = currentQuantity - orderDetailDTO.getQuantity();

          productRepository.updateQuantityByProductID(newQuantity, orderDetailDTO.getProductID());

        }
      }
    } catch (Exception e) {
      log.error("Exception {}", e);
      return false;
    }
    return true;
  }
}
