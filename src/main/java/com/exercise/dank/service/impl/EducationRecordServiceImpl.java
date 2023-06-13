package com.exercise.dank.service.impl;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
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
    public Page<UserDto> getAllUsersForGivenInstitution(String institutionId, String sortBy, String sortDirection, Integer page, Integer pageSize) {
        PageRequest pageRequest = createPageRequest(sortBy, sortDirection, page, pageSize);
        Page<EducationRecord> educationRecordPage = educationRecordRepository.findAllByInstitutionId(institutionId, pageRequest);

        List<UserDto> list = educationRecordPage.getContent().stream()
                .filter(record -> record.getInstitutionId().equals(institutionId))
                .map(record -> userMapper.mapUserToUserDto(record.getUser()))
                .sorted(getComparator(sortBy, sortDirection))
                .distinct().toList();
        return new PageImpl<>(list, pageRequest, educationRecordPage.getTotalElements());
    }

    @Override
    public Page<UserDto> getUsersByInstitutionAndConnections(
            String institutionId,
            String sortBy,
            String sortDirection,
            Integer page,
            Integer pageSize) {

        PageRequest pageRequest = createPageRequest(sortBy, sortDirection, page, pageSize);

        List<String> connectedUserIds = getConnectedUserIds();
        userMapper.mapPageOfUsersToPageOfUsersDto(educationRecordRepository.findAllByInstitutionIdAndUserUserIdIn(
                        institutionId, connectedUserIds, pageRequest)
                .map(e -> e.getUser().getConnections())
        );
        //get all users connections lists, combine them together???
        //return from repo not page but list
    }

    private PageRequest createPageRequest(String sortBy, String sortDirection, Integer page, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        return PageRequest.of(page, pageSize, sort);
    }

    private List<String> getConnectedUserIds() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        return educationRecordRepository.findAllByUserUsername(principal).stream().map(e -> e.getUser().getConnections())
                .flatMap(Collection::stream)
                .map(User::getId).toList();
    }

    private Comparator<UserDto> getComparator(String sortBy, String sortDirection) {
        Comparator<UserDto> comparator;

        if (sortBy.equals("firstName")) {
            comparator = Comparator.comparing(UserDto::firstName);
        } else {
            comparator = Comparator.comparing(UserDto::lastName);
        }

        if (sortDirection.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        return comparator;
    }
}
