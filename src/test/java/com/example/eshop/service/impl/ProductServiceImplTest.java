package com.example.eshop.service.impl;

import static org.mockito.ArgumentMatchers.any;

import com.example.eshop.dto.ProductDTO;
import com.example.eshop.model.Product;
import com.example.eshop.repository.ProductRepository;
import com.example.eshop.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ProductServiceImplTest {

  @InjectMocks
  ProductService productService = new ProductServiceImpl();

  @Mock
  ProductRepository productRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void addProduct() {
    //GIVEN
    ProductDTO productDTO = ProductDTO.builder()
        .id(1)
        .brand("Samsung")
        .color("Black")
        .description("Description")
        .exInfo("{}")
        .image("image")
        .name("Tivi")
        .price(10000000)
        .quantity(100)
        .build();
    Product product = Product.builder()
        .id(1)
        .brand("Samsung")
        .color("Black")
        .description("Description")
        .exInfo("{}")
        .image("image")
        .name("Tivi")
        .price(10000000)
        .quantity(100)
        .build();

    //WHEN
    Mockito.when(productRepository.save(any(Product.class)))
        .thenReturn(product);

    long actualResult = productService.addProduct(productDTO);

    //THEN
    Assert.assertEquals(1, actualResult);
  }

  @Test
  public void updateProductWhenProductExist() {

    //GIVE
    ProductDTO productDTO = ProductDTO.builder()
        .id(1)
        .brand("Samsung")
        .color("Black")
        .description("Description")
        .exInfo("{}")
        .image("image")
        .name("Tivi")
        .price(10000000)
        .quantity(100)
        .build();
    Product product = Product.builder()
        .id(1)
        .brand("Samsung")
        .color("Black")
        .description("Description")
        .exInfo("{}")
        .image("image")
        .name("Tivi")
        .price(10000000)
        .quantity(100)
        .build();

    //WHEN

    Mockito.when(productRepository.existsById(productDTO.getId()))
        .thenReturn(true);

    Mockito.when(productRepository.save(any(Product.class)))
        .thenReturn(product);

    long actualResult = productService.updateProduct(1, productDTO);

    //THEN
    Assert.assertEquals(1, actualResult);
  }

  @Test
  public void updateProductWhenProductNotExist() {

    //GIVE
    ProductDTO productDTO = ProductDTO.builder()
        .id(1)
        .brand("Samsung")
        .color("Black")
        .description("Description")
        .exInfo("{}")
        .image("image")
        .name("Tivi")
        .price(10000000)
        .quantity(100)
        .build();
    Product product = Product.builder()
        .id(1)
        .brand("Samsung")
        .color("Black")
        .description("Description")
        .exInfo("{}")
        .image("image")
        .name("Tivi")
        .price(10000000)
        .quantity(100)
        .build();

    //WHEN

    Mockito.when(productRepository.existsById(productDTO.getId()))
        .thenReturn(false);

    long actualProductID = productService.updateProduct(1, productDTO);

    //THEN
    Assert.assertEquals(actualProductID, 0);
  }

  @Test
  public void deleteProductWhenProductExist() {

    //GIVEN
    long id = 1;

    //WHEN

    Mockito.when(productRepository.existsById(id))
        .thenReturn(true);

    boolean actualResult = productService.deleteProduct(id);

    //THEN

    Assert.assertEquals(true, actualResult);

  }

  @Test
  public void deleteProductWhenProductNotExist() {

    //GIVEN
    long id = 1;

    //WHEN

    Mockito.when(productRepository.existsById(id))
        .thenReturn(false);

    boolean actualResult = productService.deleteProduct(id);

    //THEN

    Assert.assertEquals(false, actualResult);

  }

  @Test
  public void getProducts() {

    //GIVEN
    String name = "Tivi";
    String sort = "name,ASC";
    String price = "0,100000";
    String brand = "Samsung";
    String color = "Black";

    List<Product> productList = new ArrayList<>();

    productList.add(Product.builder()
        .id(1)
        .name("Tivi-1")
        .price(10000)
        .image("image")
        .brand("Samsung")
        .description("description")
        .color("Black")
        .quantity(10)
        .exInfo("{}")
        .build()
    );

    productList.add(Product.builder()
        .id(2)
        .name("Tivi-2")
        .price(20000)
        .image("image")
        .brand("Samsung")
        .description("description")
        .color("Black")
        .quantity(10)
        .exInfo("{}")
        .build()
    );

    //WHEN

    Mockito.when(productRepository.findProductsByNameAndPriceAndBrandAndColor(
        name, 0, 100000, brand, color, "name", "ASC")
    ).thenReturn(productList);

    List<ProductDTO> actualProductDTOs = productService.getProducts(
        name, sort, price, brand, color);

    //THEN

    Assert.assertEquals(1, actualProductDTOs.get(0).getId());
    Assert.assertEquals(10000, actualProductDTOs.get(0).getPrice());
    Assert.assertEquals(20000, actualProductDTOs.get(1).getPrice());


  }
}