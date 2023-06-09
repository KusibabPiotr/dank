package com.exercise.dank.service.contract;

import com.exercise.dank.model.dto.EducationRecordDto;

import java.util.List;

public interface EducationRecordService {
    EducationRecordDto createEducationRecord(EducationRecordDto dto);

    List<EducationRecordDto> getUserEducationRecordsByUserId(String id);
    EducationRecordDto getEducationRecordById(String id);
    EducationRecordDto updateEducationRecordById(String id, EducationRecordDto dto);
    String deleteEducationRecordById(String id);
}
