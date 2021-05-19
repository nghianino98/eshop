package com.example.eshop.repository;

import com.example.eshop.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/* Created by nghiatdh on 5/17/21 */

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

  @Autowired
  EntityManager entityManager;

  @Override
  public List<Product> findProductsByNameAndPriceAndBrandAndColor(String name, long minPrice,
      long maxPrice,
      String brand, String color, String sortName, String sortOrder) {

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

    Root<Product> product = criteriaQuery.from(Product.class);
    List<Predicate> predicates = new ArrayList<>();

    if (!StringUtils.isEmpty(name)) {
      predicates.add(criteriaBuilder.like(product.get("name"),'%'+name+'%'));
    }

    predicates.add(criteriaBuilder.between(product.get("price"), minPrice, maxPrice));

    if (!StringUtils.isEmpty(brand)) {
      predicates.add(criteriaBuilder.equal(product.get("brand"), brand));
    }

    if (!StringUtils.isEmpty(color)) {
      predicates.add(criteriaBuilder.equal(product.get("color"), color));
    }

    List<Order> orderList = new ArrayList();

    if (("ASC").equals(sortOrder)){
      orderList.add(criteriaBuilder.asc(product.get(sortName)));
    } else if (("DESC").equals(sortOrder)) {
      orderList.add(criteriaBuilder.desc(product.get(sortName)));
    }

//    if (("-").equals(sortPrice)) {
//      orderList.add(criteriaBuilder.desc(product.get("price")));
//    } else if (("+").equals(sortPrice)) {
//      orderList.add(criteriaBuilder.asc(product.get("price")));
//    }
//    if (("-").equals(sortName)) {
//      orderList.add(criteriaBuilder.desc(product.get("name")));
//    } else if (("+").equals(sortName)) {
//      orderList.add(criteriaBuilder.asc(product.get("name")));
//    }
//    if (("-").equals(sortBrand)) {
//      orderList.add(criteriaBuilder.desc(product.get("brand")));
//    } else if (("+").equals(sortBrand)) {
//      orderList.add(criteriaBuilder.asc(product.get("brand")));
//    }
//    if (("-").equals(sortColor)) {
//      orderList.add(criteriaBuilder.desc(product.get("color")));
//    } else if (("+").equals(sortColor)) {
//      orderList.add(criteriaBuilder.asc(product.get("color")));
//    }

    criteriaQuery.where(predicates.toArray(new Predicate[0]));

    return entityManager.createQuery(criteriaQuery.orderBy(orderList)).
        getResultList();
  }
}
