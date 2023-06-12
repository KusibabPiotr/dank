package com.exercise.dank.model.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class EducationRecord {
    @Id
    private String id;
    private String publicId;
    private String institutionId;
    private String degree;
    private User user;
}
