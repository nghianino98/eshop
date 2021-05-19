package com.example.eshop.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import com.example.eshop.dto.OrderDTO;
import com.example.eshop.dto.OrderDetailDTO;
import com.example.eshop.model.Order;
import com.example.eshop.model.OrderDetail;
import com.example.eshop.repository.OrderDetailRepository;
import com.example.eshop.repository.OrderRepository;
import com.example.eshop.repository.ProductRepository;
import com.example.eshop.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class OrderServiceImplTest {

  @InjectMocks
  OrderService orderService = new OrderServiceImpl();

  @Mock
  OrderRepository orderRepository;

  @Mock
  OrderDetailRepository orderDetailRepository;

  @Mock
  ProductRepository productRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void addOrderSuccessCase() {

    //GIVEN
    List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();

    orderDetailDTOs.add(OrderDetailDTO.builder()
        .productID(1)
        .quantity(2)
        .build());

    orderDetailDTOs.add(OrderDetailDTO.builder()
        .productID(2)
        .quantity(3)
        .build());

    OrderDTO orderDTO = OrderDTO.builder()
        .orderDetailList(orderDetailDTOs)
        .fullName("Ta Nghia")
        .address("Viet Nam")
        .email("nghiatdh@gmail.com")
        .phoneNumber("0827877888")
        .build();

    Order order = Order.builder()
        .id(1)
        .orderUserName("Ta Nghia")
        .userAddress("Viet Nam")
        .email("nghiatdh@gmail.com")
        .phoneNumber("0827877888")
        .build();

    int QUANTITY_PRODUCT_1 = 10;
    int QUANTITY_PRODUCT_2 = 8;

    //WHEN
    Mockito.when(orderRepository.save(any(Order.class)))
        .thenReturn(order);

    Mockito.when(productRepository.getQuantityByProductID(orderDetailDTOs.get(0).getProductID()))
        .thenReturn(QUANTITY_PRODUCT_1);

    Mockito.when(productRepository.getQuantityByProductID(orderDetailDTOs.get(1).getProductID()))
        .thenReturn(QUANTITY_PRODUCT_2);

    long orderIDActual = orderService.addOrder(orderDTO);

    //THEN
    Assert.assertEquals(1, orderIDActual);

  }

  @Test
  public void addOrderWhenValidateQuantityFailed() {

    //GIVEN
    List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();

    orderDetailDTOs.add(OrderDetailDTO.builder()
        .productID(1)
        .quantity(2)
        .build());

    orderDetailDTOs.add(OrderDetailDTO.builder()
        .productID(2)
        .quantity(3)
        .build());

    OrderDTO orderDTO = OrderDTO.builder()
        .orderDetailList(orderDetailDTOs)
        .fullName("Ta Nghia")
        .address("Viet Nam")
        .email("nghiatdh@gmail.com")
        .phoneNumber("0827877888")
        .build();

    Order order = Order.builder()
        .id(1)
        .orderUserName("Ta Nghia")
        .userAddress("Viet Nam")
        .email("nghiatdh@gmail.com")
        .phoneNumber("0827877888")
        .build();

    int QUANTITY_PRODUCT_1 = 1;
    int QUANTITY_PRODUCT_2 = 4;

    //WHEN
    Mockito.when(orderRepository.save(any(Order.class)))
        .thenReturn(order);

    Mockito.when(productRepository.getQuantityByProductID(orderDetailDTOs.get(0).getProductID()))
        .thenReturn(QUANTITY_PRODUCT_1);

    Mockito.when(productRepository.getQuantityByProductID(orderDetailDTOs.get(1).getProductID()))
        .thenReturn(QUANTITY_PRODUCT_2);

    long orderIDActual = orderService.addOrder(orderDTO);

    //THEN
    Assert.assertEquals(-1, orderIDActual);

  }

  @Test(expected = Exception.class)
  public void addOrderWhenUpdateQuantityException() {

    //GIVEN
    List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();

    orderDetailDTOs.add(OrderDetailDTO.builder()
        .productID(1)
        .quantity(2)
        .build());

    orderDetailDTOs.add(OrderDetailDTO.builder()
        .productID(2)
        .quantity(3)
        .build());

    OrderDTO orderDTO = OrderDTO.builder()
        .orderDetailList(orderDetailDTOs)
        .fullName("Ta Nghia")
        .address("Viet Nam")
        .email("nghiatdh@gmail.com")
        .phoneNumber("0827877888")
        .build();

    Order order = Order.builder()
        .id(1)
        .orderUserName("Ta Nghia")
        .userAddress("Viet Nam")
        .email("nghiatdh@gmail.com")
        .phoneNumber("0827877888")
        .build();

    int QUANTITY_PRODUCT_1 = 1;
    int QUANTITY_PRODUCT_2 = 4;

    //WHEN
    Mockito.when(orderRepository.save(any(Order.class)))
        .thenReturn(order);

    Mockito.when(productRepository.getQuantityByProductID(orderDetailDTOs.get(0).getProductID()))
        .thenReturn(QUANTITY_PRODUCT_1);

    Mockito.when(productRepository.getQuantityByProductID(orderDetailDTOs.get(1).getProductID()))
        .thenReturn(QUANTITY_PRODUCT_2);

//    Mockito.when(productRepository.updateQuantityByProductID(QUANTITY_PRODUCT_1 - orderDetailDTOs.get(0).getQuantity()
//        , orderDetailDTOs.get(0).getProductID()))
//        .thenThrow()

    doThrow(new Exception("Exception")).
        when(productRepository)
        .updateQuantityByProductID(QUANTITY_PRODUCT_1 - orderDetailDTOs.get(0).getQuantity(),
        orderDetailDTOs.get(0).getProductID());


    long orderIDActual = orderService.addOrder(orderDTO);

    //THEN
    Assert.assertEquals(-1, orderIDActual);

  }

  @Test(expected = Exception.class)
  public void addOrderWhenSaveOrderDetailException() {

    //GIVEN
    List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();

    orderDetailDTOs.add(OrderDetailDTO.builder()
        .productID(1)
        .quantity(2)
        .build());

    orderDetailDTOs.add(OrderDetailDTO.builder()
        .productID(2)
        .quantity(3)
        .build());

    OrderDTO orderDTO = OrderDTO.builder()
        .orderDetailList(orderDetailDTOs)
        .fullName("Ta Nghia")
        .address("Viet Nam")
        .email("nghiatdh@gmail.com")
        .phoneNumber("0827877888")
        .build();

    Order order = Order.builder()
        .id(1)
        .orderUserName("Ta Nghia")
        .userAddress("Viet Nam")
        .email("nghiatdh@gmail.com")
        .phoneNumber("0827877888")
        .build();

    int QUANTITY_PRODUCT_1 = 10;
    int QUANTITY_PRODUCT_2 = 8;

    //WHEN
    Mockito.when(orderRepository.save(any(Order.class)))
        .thenReturn(order);

    Mockito.when(productRepository.getQuantityByProductID(orderDetailDTOs.get(0).getProductID()))
        .thenReturn(QUANTITY_PRODUCT_1);

    Mockito.when(productRepository.getQuantityByProductID(orderDetailDTOs.get(1).getProductID()))
        .thenReturn(QUANTITY_PRODUCT_2);

    Mockito.when(orderDetailRepository.save(any(OrderDetail.class)))
        .thenThrow(new Exception());

    long orderIDActual = orderService.addOrder(orderDTO);

    //THEN
    Assert.assertEquals(-1, orderIDActual);

  }

}