package com.example.CDWeb.repository;

import com.example.CDWeb.model.Category;
import com.example.CDWeb.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> getCategoryById(Integer id);

}
