package com.exercise.dank.model.dto;

import lombok.Builder;

@Builder
public record EducationRecordDto(
        String publicId,
        String userId,
        String institutionId,
        String degree
) {
}
