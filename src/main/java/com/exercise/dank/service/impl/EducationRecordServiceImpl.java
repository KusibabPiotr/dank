package com.exercise.dank.service.impl;

import com.exercise.dank.exception.EducationRecordNotFound;
import com.exercise.dank.mapper.EducationRecordMapper;
import com.exercise.dank.model.domain.EducationRecord;
import com.exercise.dank.model.dto.EducationRecordDto;
import com.exercise.dank.repo.EducationRecordRepository;
import com.exercise.dank.service.contract.EducationRecordService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationRecordServiceImpl implements EducationRecordService {
    private final EducationRecordRepository educationRecordRepository;
    private final EducationRecordMapper educationRecordMapper;
    @Override
    public EducationRecordDto createEducationRecord(EducationRecordDto dto) {
        return educationRecordMapper.mapEducationRecordToDto(educationRecordRepository.insert(educationRecordMapper.mapDtoToEducationRecord(dto)));
    }

    @Override
    public List<EducationRecordDto> getUserEducationRecordsByUserId(String id) {
        return educationRecordMapper.mapListOfEducationRecordToDtoList(educationRecordRepository.findAllByUserId(id));
    }

    @Override
    public EducationRecordDto getEducationRecordById(String id) {
        return educationRecordMapper.mapEducationRecordToDto(educationRecordRepository.findById(id)
                .orElseThrow(EducationRecordNotFound::new));
    }

    @Override
    public EducationRecordDto updateEducationRecordById(String id, EducationRecordDto dto) {
        EducationRecord educationRecord = getEducationRecordByIdAndSetNewValues(id, dto);
        return educationRecordMapper.mapEducationRecordToDto(educationRecord);
    }

    @Override
    public String deleteEducationRecordById(String id) {
        educationRecordRepository.deleteById(id);
        return "Successfully deleted education record!";
    }

    private EducationRecord getEducationRecordByIdAndSetNewValues(String id, EducationRecordDto dto) {
        EducationRecord educationRecord = educationRecordRepository.findById(id).orElseThrow(EducationRecordNotFound::new);
        educationRecord.setUserId(dto.userId());
        educationRecord.setPublicId(dto.publicId());
        educationRecord.setInstitutionId(dto.institutionId());
        educationRecord.setDegree(dto.degree());
        return educationRecord;
    }
}
