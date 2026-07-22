package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.SaveSchoolMappingRequest;
import com.schoolmanagement.schoolmanagementwebsite.dto.SchoolMappingResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.SidebarMenuResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.SidebarModuleResponse;
import com.schoolmanagement.schoolmanagementwebsite.dto.SidebarSubMenuResponse;
import com.schoolmanagement.schoolmanagementwebsite.entity.Menu;
import com.schoolmanagement.schoolmanagementwebsite.entity.Module;
import com.schoolmanagement.schoolmanagementwebsite.entity.School;
import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolGroupMapping;
import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolMenuMapping;
import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolModuleMapping;
import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolSubMenuMapping;
import com.schoolmanagement.schoolmanagementwebsite.entity.SubMenu;
import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroup;
import com.schoolmanagement.schoolmanagementwebsite.repository.*;

@ Service 

    @Transactional
    public class SchoolMappingService {

        private final SchoolRepository schoolRepository;
        private final UserGroupRepository userGroupRepository;

        private final ModuleRepository moduleRepository;
        private final MenuRepository menuRepository;
        private final SubMenuRepository subMenuRepository;

        private final SchoolGroupMappingRepository schoolGroupMappingRepository;

        public SchoolMappingService(
                SchoolRepository schoolRepository,
                UserGroupRepository userGroupRepository,
                ModuleRepository moduleRepository,
                MenuRepository menuRepository,
                SubMenuRepository subMenuRepository,
                SchoolGroupMappingRepository schoolGroupMappingRepository) {

            this.schoolRepository = schoolRepository;
            this.userGroupRepository = userGroupRepository;
            this.moduleRepository = moduleRepository;
            this.menuRepository = menuRepository;
            this.subMenuRepository = subMenuRepository;
            this.schoolGroupMappingRepository = schoolGroupMappingRepository;
        }

        // public void saveMapping(SaveSchoolMappingRequest request) {
    //     System.out.println (request.getSchoolId

    //     ());
    //     System.out.println (request.getUserGroupId

    //     ());
    //     System.out.println (request.getModuleIds

    //     ());
    //     System.out.println (request.getMenuIds

    //     ());
    //     System.out.println (request.getSubMenuIds
    //     ());

        

    //     School school = schoolRepository.findById(request.getSchoolId())
    //             .orElseThrow(() -> new RuntimeException("School not found"));

    //     UserGroup group = userGroupRepository.findById(request.getUserGroupId())
    //             .orElseThrow(() -> new RuntimeException("User Group not found"));

    //     SchoolGroupMapping groupMapping = new SchoolGroupMapping();

    //     groupMapping.setSchool (school);

    //     groupMapping.setUserGroup (group);

    //     for (Long moduleId : request.getModuleIds()) {

    //         com.schoolmanagement.schoolmanagementwebsite.entity.Module module
    //                 = moduleRepository.findById(moduleId)
    //                         .orElseThrow();

    //         SchoolModuleMapping moduleMapping = new SchoolModuleMapping();

    //         moduleMapping.setSchoolGroupMapping(groupMapping);
    //         moduleMapping.setModule(module);
    //         for (Long menuId : request.getMenuIds()) {

    //             Menu menu = menuRepository.findById(menuId)
    //                     .orElseThrow();

    //             if (!menu.getModule().getId().equals(moduleId)) {
    //                 continue;
    //             }

    //             SchoolMenuMapping menuMapping = new SchoolMenuMapping();

    //             menuMapping.setSchoolModuleMapping(moduleMapping);
    //             menuMapping.setMenu(menu);
    //             for (Long subId : request.getSubMenuIds()) {

    //                 SubMenu subMenu = subMenuRepository.findById(subId)
    //                         .orElseThrow();

    //                 if (!subMenu.getMenu().getId().equals(menuId)) {
    //                     continue;
    //                 }

    //                 SchoolSubMenuMapping subMapping
    //                         = new SchoolSubMenuMapping();

    //                 subMapping.setSchoolMenuMapping(menuMapping);
    //                 subMapping.setSubMenu(subMenu);

    //                 menuMapping.getSubMenuMappings().add(subMapping);
    //             }

    //             moduleMapping.getMenuMappings().add(menuMapping);
    //         }

    //         groupMapping.getModuleMappings().add(moduleMapping);
    //     }

    //     schoolGroupMappingRepository.save (groupMapping);
    // }

    public void saveMapping(SaveSchoolMappingRequest request) {

        System.out.println(request.getSchoolId());
        System.out.println(request.getUserGroupId());
        System.out.println(request.getModuleIds());
        System.out.println(request.getMenuIds());
        System.out.println(request.getSubMenuIds());

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found"));

        UserGroup group = userGroupRepository.findById(request.getUserGroupId())
                .orElseThrow(() -> new RuntimeException("User Group not found"));

        SchoolGroupMapping groupMapping = schoolGroupMappingRepository
                .findBySchoolIdAndUserGroupId(
                        request.getSchoolId(),
                        request.getUserGroupId())
                .orElse(null);

        if (groupMapping == null) {

            groupMapping = new SchoolGroupMapping();
            groupMapping.setSchool(school);
            groupMapping.setUserGroup(group);

        }

        for (Long moduleId : request.getModuleIds()) {

            Module module = moduleRepository
                    .findById(moduleId)
                    .orElseThrow();

            SchoolModuleMapping moduleMapping = groupMapping
                    .getModuleMappings()
                    .stream()
                    .filter(m -> m.getModule().getId().equals(moduleId))
                    .findFirst()
                    .orElse(null);

            if (moduleMapping == null) {

                moduleMapping = new SchoolModuleMapping();
                moduleMapping.setSchoolGroupMapping(groupMapping);
                moduleMapping.setModule(module);

                groupMapping.getModuleMappings().add(moduleMapping);
            }
            for (Long menuId : request.getMenuIds()) {

                Menu menu = menuRepository
                        .findById(menuId)
                        .orElseThrow();

                if (!menu.getModule().getId().equals(moduleId)) {
                    continue;
                }

                SchoolMenuMapping menuMapping = moduleMapping
                        .getMenuMappings()
                        .stream()
                        .filter(m -> m.getMenu().getId().equals(menuId))
                        .findFirst()
                        .orElse(null);

                if (menuMapping == null) {

                    menuMapping = new SchoolMenuMapping();
                    menuMapping.setSchoolModuleMapping(moduleMapping);
                    menuMapping.setMenu(menu);

                    moduleMapping.getMenuMappings().add(menuMapping);
                }

                // 👇 Yahin se submenu loop start hoga
                for (Long subId : request.getSubMenuIds()) {

                    SubMenu subMenu = subMenuRepository
                            .findById(subId)
                            .orElseThrow();

                    if (!subMenu.getMenu().getId().equals(menuId)) {
                        continue;
                    }

                    boolean alreadyExists = menuMapping
                            .getSubMenuMappings()
                            .stream()
                            .anyMatch(s -> s.getSubMenu().getId().equals(subId));

                    if (!alreadyExists) {

                        SchoolSubMenuMapping subMapping
                                = new SchoolSubMenuMapping();

                        subMapping.setSchoolMenuMapping(menuMapping);
                        subMapping.setSubMenu(subMenu);

                        menuMapping.getSubMenuMappings().add(subMapping);
                    }
                }
            }
        }

        schoolGroupMappingRepository.save(groupMapping);
    }

    public SchoolMappingResponse loadMapping(Long schoolId, Long groupId) {

        SchoolGroupMapping mapping = schoolGroupMappingRepository
                .findBySchoolIdAndUserGroupId(schoolId, groupId)
                .orElse(null);

        if (mapping == null) {
            return new SchoolMappingResponse();
        }

        return convertToResponse(mapping);
    }

    private SchoolMappingResponse convertToResponse(
            SchoolGroupMapping mapping) {

        SchoolMappingResponse response = new SchoolMappingResponse();

        for (SchoolModuleMapping moduleMapping : mapping.getModuleMappings()) {

            response.getModuleIds().add(
                    moduleMapping.getModule().getId());

            for (SchoolMenuMapping menuMapping
                    : moduleMapping.getMenuMappings()) {

                response.getMenuIds().add(
                        menuMapping.getMenu().getId());

                for (SchoolSubMenuMapping subMapping
                        : menuMapping.getSubMenuMappings()) {

                    response.getSubMenuIds().add(
                            subMapping.getSubMenu().getId());
                }
            }
        }

        return response;
    }

    public List<SidebarModuleResponse> getSidebar(Long schoolId, Long groupId) {

        SchoolGroupMapping mapping = schoolGroupMappingRepository
                .findBySchoolIdAndUserGroupId(schoolId, groupId)
                .orElse(null);

        if (mapping == null) {
            return new ArrayList<>();
        }

        List<SidebarModuleResponse> sidebar = new ArrayList<>();

        for (SchoolModuleMapping moduleMapping : mapping.getModuleMappings()) {

            Module module = moduleMapping.getModule();

            SidebarModuleResponse moduleDto = new SidebarModuleResponse();
            moduleDto.setId(module.getId());
            moduleDto.setLabel(module.getModuleName());
            moduleDto.setIcon(module.getIcon());
            moduleDto.setPath(module.getPath());
            moduleDto.setImage(module.getImage());

            List<SidebarMenuResponse> menus = new ArrayList<>();

            for (SchoolMenuMapping menuMapping : moduleMapping.getMenuMappings()) {

                Menu menu = menuMapping.getMenu();

                SidebarMenuResponse menuDto = new SidebarMenuResponse();
                menuDto.setId(menu.getId());
                menuDto.setLabel(menu.getMenuName());
                menuDto.setPath(menu.getMenuUrl());

                List<SidebarSubMenuResponse> subMenus = new ArrayList<>();

                for (SchoolSubMenuMapping subMapping : menuMapping.getSubMenuMappings()) {

                    SubMenu sub = subMapping.getSubMenu();

                    SidebarSubMenuResponse subDto = new SidebarSubMenuResponse();
                    subDto.setId(sub.getId());
                    subDto.setLabel(sub.getSubMenuName());
                    subDto.setPath(sub.getSubMenuUrl());

                    subMenus.add(subDto);
                }

                menuDto.setSubSubMenu(subMenus);

                menus.add(menuDto);
            }

            moduleDto.setSubMenus(menus);

            sidebar.add(moduleDto);
        }

        return sidebar;
    }
}
