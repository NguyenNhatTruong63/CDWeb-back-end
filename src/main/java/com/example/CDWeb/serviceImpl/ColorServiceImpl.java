package com.example.CDWeb.serviceImpl;

import com.example.CDWeb.model.Color;
import com.example.CDWeb.repository.ColorRepository;
import com.example.CDWeb.repository.SizeRepository;
import com.example.CDWeb.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl  implements ColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public Color getColorById(Long id) {
        return colorRepository.getColorById(id);
    }
}
