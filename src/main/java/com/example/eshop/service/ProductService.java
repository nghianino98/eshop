package com.example.eshop.service;

import com.example.eshop.dto.ProductDTO;
import com.example.eshop.model.Product;
import java.awt.print.Pageable;
import java.util.List;

public interface ProductService {

  long addProduct(ProductDTO productDTO);

  long updateProduct(long id, ProductDTO productDTO);

  boolean deleteProduct(long id);

  List<ProductDTO> getProducts(String name, String sort, String price, String brand, String color);

}
