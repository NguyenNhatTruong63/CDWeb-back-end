package com.example.CDWeb.repository;

import com.example.CDWeb.model.Product;
import com.example.CDWeb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product>  getProductById(Integer id);

}
