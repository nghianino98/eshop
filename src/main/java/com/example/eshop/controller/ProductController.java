package com.example.eshop.controller;

/* Created by nghiatdh on 5/15/21 */

import com.example.eshop.dto.ProductDTO;
import com.example.eshop.service.ProductService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "eshop/products")
@Slf4j
public class ProductController {

  @Autowired
  ProductService productService;

  @GetMapping(path = "", produces = "application/json")
  public @ResponseBody
  List<ProductDTO> getProducts(@RequestParam(defaultValue = "") String q,
      @RequestParam(defaultValue = "name,ASC") String sort,
      @RequestParam(defaultValue = "0,-1") String price,
      @RequestParam(defaultValue = "") String brand,
      @RequestParam(defaultValue = "") String color) {
    return productService.getProducts(q, sort, price, brand, color);
  }

  @Secured("ROLE_ADMIN")
  @PostMapping(path = "", consumes = "application/json", produces = "application/json")
  public @ResponseBody
  long addProduct(@RequestBody ProductDTO productDTO) {
    return productService.addProduct(productDTO);
  }

  @Secured("ROLE_ADMIN")
  @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
  public @ResponseBody
  long updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {
    return productService.updateProduct(Long.parseLong(id), productDTO);
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping(path = "/{id}", produces = "application/json")
  public @ResponseBody
  boolean deleteProduct(@PathVariable String id) {
    return productService.deleteProduct(Long.parseLong(id));
  }

  }
