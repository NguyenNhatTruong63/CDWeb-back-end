package com.example.CDWeb.serviceImpl;

import com.example.CDWeb.model.Size;
import com.example.CDWeb.repository.CartRepository;
import com.example.CDWeb.repository.SizeRepository;
import com.example.CDWeb.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public Size getSizeById(Long id) {
        return sizeRepository.getSizeById(id);
    }
}
