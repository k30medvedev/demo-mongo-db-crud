package com.example.project.part.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@Document(collection = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String UUID;

    private String partName;
    private String partNumber;
    private String carMake;
    private String carModel;
    private int year;
    private double price;
    private int stock;
    private String description;
    private String category;

    public Part(String partName, String partNumber, String carMake, String carModel, int year, double price, int stock, String description, String category) {
        this.partName = partName;
        this.partNumber = partNumber;
        this.carMake = carMake;
        this.carModel = carModel;
        this.year = year;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return year == part.year && Double.compare(price, part.price) == 0 && stock == part.stock && Objects.equals(UUID, part.UUID) && Objects.equals(partName, part.partName) && Objects.equals(partNumber, part.partNumber) && Objects.equals(carMake, part.carMake) && Objects.equals(carModel, part.carModel) && Objects.equals(description, part.description) && Objects.equals(category, part.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UUID, partName, partNumber, carMake, carModel, year, price, stock, description, category);
    }
}
