package com.exercise.dank.model.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Builder
@Document
public class User{
    @Id
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private List<User> connections;
}
