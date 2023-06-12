package com.exercise.dank.model.dto;

import lombok.Builder;

@Builder
public record EducationRecordDto(
        String publicId,
        String institutionId,
        String degree,
        UserDto userDto
) {
}
