package com.example.eshop.repository;

/* Created by nghiatdh on 5/15/21 */

import com.example.eshop.model.Product;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

  @Query("select p.quantity from Product p where p.id = :id")
  int getQuantityByProductID (@Param("id") long id);

  @Modifying
  @Transactional
  @Query("update Product p set p.quantity = :quantity where p.id = :id")
  void updateQuantityByProductID (@Param("quantity") int quantity, @Param("id") long id);

}
