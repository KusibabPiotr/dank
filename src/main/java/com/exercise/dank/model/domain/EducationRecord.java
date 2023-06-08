package com.exercise.dank.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationRecord {
    private Long id;
    private String publicId;
    private String userId;
    private String institutionId;
    private String degree;
}
