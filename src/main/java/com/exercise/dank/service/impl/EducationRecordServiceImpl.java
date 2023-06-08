package com.exercise.dank.service.impl;

import com.exercise.dank.mapper.EducationRecordMapper;
import com.exercise.dank.model.dto.EducationRecordDto;
import com.exercise.dank.repo.EducationRecordRepository;
import com.exercise.dank.service.contract.EducationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationRecordServiceImpl implements EducationRecordService {
    private final EducationRecordRepository educationRecordRepository;
    private final EducationRecordMapper educationRecordMapper;
    @Override
    public EducationRecordDto createEducationRecord(EducationRecordDto dto) {
        return educationRecordRepository.save(educationRecordMapper.mapEducationRecordToDto());
    }
}
