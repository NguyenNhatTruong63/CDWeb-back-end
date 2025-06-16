package com.example.CDWeb.serviceImpl;

import com.example.CDWeb.model.Color;
import com.example.CDWeb.repository.ColorRepository;
import com.example.CDWeb.repository.SizeRepository;
import com.example.CDWeb.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl  implements ColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public Color getColorById(Long id) {
        return colorRepository.getColorById(id);
    }

    @Override
    public List<Color> getColorsByNames(List<String> names) {
        List<Color> colors = colorRepository.findByNameIn(names);
        if (colors.size() != names.size()) {
            throw new IllegalArgumentException("Một hoặc nhiều màu không hợp lệ");
        }
        return colors;
    }

}
