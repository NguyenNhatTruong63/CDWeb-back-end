package com.example.CDWeb.service;


import com.example.CDWeb.model.Size;

import java.util.List;

public interface SizeService {
    Size getSizeById(Long id);
    List<Size> getSizesByValues(List<Integer> values);

}
