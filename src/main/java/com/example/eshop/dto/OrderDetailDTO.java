package com.example.eshop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/* Created by nghiatdh on 5/18/21 */
@Getter
@Setter
@Builder
public class OrderDetailDTO {

  private long productID;
  private int quantity;

}
