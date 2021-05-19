package com.example.eshop.service;

import com.example.eshop.dto.OrderDTO;
import org.springframework.stereotype.Service;

/* Created by nghiatdh on 5/18/21 */
@Service
public interface OrderService {

  long addOrder(OrderDTO orderDTO);

}
