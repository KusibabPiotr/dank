package com.exercise.dank.mapper;

import com.exercise.dank.model.domain.EducationRecord;
import com.exercise.dank.model.dto.EducationRecordDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EducationRecordMapper {
    public EducationRecord mapDtoToEducationRecord(EducationRecordDto dto){
        return EducationRecord.builder()
                .userId(dto.userId())
                .publicId(dto.publicId())
                .institutionId(dto.institutionId())
                .degree(dto.degree())
                .build();
    }
    public EducationRecordDto mapEducationRecordToDto(EducationRecord educationRecord){
        return EducationRecordDto.builder()
                .userId(educationRecord.getUserId())
                .publicId(educationRecord.getPublicId())
                .institutionId(educationRecord.getInstitutionId())
                .degree(educationRecord.getDegree())
                .build();
    }
    public List<EducationRecordDto> mapListOfEducationRecordToDtoList(List<EducationRecord> educationRecordList) {
        return educationRecordList.stream().map(this::mapEducationRecordToDto).toList();
    }
}
