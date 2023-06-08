package com.exercise.dank.controller;

import com.exercise.dank.model.dto.EducationRecordDto;
import com.exercise.dank.service.contract.EducationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EducationRecordController {
    private final EducationRecordService educationRecordService;

    @PostMapping("/education")
    public ResponseEntity<EducationRecordDto> createEducationRecord(@RequestBody EducationRecordDto dto){
        return ResponseEntity.ok(educationRecordService.createEducationRecord(dto));
    }

    @GetMapping("/educations/{userId}")
    public ResponseEntity<List<EducationRecordDto>> getUserEducationRecordsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(educationRecordService.getUserEducationRecordsByUserId(userId));
    }

    @GetMapping("/education/{id}")
    public ResponseEntity<EducationRecordDto> getEducationRecordById(@PathVariable Long id){
        return ResponseEntity.ok(educationRecordService.getEducationRecordById(id));
    }

    @PutMapping("/education/{id}")
    public ResponseEntity<EducationRecordDto> updateEducationRecordById(@PathVariable Long id, @RequestBody EducationRecordDto dto){
        return ResponseEntity.ok(educationRecordService.updateEducationRecordById(id, dto));
    }

    @DeleteMapping("/education/{id}")
    public ResponseEntity<String> deleteEducationRecordById(@PathVariable Long id){
        return ResponseEntity.ok(educationRecordService.deleteEducationRecordById(id));
    }
}
