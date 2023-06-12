package com.exercise.dank.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserDto(
        String userId,
        String firstName,
        String lastName,
        String username,
        List<UserDto> connections
) {
}
