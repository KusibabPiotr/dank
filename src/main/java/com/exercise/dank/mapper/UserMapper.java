package com.exercise.dank.mapper;

import com.exercise.dank.model.domain.User;
import com.exercise.dank.model.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public User mapUserDtoToUser(UserDto dto) {
        return User.builder()
                .userId(dto.userId())
                .lastName(dto.lastName())
                .firstName(dto.firstName())
                .username(dto.username())
                .connections(mapListOfUsersDtoToListOfUsers(dto.connections()))
                .build();
    }
    public UserDto mapUserToUserDto(User user){
        return UserDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .connections(mapListOfUsersToListOfUsersDto(user.getConnections()))
                .build();
    }

    public List<UserDto> mapListOfUsersToListOfUsersDto(List<User> list) {
        return list.stream().map(this::mapUserToUserDto).toList();
    }

    public List<User> mapListOfUsersDtoToListOfUsers(List<UserDto> list) {
        return list.stream().map(this::mapUserDtoToUser).toList();
    }
}
