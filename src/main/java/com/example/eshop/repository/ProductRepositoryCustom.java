package com.example.eshop.repository;

import com.example.eshop.model.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryCustom {

  List<Product> findProductsByNameAndPriceAndBrandAndColor(String name, long minPrice,
      long maxPrice,
      String brand, String color, String sortName, String sortOrder);

}
