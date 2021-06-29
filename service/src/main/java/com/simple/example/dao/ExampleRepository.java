package com.simple.example.dao;


import com.simple.example.model.ExampleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExampleRepository extends JpaRepository<ExampleModel, Long> {
    public List<ExampleModel> findByName(String name);
}
