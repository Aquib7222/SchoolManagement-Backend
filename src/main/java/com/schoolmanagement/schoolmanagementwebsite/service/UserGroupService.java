package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroup;

public interface UserGroupService {

    List<UserGroup> getAllGroups();

    UserGroup getGroupById(Long id);

    UserGroup createGroup(UserGroup userGroup);

    UserGroup updateGroup(Long id, UserGroup userGroup);

    void deleteGroup(Long id);
}