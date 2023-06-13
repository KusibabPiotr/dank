package com.exercise.dank.service.contract;

import com.exercise.dank.model.dto.EducationRecordDto;
import com.exercise.dank.model.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EducationRecordService {
    EducationRecordDto createEducationRecord(EducationRecordDto dto);

    List<EducationRecordDto> getUserEducationRecordsByUserId(String id);
    EducationRecordDto getEducationRecordById(String id);
    EducationRecordDto updateEducationRecordById(String id, EducationRecordDto dto);
    String deleteEducationRecordById(String id);

    Page<UserDto> getAllUsersForGivenInstitution(String institutionId, String sortBy, String sortDirection, Integer page, Integer pageSize);

    Page<UserDto> getUsersByInstitutionAndConnections(String institutionId, String sortBy, String sortDirection, Integer page, Integer pageSize);
}
