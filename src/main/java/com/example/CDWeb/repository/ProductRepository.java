package com.example.CDWeb.repository;

import com.example.CDWeb.model.Product;
import com.example.CDWeb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product>  getProductById(Integer id);

    List<Product> findByNameContainingIgnoreCase(String key);

    @Query(value = "SELECT * FROM product WHERE category IS NOT NULL ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Product> findRandomProducts(@Param("limit") int limit);
    List<Product> findByCategoryIsNotNull();

}
