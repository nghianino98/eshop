package com.example.eshop.dto;

/* Created by nghiatdh on 5/18/21 */

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDTO {

  private String fullName;
  private String phoneNumber;
  private String address;
  private String email;
  private List<OrderDetailDTO> orderDetailList;

}
