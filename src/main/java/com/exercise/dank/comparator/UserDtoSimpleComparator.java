package com.exercise.dank.comparator;

import com.exercise.dank.model.dto.UserDto;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
@RequiredArgsConstructor
public class UserDtoSimpleComparator implements Comparator<UserDto> {
    private final String sortBy;
    private final String sortDirection;
    private static final String FIRST_NAME = "firstName";
    private static final String USERNAME = "username";
    private static final String DESC = "desc";
    @Override
    public int compare(UserDto user1, UserDto user2) {
        int result;
        if (sortBy.equals(FIRST_NAME)) {
            result = user1.firstName().compareTo(user2.firstName());
        } else if (sortBy.equals(USERNAME)) {
            result = user1.username().compareTo(user2.username());
        } else {
            result = user1.lastName().compareTo(user2.lastName());
        }

        if (sortDirection.equalsIgnoreCase(DESC)) {
            result = -result;
        }
        return result;
    }
}
