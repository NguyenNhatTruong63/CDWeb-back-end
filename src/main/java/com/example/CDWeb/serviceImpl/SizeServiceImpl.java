package com.example.CDWeb.serviceImpl;

import com.example.CDWeb.model.Size;
import com.example.CDWeb.repository.CartRepository;
import com.example.CDWeb.repository.SizeRepository;
import com.example.CDWeb.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public Size getSizeById(Long id) {
        return sizeRepository.getSizeById(id);
    }

    @Override
    public List<Size> getSizesByValues(List<Integer> values) {
        List<Size> sizes = sizeRepository.findByValueIn(values);
        if (sizes.size() != values.size()) {
            throw new IllegalArgumentException("Một hoặc nhiều size không hợp lệ");
        }
        return sizes;
    }
}
