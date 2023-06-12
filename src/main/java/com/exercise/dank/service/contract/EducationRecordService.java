package com.exercise.dank.service.contract;

import com.exercise.dank.model.dto.EducationRecordDto;
import com.exercise.dank.model.dto.UserDto;

import java.util.List;

public interface EducationRecordService {
    EducationRecordDto createEducationRecord(EducationRecordDto dto);

    List<EducationRecordDto> getUserEducationRecordsByUserId(String id);
    EducationRecordDto getEducationRecordById(String id);
    EducationRecordDto updateEducationRecordById(String id, EducationRecordDto dto);
    String deleteEducationRecordById(String id);

    List<UserDto> getAllUsersForGivenInstitution(String institutionId, String sortBy, String sortDirection, Integer page, Integer pageSize);

    List<UserDto> getUsersByInstitutionAndConnections(String institutionId, String sortBy, String sortDirection, Integer page, Integer pageSize);
}
