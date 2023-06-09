package com.exercise.dank.repo;


import com.exercise.dank.model.domain.EducationRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EducationRecordRepository extends MongoRepository<EducationRecord, String> {
    List<EducationRecord>findAllByUserId(String id);
}
