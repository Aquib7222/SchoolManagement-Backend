package com.schoolmanagement.schoolmanagementwebsite.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroup;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserGroupRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.UserGroupService;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupRepository repository;

    // ==========================================
    // GET ALL ACTIVE GROUPS
    // ==========================================

    @Override
    public List<UserGroup> getAllGroups() {

        return repository.findByStatus("Active");
    }

    // ==========================================
    // CREATE
    // ==========================================

    @Override
    public UserGroup createGroup(UserGroup userGroup) {

        if (userGroup.getGroupName() == null ||
                userGroup.getGroupName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Group name is required"
            );
        }

        if (userGroup.getGroupCode() == null ||
                userGroup.getGroupCode().trim().isEmpty()) {

            throw new RuntimeException(
                    "Group code is required"
            );
        }

        String groupName =
                userGroup.getGroupName().trim();

        String groupCode =
                userGroup.getGroupCode()
                        .trim()
                        .toUpperCase();

        // Duplicate name
        if (repository.existsByGroupNameIgnoreCase(groupName)) {

            throw new RuntimeException(
                    "User group with this name already exists"
            );
        }

        // Duplicate code
        if (repository.existsByGroupCodeIgnoreCase(groupCode)) {

            throw new RuntimeException(
                    "User group with this code already exists"
            );
        }

        userGroup.setGroupName(groupName);
        userGroup.setGroupCode(groupCode);

        if (userGroup.getStatus() == null ||
                userGroup.getStatus().trim().isEmpty()) {

            userGroup.setStatus("Active");
        }

        return repository.save(userGroup);
    }

    @Override
public UserGroup getGroupById(Long id) {

    return repository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException(
                            "User Group not found with id: " + id
                    )
            );
}

    // ==========================================
    // UPDATE
    // ==========================================

    @Override
    public UserGroup updateGroup(
            Long id,
            UserGroup userGroup
    ) {

        UserGroup existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User group not found"
                                )
                        );

        // --------------------------------------
        // VALIDATION
        // --------------------------------------

        if (userGroup.getGroupName() == null ||
                userGroup.getGroupName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Group name is required"
            );
        }

        if (userGroup.getGroupCode() == null ||
                userGroup.getGroupCode().trim().isEmpty()) {

            throw new RuntimeException(
                    "Group code is required"
            );
        }

        String groupName =
                userGroup.getGroupName().trim();

        String groupCode =
                userGroup.getGroupCode()
                        .trim()
                        .toUpperCase();

        // --------------------------------------
        // DUPLICATE NAME
        // Ignore current record
        // --------------------------------------

        if (repository
                .existsByGroupNameIgnoreCaseAndIdNot(
                        groupName,
                        id
                )) {

            throw new RuntimeException(
                    "Another user group with this name already exists"
            );
        }

        // --------------------------------------
        // DUPLICATE CODE
        // Ignore current record
        // --------------------------------------

        if (repository
                .existsByGroupCodeIgnoreCaseAndIdNot(
                        groupCode,
                        id
                )) {

            throw new RuntimeException(
                    "Another user group with this code already exists"
            );
        }

        // --------------------------------------
        // UPDATE
        // --------------------------------------

        existing.setGroupName(groupName);
        existing.setGroupCode(groupCode);

        if (userGroup.getStatus() == null ||
                userGroup.getStatus().trim().isEmpty()) {

            existing.setStatus("Active");

        } else {

            existing.setStatus(
                    userGroup.getStatus().trim()
            );
        }

        return repository.save(existing);
    }

    // ==========================================
    // DELETE
    // ==========================================

    @Override
    public void deleteGroup(Long id) {

        UserGroup existing =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User group not found"
                                )
                        );

        repository.delete(existing);
    }

}