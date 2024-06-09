package com.example.project.user.service;

import com.example.project.user.dto.PartRequestDto;
import com.example.project.user.dto.PartResponseDto;
import com.example.project.user.dto.UpdateRequestDto;
import com.example.project.user.entity.Part;
import com.example.project.user.mapper.PartMapper;
import com.example.project.user.repository.PartRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static com.example.project.user.service.QueryHelper.createQueryByFilters;
import static com.example.project.user.service.QueryHelper.createUpdateByFilters;

@Slf4j
@Service
public class PartServiceImpl implements PartService {

    private final PartMapper mapper;
    private final MongoTemplate mongoTemplate;
    private final PartRepository partRepository;


    public PartServiceImpl(PartRepository partRepository, MongoTemplate mongoTemplate, PartMapper mapper) {
        this.partRepository = partRepository;
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
    }

    @Override
    public PartResponseDto savePart(PartRequestDto partRequestDto) {
        log.info("Part is saving");
        Part part = mapper.toPart(partRequestDto);
        Part result = partRepository.save(part);
        return mapper.toPartResponseDto(result);
    }

    @Override
    public List<PartResponseDto> saveAll(List<PartRequestDto> parts) {
        log.info("Save all parts");
        List<Part> listToSave = mapper.partToList(parts);
        List<Part> savedList = partRepository.saveAll(listToSave);
        return mapper.toPartResponseDtoList(savedList);
    }

    @Override
    public List<PartResponseDto> findAllByIds(List<String> Ids) {
        log.info("Find all parts by list of Id");
        return mapper.toPartResponseDtos(partRepository.findAllById(Ids));
    }

    @Override
    public PartResponseDto findById(String id) {
        log.info(String.format("Find part by id: [%s] ", id));
        Part part = findOneOrThrowException(id);
        return mapper.toPartResponseDto(part);
    }

    @Override
    public List<PartResponseDto> getAllParts() {
        log.info("All parts requested");
        return mapper.toPartResponseDtoList(partRepository.findAll());
    }

    @Override
    public List<PartResponseDto> searchPartsByFilters(@NotNull Map<String, String> filters) {
        Query query = createQueryByFilters(filters);
        return mapper.toPartResponseDtoList(mongoTemplate.find(query, Part.class));
    }

    @Override
    @Transactional
    public void decreaseStock(String id, int quantity) {
        findOneOrThrowException(id);

        mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").is(id)),
                new Update().inc("stock", -quantity),
                Part.class
        );

    }

    @Override
    @Transactional
    public void updatePartsByFilter(@NotNull List<UpdateRequestDto> updateRequest) {
        log.info("Update parts by filter");
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Part.class);

        updateRequest.forEach(element -> {
                    String id = element.id();
                    findOneOrThrowException(id);
                    Query query = new Query();
                    query.addCriteria(Criteria.where("id").is(id));
                    Update update = createUpdateByFilters(element.values());
                    bulkOperations.updateOne(query, update);

                }
        );
        bulkOperations.execute();

    }

    @Override
    public Long getCount() {
        return partRepository.count();
    }

    @Override
    @Transactional
    public void deleteParts(List<String> ids) {
        log.info("delete parts by filter");
        partRepository.deleteAllById(ids);
    }


    private Part findOneOrThrowException(String id) {
        return partRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Part not found with id: " + id));
    }
}
