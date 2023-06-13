package com.exercise.dank.service.impl;

import com.exercise.dank.comparator.UserDtoSimpleComparator;
import com.exercise.dank.exception.EducationRecordNotFound;
import com.exercise.dank.mapper.EducationRecordMapper;
import com.exercise.dank.mapper.UserMapper;
import com.exercise.dank.model.domain.EducationRecord;
import com.exercise.dank.model.domain.User;
import com.exercise.dank.model.dto.EducationRecordDto;
import com.exercise.dank.model.dto.UserDto;
import com.exercise.dank.repo.EducationRecordRepository;
import com.exercise.dank.service.contract.EducationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationRecordServiceImpl implements EducationRecordService {
    private final EducationRecordRepository educationRecordRepository;
    private final EducationRecordMapper educationRecordMapper;
    private final UserMapper userMapper;

    @Override
    public EducationRecordDto createEducationRecord(EducationRecordDto dto) {
        return educationRecordMapper.mapEducationRecordToDto(educationRecordRepository.insert(educationRecordMapper.mapDtoToEducationRecord(dto)));
    }

    @Override
    public List<EducationRecordDto> getUserEducationRecordsByUserId(String id) {
        return educationRecordMapper.mapListOfEducationRecordToDtoList(educationRecordRepository.findAllByUserUserId(id));
    }

    @Override
    public EducationRecordDto getEducationRecordById(String id) {
        return educationRecordMapper.mapEducationRecordToDto(educationRecordRepository.findAllByInstitutionId(id)
                .orElseThrow(EducationRecordNotFound::new));
    }

    @Override
    public EducationRecordDto updateEducationRecordById(String id, EducationRecordDto dto) {
        EducationRecord educationRecord = educationRecordRepository.findById(id).orElseThrow(EducationRecordNotFound::new);
        educationRecord.setUser(userMapper.mapUserDtoToUser(dto.userDto()));
        educationRecord.setPublicId(dto.publicId());
        educationRecord.setInstitutionId(dto.institutionId());
        educationRecord.setDegree(dto.degree());
        educationRecordRepository.save(educationRecord);
        return educationRecordMapper.mapEducationRecordToDto(educationRecord);
    }

    @Override
    public String deleteEducationRecordById(String id) {
        educationRecordRepository.deleteById(id);
        return "Successfully deleted education record!";
    }

    @Override
    public Page<UserDto> getAllUsersForGivenInstitution(String institutionId, String sortBy, String sortDirection,
                                                        Integer page, Integer pageSize) {
        Page<EducationRecord> educationRecordPage = getAllEducationRecordsForInstitution(institutionId, sortBy, sortDirection, page, pageSize);
        List<UserDto> list = getAllUsersForGivenInstitution(educationRecordPage, sortBy, sortDirection);
        return new PageImpl<>(list, createPageRequest(sortBy, sortDirection, page, pageSize), educationRecordPage.getTotalElements());
    }

    @Override
    public Page<UserDto> getUsersByInstitutionAndConnections(String userId, String institutionId, String sortBy, String sortDirection,
            Integer page, Integer pageSize) {
        Page<EducationRecord> educationRecordPage = getAllEducationRecordsForInstitution(institutionId, sortBy, sortDirection, page, pageSize);
        List<UserDto> list = getAllUsersForGivenInstitution(educationRecordPage, sortBy, sortDirection);
        List<String> connectedUserIds = getConnectedUserIds(userId);
        PageImpl<UserDto> userDtoPageable =  new PageImpl<>(list, createPageRequest(sortBy, sortDirection, page, pageSize), educationRecordPage.getTotalElements());
        List<UserDto> allConnectedUsersFromWholeList = getAllConnectedUsersFromWholeList(userDtoPageable, connectedUserIds);
        return moveConnectedUsersUpInSortedPosition(userDtoPageable, allConnectedUsersFromWholeList);
    }

    private Page<UserDto> moveConnectedUsersUpInSortedPosition(PageImpl<UserDto> userDtoPageable, List<UserDto> allConnectedUsersFromWholeList) {
        List<UserDto> userList = new ArrayList<>(userDtoPageable.getContent());

        List<UserDto> connectedUsers = userList.stream()
                .filter(allConnectedUsersFromWholeList::contains)
                .toList();

        userList.removeAll(connectedUsers);
        userList.addAll(0, connectedUsers);

        int totalElements = userList.size();
        Pageable pageable = userDtoPageable.getPageable();
        int startIndex = (int)pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), totalElements);
        List<UserDto> usersForPage = userList.subList(startIndex, endIndex);

        return new PageImpl<>(usersForPage, pageable, totalElements);
    }

    private List<UserDto> getAllConnectedUsersFromWholeList(PageImpl<UserDto> userDtoPageable, List<String> connectedUserIds){
        return userDtoPageable.getContent().stream().filter(e -> {
            for (UserDto userDto : e.connections()) {
                for (String connectedUserId : connectedUserIds) {
                    if (connectedUserId.equals(userDto.userId())) {
                        return true;
                    }
                }
            }
            return false;
        }).toList();
    }

    private List<UserDto> getAllUsersForGivenInstitution(Page<EducationRecord> educationRecordPage, String sortBy, String sortDirection){
        return educationRecordPage.getContent().stream()
                .map(record -> userMapper.mapUserToUserDto(record.getUser()))
                .sorted(new UserDtoSimpleComparator(sortBy, sortDirection))
                .distinct().toList();
    }

    private Page<EducationRecord> getAllEducationRecordsForInstitution(String institutionId, String sortBy, String sortDirection,
                                                                       Integer page, Integer pageSize){
        PageRequest pageRequest = createPageRequest(sortBy, sortDirection, page, pageSize);
        return educationRecordRepository.findAllByInstitutionId(institutionId, pageRequest);
    }

    private PageRequest createPageRequest(String sortBy, String sortDirection, Integer page, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        return PageRequest.of(page, pageSize, sort);
    }

    private List<String> getConnectedUserIds(String userId) {
        return educationRecordRepository.findAllByUserUserId(userId).stream()
                .map(e -> e.getUser().getConnections())
                .flatMap(Collection::stream)
                .map(User::getId).toList();
    }
}
