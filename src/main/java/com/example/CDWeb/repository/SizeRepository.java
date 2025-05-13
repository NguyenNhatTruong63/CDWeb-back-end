package com.example.CDWeb.repository;

import com.example.CDWeb.model.Product;
import com.example.CDWeb.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Size getSizeById(Long id);
}
