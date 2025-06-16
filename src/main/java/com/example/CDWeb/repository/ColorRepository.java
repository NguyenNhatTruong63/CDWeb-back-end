package com.example.CDWeb.repository;

import com.example.CDWeb.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    Color getColorById(long id);
    List<Color> findByNameIn(List<String> names);
}
