package com.exercise.dank.controller;

import com.exercise.dank.model.dto.EducationRecordDto;
import com.exercise.dank.model.dto.UserDto;
import com.exercise.dank.service.contract.EducationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<EducationRecordDto>> getUserEducationRecordsByUserId(@PathVariable String userId){
        return ResponseEntity.ok(educationRecordService.getUserEducationRecordsByUserId(userId));
    }

    @GetMapping("/education/{id}")
    public ResponseEntity<EducationRecordDto> getEducationRecordByInstitutionId(@PathVariable String id){
        return ResponseEntity.ok(educationRecordService.getEducationRecordById(id));
    }

    @PutMapping("/education/{id}")
    public ResponseEntity<EducationRecordDto> updateEducationRecordById(@PathVariable String id,
                                                                        @RequestBody EducationRecordDto dto){
        return ResponseEntity.ok(educationRecordService.updateEducationRecordById(id, dto));
    }

    @DeleteMapping("/education/{id}")
    public ResponseEntity<String> deleteEducationRecordById(@PathVariable String id){
        return ResponseEntity.ok(educationRecordService.deleteEducationRecordById(id));
    }

    @GetMapping("/education/users")
    public ResponseEntity<Page<UserDto>> getUsersByInstitutionAndConnections(
            @RequestParam String institutionId,
            @RequestParam Boolean connectionBasedSorting,
            @RequestParam(required = false, defaultValue = "lastName") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if (connectionBasedSorting)
            return ResponseEntity.ok(educationRecordService.getUsersByInstitutionAndConnections(institutionId, sortBy, sortDirection, page, pageSize));
        else
            return ResponseEntity.ok(educationRecordService.getAllUsersForGivenInstitution(institutionId, sortBy, sortDirection, page, pageSize));
    }
}
