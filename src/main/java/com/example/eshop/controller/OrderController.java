package com.example.eshop.controller;

/* Created by nghiatdh on 5/18/21 */

import com.example.eshop.dto.OrderDTO;
import com.example.eshop.dto.ProductDTO;
import com.example.eshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "eshop/orders")
@Slf4j
public class OrderController {

  @Autowired
  OrderService orderService;

  @PostMapping(path = "", consumes = "application/json", produces = "application/json")
  public @ResponseBody
  long addOrder(@RequestBody OrderDTO orderDTO) {
    return orderService.addOrder(orderDTO);
  }

}
