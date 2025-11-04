package com.eCommerce.main.repository;

import com.eCommerce.main.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);
    List<Product> findByNameContainingIgnoreCase(String name);
    @Query("SELECT p FROM Product p WHERE p.quantity < :threshold")
    List<Product> findLowStockProducts(@Param("threshold") Long threshold);
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    boolean existsByNameIgnoreCase(String name);
}
