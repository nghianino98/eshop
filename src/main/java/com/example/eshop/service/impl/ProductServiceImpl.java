package com.example.eshop.service.impl;

import com.example.eshop.dto.ProductDTO;
import com.example.eshop.model.Product;
import com.example.eshop.repository.ProductRepository;
import com.example.eshop.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* Created by nghiatdh on 5/15/21 */

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

  @Autowired
  ProductRepository productRepository;

  @Override
  public long addProduct(ProductDTO productDTO) {
    Product product = productRepository.save(getProductFromProductDTO(productDTO));
    return product.getId();
  }

  @Override
  public long updateProduct(long id, ProductDTO productDTO) {
    boolean isExist;
    try {
      isExist = productRepository.existsById(id);
    } catch (Exception e) {
      isExist = false;
      log.info("ProductServiceImpl exception {}", e);
    }
    if (true == isExist) {
      Product productResult = productRepository.save(getProductFromProductDTO(productDTO));
      return productResult.getId();
    } else {
      log.info("Product with ID {} not found", id);
      return 0;
    }
  }

  @Override
  public boolean deleteProduct(long id) {
    boolean isExist;
    try {
      isExist = productRepository.existsById(id);
    } catch (Exception e) {
      isExist = false;
      log.info("ProductServiceImpl exception {}", e);
    }

    if (true == isExist) {
      try {
        productRepository.deleteById(id);
      } catch (Exception e) {
        log.info("Delete Product with ID {} exception {}", id, e);
        return false;
      }
      return true;
    } else {
      log.info("Product with ID {} not found", id);
      return false;
    }
  }

  @Override
  public List<ProductDTO> getProducts(String name, String sort, String price, String brand,
      String color) {
    List<ProductDTO> productDTOList = new ArrayList<>();

    String[] sortSample = sort.split(",");
    String sortName = sortSample[0];
    String sortOrder = sortSample[1];

    String[] priceSample = price.split(",");
    long minPrice = Long.parseLong(priceSample[0]);
    long maxPrice = Long.parseLong(priceSample[1]);
    if (-1 == maxPrice) {
      maxPrice = Integer.MAX_VALUE;
    }

    List<Product> productList = productRepository.findProductsByNameAndPriceAndBrandAndColor(
        name, minPrice, maxPrice, brand, color, sortName, sortOrder);

    for (Product product : productList
    ) {
      productDTOList.add(ProductDTO.builder()
          .image(product.getImage())
          .name(product.getName())
          .price(product.getPrice())
          .brand(product.getBrand())
          .color(product.getColor())
          .description(product.getDescription())
          .exInfo(product.getExInfo())
          .id(product.getId())
          .quantity(product.getQuantity())
          .build()
      );
    }
    return productDTOList;
  }

  private Product getProductFromProductDTO(ProductDTO productDTO) {
    Product product = Product.builder()
        .id(productDTO.getId())
        .brand(productDTO.getBrand())
        .color(productDTO.getColor())
        .description(productDTO.getDescription())
        .exInfo(productDTO.getExInfo())
        .image(productDTO.getImage())
        .name(productDTO.getName())
        .price(productDTO.getPrice())
        .quantity(productDTO.getQuantity())
        .build();
    return product;
  }

}
