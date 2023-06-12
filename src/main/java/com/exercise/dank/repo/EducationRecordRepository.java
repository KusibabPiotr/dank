package com.exercise.dank.repo;


import com.exercise.dank.model.domain.EducationRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EducationRecordRepository extends MongoRepository<EducationRecord, String> {
    List<EducationRecord>findAllByUserUserId(String id);
    List<EducationRecord>findAllByUserUsername(String username);
    Page<EducationRecord> findAllByInstitutionId(String institutionId, Pageable pageable);
    Optional<EducationRecord> findAllByInstitutionId(String institutionId);
    Page<EducationRecord> findAllByInstitutionIdAndUserUserIdIn(String institutionId, List<String> ids, Pageable pageable);
}
