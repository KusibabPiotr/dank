package com.exercise.dank.controller;

import com.exercise.dank.model.dto.EducationRecordDto;
import com.exercise.dank.service.contract.EducationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EducationRecordController {
    private final EducationRecordService educationRecordService;

    @PostMapping("/education")
    public ResponseEntity<EducationRecordDto> createEducationRecord(@RequestBody EducationRecordDto dto){
        return ResponseEntity.ok(educationRecordService.createEducationRecord(dto));
    }
}
