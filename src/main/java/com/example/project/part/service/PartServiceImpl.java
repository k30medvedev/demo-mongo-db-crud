package com.example.project.part.service;

import com.example.project.part.dto.PartRequestDto;
import com.example.project.part.dto.PartResponseDto;
import com.example.project.part.dto.UpdateRequestDto;
import com.example.project.part.entity.Part;
import com.example.project.part.mapper.PartMapper;
import com.example.project.part.repository.PartRepository;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static com.example.project.part.service.QueryHelper.createQueryByFilters;
import static com.example.project.part.service.QueryHelper.createUpdateByFilters;

@Slf4j
@Service
public class PartServiceImpl implements PartService {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    private final PartMapper mapper;
    private final MongoTemplate mongoTemplate;
    private final PartRepository partRepository;
    private final MongoClient mongoClient;

    public PartServiceImpl(PartRepository partRepository, MongoTemplate mongoTemplate, PartMapper mapper, MongoClient mongoClient) {
        this.partRepository = partRepository;
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
        this.mongoClient = mongoClient;
    }

    @Override
    public PartResponseDto savePart(PartRequestDto partRequestDto) {
        log.info("Saving part");
        try (ClientSession clientSession = mongoClient.startSession()) {
            return clientSession.withTransaction(() -> {
                Part part = mapper.toPart(partRequestDto);
                Part savedPart = partRepository.save(part);
                return mapper.toPartResponseDto(savedPart);
            }, txnOptions);
        }
    }

    @Override
    public List<PartResponseDto> saveAll(List<PartRequestDto> partRequestDtos) {
        log.info("Saving all parts");
        try (ClientSession clientSession = mongoClient.startSession()) {
            return clientSession.withTransaction(() -> {
                List<Part> parts = mapper.partToList(partRequestDtos);
                partRepository.saveAll(parts);
                return mapper.toPartResponseDtoList(parts);
            }, txnOptions);
        }
    }

    @Override
    public List<PartResponseDto> findAllByIds(List<String> ids) {
        log.info("Finding parts by list of IDs");
        List<Part> parts = partRepository.findAllById(ids);
        return mapper.toPartResponseDtoList(parts);
    }

    @Override
    public PartResponseDto findById(String id) {
        log.info("Finding part by ID: [{}]", id);
        Part part = findOneOrThrowException(id);
        return mapper.toPartResponseDto(part);
    }

    @Override
    public List<PartResponseDto> getAllParts() {
        log.info("Getting all parts");
        List<Part> parts = partRepository.findAll();
        return mapper.toPartResponseDtoList(parts);
    }

    @Override
    public List<PartResponseDto> searchPartsByFilters(@NotNull Map<String, String> filters) {
        log.info("Searching parts by filters");
        Query query = createQueryByFilters(filters);
        List<Part> parts = mongoTemplate.find(query, Part.class);
        return mapper.toPartResponseDtoList(parts);
    }

    @Override
    public void decreaseStock(String id, int quantity) {
        log.info("Decreasing stock for part ID: [{}] by quantity: [{}]", id, quantity);
        try (ClientSession clientSession = mongoClient.startSession()) {
            clientSession.withTransaction(() -> {
                findOneOrThrowException(id);
                mongoTemplate.updateFirst(
                        Query.query(Criteria.where("_id").is(id)),
                        new Update().inc("stock", -quantity),
                        Part.class
                );
                return null;
            }, txnOptions);
        }
    }

    @Override
    public void updatePartsByFilter(@NotNull List<UpdateRequestDto> updateRequests) {
        log.info("Updating parts by filter");
        try (ClientSession clientSession = mongoClient.startSession()) {
            clientSession.withTransaction(() -> {
                BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Part.class);
                updateRequests.forEach(updateRequest -> {
                    String id = updateRequest.uuid();
                    findOneOrThrowException(id);
                    Query query = Query.query(Criteria.where("id").is(id));
                    Update update = createUpdateByFilters(updateRequest.values());
                    bulkOperations.updateOne(query, update);
                });
                bulkOperations.execute();
                return null;
            }, txnOptions);
        }
    }

    @Override
    public Long getCount() {
        log.info("Getting parts count");
        return partRepository.count();
    }

    @Override
    public void deleteParts(List<String> ids) {
        log.info("Deleting parts by IDs");
        try (ClientSession clientSession = mongoClient.startSession()) {
            clientSession.withTransaction(() -> {
                partRepository.deleteAllById(ids);
                return null;
            }, txnOptions);
        }
    }

    private Part findOneOrThrowException(String id) {
        return partRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Part not found with ID: " + id));
    }
}
