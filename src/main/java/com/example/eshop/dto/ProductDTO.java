package com.example.eshop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/* Created by nghiatdh on 5/15/21 */
@Getter
@Setter
@Builder
public class ProductDTO {
  private long id;
  private String name;
  private long price;
  private String brand;
  private String color;
  private String image;
  private String description;
  private int quantity;
  private String exInfo;
}
