package com.example.project.part.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Map;

public class QueryHelper {
    public static @NotNull Query createQueryByFilters(@NotNull Map<String, String> filters) {
        Query query = new Query();

        filters.forEach((field, value) -> {
            switch (field) {
                case "partName":
                    query.addCriteria(Criteria.where("partName").is(value));
                    break;
                case "partNumber":
                    query.addCriteria(Criteria.where("partNumber").is(value));
                    break;
                case "carMake":
                    query.addCriteria(Criteria.where("carMake").is(value));
                    break;
                case "carModel":
                    query.addCriteria(Criteria.where("carModel").is(value));
                    break;
                case "year":
                    query.addCriteria(Criteria.where("year").is(Integer.parseInt(value)));
                    break;
                case "price":
                    query.addCriteria(Criteria.where("price").is(Double.parseDouble(value)));
                    break;
                case "stock":
                    query.addCriteria(Criteria.where("stock").is(Integer.parseInt(value)));
                    break;
                case "description":
                    query.addCriteria(Criteria.where("description").is(value));
                    break;
                case "category":
                    query.addCriteria(Criteria.where("category").is(value));
                    break;
            }
        });
        return query;
    }

    public static @NotNull Update createUpdateByFilters(@NotNull Map<String, String> filters) {
        Update update = new Update();

        filters.forEach((field, value) -> {
            switch (field) {
                case "partName":
                    update.set("partName", value);
                    break;
                case "partNumber":
                    update.set("partNumber", value);
                    break;
                case "carMake":
                    update.set("carMake", value);
                    break;
                case "carModel":
                    update.set("carModel", value);
                    break;
                case "year":
                    update.set("year", Integer.parseInt(value));
                    break;
                case "price":
                    update.set("price", Double.parseDouble(value));
                    break;
                case "stock":
                    update.set("stock", Integer.parseInt(value));
                    break;
                case "description":
                    update.set("description", value);
                    break;
                case "category":
                    update.set("category", value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + field);
            }
        });
        return update;
    }
}
