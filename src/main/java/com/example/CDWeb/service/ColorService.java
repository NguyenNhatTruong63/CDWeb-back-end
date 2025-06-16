package com.example.CDWeb.service;

import com.example.CDWeb.model.Color;

import java.util.List;

public interface ColorService {
    Color getColorById(Long id);
    List<Color> getColorsByNames(List<String> names);
}
