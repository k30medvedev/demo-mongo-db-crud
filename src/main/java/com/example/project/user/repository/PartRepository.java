package com.example.project.user.repository;

import com.example.project.user.entity.Part;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PartRepository extends MongoRepository<Part, String> {
}