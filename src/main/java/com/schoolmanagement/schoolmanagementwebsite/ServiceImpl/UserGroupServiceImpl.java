package com.schoolmanagement.schoolmanagementwebsite.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroup;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserGroupRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.UserGroupService;

@Service
public class UserGroupServiceImpl implements UserGroupService{

    @Autowired
    private UserGroupRepository repository;

    @Override
    public List<UserGroup> getAllGroups() {

        return repository.findByStatus("Active");

    }

}