package com.example.project.part.repository;

import com.example.project.part.entity.Part;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartRepository extends MongoRepository<Part, String> {
}