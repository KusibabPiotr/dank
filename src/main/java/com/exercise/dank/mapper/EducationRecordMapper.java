package com.exercise.dank.mapper;

import com.exercise.dank.model.domain.EducationRecord;
import com.exercise.dank.model.dto.EducationRecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EducationRecordMapper {
    private final UserMapper userMapper;
    public EducationRecord mapDtoToEducationRecord(EducationRecordDto dto){
        return EducationRecord.builder()
                .user(userMapper.mapUserDtoToUser(dto.userDto()))
                .publicId(dto.publicId())
                .institutionId(dto.institutionId())
                .degree(dto.degree())
                .build();
    }
    public EducationRecordDto mapEducationRecordToDto(EducationRecord educationRecord){
        return EducationRecordDto.builder()
                .userDto(userMapper.mapUserToUserDto(educationRecord.getUser()))
                .publicId(educationRecord.getPublicId())
                .institutionId(educationRecord.getInstitutionId())
                .degree(educationRecord.getDegree())
                .build();
    }
    public List<EducationRecordDto> mapListOfEducationRecordToDtoList(List<EducationRecord> educationRecordList) {
        return educationRecordList.stream().map(this::mapEducationRecordToDto).toList();
    }
}
