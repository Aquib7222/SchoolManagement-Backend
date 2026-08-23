// package com.schoolmanagement.schoolmanagementwebsite.service;
// import java.util.ArrayList;
// import java.util.List;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import com.schoolmanagement.schoolmanagementwebsite.dto.SaveSchoolMappingRequest;
// import com.schoolmanagement.schoolmanagementwebsite.dto.SchoolMappingResponse;
// import com.schoolmanagement.schoolmanagementwebsite.dto.SidebarMenuResponse;
// import com.schoolmanagement.schoolmanagementwebsite.dto.SidebarModuleResponse;
// import com.schoolmanagement.schoolmanagementwebsite.dto.SidebarSubMenuResponse;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Menu;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Module;
// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolGroupMapping;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolMenuMapping;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolModuleMapping;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolSubMenuMapping;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SubMenu;
// import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroup;
// import com.schoolmanagement.schoolmanagementwebsite.repository.*;
// @ Service 
//     @Transactional
//     public class SchoolMappingService {
//         private final SchoolRepository schoolRepository;
//         private final UserGroupRepository userGroupRepository;
//         private final ModuleRepository moduleRepository;
//         private final MenuRepository menuRepository;
//         private final SubMenuRepository subMenuRepository;
//         private final SchoolGroupMappingRepository schoolGroupMappingRepository;
//         public SchoolMappingService(
//                 SchoolRepository schoolRepository,
//                 UserGroupRepository userGroupRepository,
//                 ModuleRepository moduleRepository,
//                 MenuRepository menuRepository,
//                 SubMenuRepository subMenuRepository,
//                 SchoolGroupMappingRepository schoolGroupMappingRepository) {
//             this.schoolRepository = schoolRepository;
//             this.userGroupRepository = userGroupRepository;
//             this.moduleRepository = moduleRepository;
//             this.menuRepository = menuRepository;
//             this.subMenuRepository = subMenuRepository;
//             this.schoolGroupMappingRepository = schoolGroupMappingRepository;
//         }
//     // public void saveMapping(SaveSchoolMappingRequest request) {
//     //     System.out.println(request.getSchoolId());
//     //     System.out.println(request.getUserGroupId());
//     //     System.out.println(request.getModuleIds());
//     //     System.out.println(request.getMenuIds());
//     //     System.out.println(request.getSubMenuIds());
//     //     School school = schoolRepository.findById(request.getSchoolId())
//     //             .orElseThrow(() -> new RuntimeException("School not found"));
//     //     UserGroup group = userGroupRepository.findById(request.getUserGroupId())
//     //             .orElseThrow(() -> new RuntimeException("User Group not found"));
//     //     SchoolGroupMapping groupMapping = schoolGroupMappingRepository
//     //             .findBySchoolIdAndUserGroupId(
//     //                     request.getSchoolId(),
//     //                     request.getUserGroupId())
//     //             .orElse(null);
//     //     if (groupMapping == null) {
//     //         groupMapping = new SchoolGroupMapping();
//     //         groupMapping.setSchool(school);
//     //         groupMapping.setUserGroup(group);
//     //     }
//     //     for (Long moduleId : request.getModuleIds()) {
//     //         Module module = moduleRepository
//     //                 .findById(moduleId)
//     //                 .orElseThrow();
//     //         SchoolModuleMapping moduleMapping = groupMapping
//     //                 .getModuleMappings()
//     //                 .stream()
//     //                 .filter(m -> m.getModule().getId().equals(moduleId))
//     //                 .findFirst()
//     //                 .orElse(null);
//     //         if (moduleMapping == null) {
//     //             moduleMapping = new SchoolModuleMapping();
//     //             moduleMapping.setSchoolGroupMapping(groupMapping);
//     //             moduleMapping.setModule(module);
//     //             groupMapping.getModuleMappings().add(moduleMapping);
//     //         }
//     //         for (Long menuId : request.getMenuIds()) {
//     //             Menu menu = menuRepository
//     //                     .findById(menuId)
//     //                     .orElseThrow();
//     //             if (!menu.getModule().getId().equals(moduleId)) {
//     //                 continue;
//     //             }
//     //             SchoolMenuMapping menuMapping = moduleMapping
//     //                     .getMenuMappings()
//     //                     .stream()
//     //                     .filter(m -> m.getMenu().getId().equals(menuId))
//     //                     .findFirst()
//     //                     .orElse(null);
//     //             if (menuMapping == null) {
//     //                 menuMapping = new SchoolMenuMapping();
//     //                 menuMapping.setSchoolModuleMapping(moduleMapping);
//     //                 menuMapping.setMenu(menu);
//     //                 moduleMapping.getMenuMappings().add(menuMapping);
//     //             }
//     //             // 👇 Yahin se submenu loop start hoga
//     //             for (Long subId : request.getSubMenuIds()) {
//     //                 SubMenu subMenu = subMenuRepository
//     //                         .findById(subId)
//     //                         .orElseThrow();
//     //                 if (!subMenu.getMenu().getId().equals(menuId)) {
//     //                     continue;
//     //                 }
//     //                 boolean alreadyExists = menuMapping
//     //                         .getSubMenuMappings()
//     //                         .stream()
//     //                         .anyMatch(s -> s.getSubMenu().getId().equals(subId));
//     //                 if (!alreadyExists) {
//     //                     SchoolSubMenuMapping subMapping
//     //                             = new SchoolSubMenuMapping();
//     //                     subMapping.setSchoolMenuMapping(menuMapping);
//     //                     subMapping.setSubMenu(subMenu);
//     //                     menuMapping.getSubMenuMappings().add(subMapping);
//     //                 }
//     //             }
//     //         }
//     //     }
//     //     schoolGroupMappingRepository.save(groupMapping);
//     // }
//     @Transactional
// public void saveMapping(SaveSchoolMappingRequest request) {
//     System.out.println("School ID    : " + request.getSchoolId());
//     System.out.println("Group ID     : " + request.getUserGroupId());
//     System.out.println("Module IDs   : " + request.getModuleIds());
//     System.out.println("Menu IDs     : " + request.getMenuIds());
//     System.out.println("SubMenu IDs  : " + request.getSubMenuIds());
//     // =====================================================
//     // VALIDATE SCHOOL
//     // =====================================================
//     School school = schoolRepository
//             .findById(request.getSchoolId())
//             .orElseThrow(() ->
//                     new RuntimeException("School not found"));
//     // =====================================================
//     // VALIDATE USER GROUP
//     // =====================================================
//     UserGroup group = userGroupRepository
//             .findById(request.getUserGroupId())
//             .orElseThrow(() ->
//                     new RuntimeException("User Group not found"));
//     // =====================================================
//     // FIND EXISTING GROUP MAPPING
//     // =====================================================
//     SchoolGroupMapping groupMapping =
//             schoolGroupMappingRepository
//                     .findBySchoolIdAndUserGroupId(
//                             request.getSchoolId(),
//                             request.getUserGroupId()
//                     )
//                     .orElse(null);
//     // =====================================================
//     // CREATE GROUP MAPPING IF NOT EXISTS
//     // =====================================================
//     if (groupMapping == null) {
//         groupMapping = new SchoolGroupMapping();
//         groupMapping.setSchool(school);
//         groupMapping.setUserGroup(group);
//     }
//     // =====================================================
//     // IMPORTANT
//     //
//     // Existing mappings ko completely clear karo.
//     //
//     // CascadeType.ALL + orphanRemoval=true ke
//     // wajah se child mappings automatically delete hongi.
//     // =====================================================
//     groupMapping.getModuleMappings().clear();
//     // =====================================================
//     // NULL SAFETY
//     // =====================================================
//     List<Long> moduleIds =
//             request.getModuleIds() != null
//                     ? request.getModuleIds()
//                     : new ArrayList<>();
//     List<Long> menuIds =
//             request.getMenuIds() != null
//                     ? request.getMenuIds()
//                     : new ArrayList<>();
//     List<Long> subMenuIds =
//             request.getSubMenuIds() != null
//                     ? request.getSubMenuIds()
//                     : new ArrayList<>();
//     // =====================================================
//     // MODULE
//     // =====================================================
//     for (Long moduleId : moduleIds) {
//         Module module = moduleRepository
//                 .findById(moduleId)
//                 .orElseThrow(() ->
//                         new RuntimeException(
//                                 "Module not found: " + moduleId
//                         ));
//         SchoolModuleMapping moduleMapping =
//                 new SchoolModuleMapping();
//         moduleMapping.setSchoolGroupMapping(groupMapping);
//         moduleMapping.setModule(module);
//         groupMapping.getModuleMappings()
//                 .add(moduleMapping);
//         // =================================================
//         // MENU
//         // =================================================
//         for (Long menuId : menuIds) {
//             Menu menu = menuRepository
//                     .findById(menuId)
//                     .orElseThrow(() ->
//                             new RuntimeException(
//                                     "Menu not found: " + menuId
//                             ));
//             // Menu isi module ka hona chahiye
//             if (!menu.getModule()
//                     .getId()
//                     .equals(moduleId)) {
//                 continue;
//             }
//             SchoolMenuMapping menuMapping =
//                     new SchoolMenuMapping();
//             menuMapping.setSchoolModuleMapping(
//                     moduleMapping
//             );
//             menuMapping.setMenu(menu);
//             moduleMapping.getMenuMappings()
//                     .add(menuMapping);
//             // =============================================
//             // SUB MENU
//             // =============================================
//             for (Long subMenuId : subMenuIds) {
//                 SubMenu subMenu =
//                         subMenuRepository
//                                 .findById(subMenuId)
//                                 .orElseThrow(() ->
//                                         new RuntimeException(
//                                                 "SubMenu not found: "
//                                                         + subMenuId
//                                         ));
//                 // SubMenu isi menu ka hona chahiye
//                 if (!subMenu.getMenu()
//                         .getId()
//                         .equals(menuId)) {
//                     continue;
//                 }
//                 SchoolSubMenuMapping subMapping =
//                         new SchoolSubMenuMapping();
//                 subMapping.setSchoolMenuMapping(
//                         menuMapping
//                 );
//                 subMapping.setSubMenu(subMenu);
//                 menuMapping.getSubMenuMappings()
//                         .add(subMapping);
//             }
//         }
//     }
//     // =====================================================
//     // SAVE
//     // =====================================================
//     schoolGroupMappingRepository.save(groupMapping);
// }
//     public SchoolMappingResponse loadMapping(Long schoolId, Long groupId) {
//         SchoolGroupMapping mapping = schoolGroupMappingRepository
//                 .findBySchoolIdAndUserGroupId(schoolId, groupId)
//                 .orElse(null);
//         if (mapping == null) {
//             return new SchoolMappingResponse();
//         }
//         return convertToResponse(mapping);
//     }
//     private SchoolMappingResponse convertToResponse(
//             SchoolGroupMapping mapping) {
//         SchoolMappingResponse response = new SchoolMappingResponse();
//         for (SchoolModuleMapping moduleMapping : mapping.getModuleMappings()) {
//             response.getModuleIds().add(
//                     moduleMapping.getModule().getId());
//             for (SchoolMenuMapping menuMapping
//                     : moduleMapping.getMenuMappings()) {
//                 response.getMenuIds().add(
//                         menuMapping.getMenu().getId());
//                 for (SchoolSubMenuMapping subMapping
//                         : menuMapping.getSubMenuMappings()) {
//                     response.getSubMenuIds().add(
//                             subMapping.getSubMenu().getId());
//                 }
//             }
//         }
//         return response;
//     }
//     public List<SidebarModuleResponse> getSidebar(Long schoolId, Long groupId) {
//         SchoolGroupMapping mapping = schoolGroupMappingRepository
//                 .findBySchoolIdAndUserGroupId(schoolId, groupId)
//                 .orElse(null);
//         if (mapping == null) {
//             return new ArrayList<>();
//         }
//         List<SidebarModuleResponse> sidebar = new ArrayList<>();
//         for (SchoolModuleMapping moduleMapping : mapping.getModuleMappings()) {
//             Module module = moduleMapping.getModule();
//             SidebarModuleResponse moduleDto = new SidebarModuleResponse();
//             moduleDto.setId(module.getId());
//             moduleDto.setLabel(module.getModuleName());
//             moduleDto.setIcon(module.getIcon());
//             moduleDto.setPath(module.getPath());
//             moduleDto.setImage(module.getImage());
//             moduleDto.setSequenceNumber(module.getSequenceNumber());
//             List<SidebarMenuResponse> menus = new ArrayList<>();
//             for (SchoolMenuMapping menuMapping : moduleMapping.getMenuMappings()) {
//                 Menu menu = menuMapping.getMenu();
//                 SidebarMenuResponse menuDto = new SidebarMenuResponse();
//                 menuDto.setId(menu.getId());
//                 menuDto.setLabel(menu.getMenuName());
//                 menuDto.setPath(menu.getMenuUrl());
//                 List<SidebarSubMenuResponse> subMenus = new ArrayList<>();
//                 for (SchoolSubMenuMapping subMapping : menuMapping.getSubMenuMappings()) {
//                     SubMenu sub = subMapping.getSubMenu();
//                     SidebarSubMenuResponse subDto = new SidebarSubMenuResponse();
//                     subDto.setId(sub.getId());
//                     subDto.setLabel(sub.getSubMenuName());
//                     subDto.setPath(sub.getSubMenuUrl());
//                     subMenus.add(subDto);
//                 }
//                 menuDto.setSubSubMenu(subMenus);
//                 menus.add(menuDto);
//             }
//             moduleDto.setSubMenus(menus);
//             sidebar.add(moduleDto);
//         }
//         return sidebar;
//     }
// }

// package com.schoolmanagement.schoolmanagementwebsite.service;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.schoolmanagement.schoolmanagementwebsite.dto.SaveSchoolMappingRequest;
// import com.schoolmanagement.schoolmanagementwebsite.dto.SchoolMappingResponse;
// import com.schoolmanagement.schoolmanagementwebsite.dto.SidebarMenuResponse;
// import com.schoolmanagement.schoolmanagementwebsite.dto.SidebarModuleResponse;
// import com.schoolmanagement.schoolmanagementwebsite.dto.SidebarSubMenuResponse;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Menu;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Module;
// import com.schoolmanagement.schoolmanagementwebsite.entity.School;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolGroupMapping;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolMenuMapping;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolModuleMapping;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SchoolSubMenuMapping;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SubMenu;
// import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroup;
// import com.schoolmanagement.schoolmanagementwebsite.repository.MenuRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.ModuleRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolGroupMappingRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.SubMenuRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.UserGroupRepository;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// @Transactional
// public class SchoolMappingService {

//     private final SchoolRepository schoolRepository;
//     private final UserGroupRepository userGroupRepository;

//     private final ModuleRepository moduleRepository;
//     private final MenuRepository menuRepository;
//     private final SubMenuRepository subMenuRepository;

//     private final SchoolGroupMappingRepository schoolGroupMappingRepository;

//     // =========================================================
//     // SAVE / UPDATE SCHOOL MAPPING
//     // =========================================================
//     @Transactional
//     public void saveMapping(SaveSchoolMappingRequest request) {

//         System.out.println("====================================");
//         System.out.println("SCHOOL ID    : " + request.getSchoolId());
//         System.out.println("GROUP ID     : " + request.getUserGroupId());
//         System.out.println("MODULE IDS   : " + request.getModuleIds());
//         System.out.println("MENU IDS     : " + request.getMenuIds());
//         System.out.println("SUBMENU IDS  : " + request.getSubMenuIds());
//         System.out.println("====================================");

//         // =====================================================
//         // 1. VALIDATE SCHOOL
//         // =====================================================
//         School school = schoolRepository
//                 .findById(request.getSchoolId())
//                 .orElseThrow(()
//                         -> new RuntimeException("School not found"));

//         // =====================================================
//         // 2. VALIDATE USER GROUP
//         // =====================================================
//         UserGroup group = userGroupRepository
//                 .findById(request.getUserGroupId())
//                 .orElseThrow(()
//                         -> new RuntimeException("User Group not found"));

//         // =====================================================
//         // 3. NULL SAFETY
//         // =====================================================
//         List<Long> moduleIds
//                 = request.getModuleIds() != null
//                 ? request.getModuleIds()
//                 : new ArrayList<>();

//         List<Long> menuIds
//                 = request.getMenuIds() != null
//                 ? request.getMenuIds()
//                 : new ArrayList<>();

//         List<Long> subMenuIds
//                 = request.getSubMenuIds() != null
//                 ? request.getSubMenuIds()
//                 : new ArrayList<>();

//         // =====================================================
//         // 4. DELETE OLD MAPPING
//         // =====================================================
//         schoolGroupMappingRepository
//                 .deleteBySchoolIdAndUserGroupId(
//                         request.getSchoolId(),
//                         request.getUserGroupId()
//                 );

//         schoolGroupMappingRepository.flush();

//         // =====================================================
//         // 5. CREATE NEW GROUP MAPPING
//         // =====================================================
//         SchoolGroupMapping groupMapping
//                 = new SchoolGroupMapping();

//         groupMapping.setSchool(school);
//         groupMapping.setUserGroup(group);           
                 
                
//     // =====================================================
//         // 6. MODULE MAPPING
//     // =====================================================
            
//                     duleId : moduleIds)
                    
//                              moduleRepository
//                             Id)
//                     hrow(() ->
//                         new RuntimeException(
//                             ));
                     

//             SchoolModuleMapping moduleMapping =
//                     new SchoolMo
            
//         moduleMapping.setSchoolGroupMapping(
//                     groupMapping
//         );
            
//                     pping.setModule(module);

//                     .add(moduleMapping);
            
            
//             // 7. MENU MAPPING
//         // =================================================
                
//                         nuId : menuIds) {
                        
//                                  Repository
//                                 )
//                         hrow(() ->
//                             new RuntimeException(
//                                 ));
                
                        
//                         isi module ka hona chahiye
//             if (!menu.getModule()
//                         .getId()
//                         .equals(moduleId)) {

//                 }
                         

//                 SchoolMenuMapping menuMapping =
//                         new SchoolMen
                
//             menuMapping.setSchoolModuleMapping(
//                         moduleMapping
//             );
                
//                         ing.setMenu(menu);

//                         .add(menuMapping);
                
                
//                 // 8. SUB MENU MAPPING
//             // =============================================
                    
//                              bMenuId : subMenu
                                    
//                                     =
//                                              y
//                                             uId)
                                            
//                                     new RuntimeException(
//                                                 "SubMenu not found: "
//                                             ));
                    
                            
//                             nu isi menu ka hona chahiye
//                 if (!subMenu.getMenu()
//                             .getId()
//                             .equals(menuId)) {

//                     }
                             

//                     SchoolSubMenuMapping subMapping 
//                             new SchoolS
                    
//                 subMapping.setSchoolMenuMapping(
//                             menuMapping
//                 );
                    
//                             ng.setSubMenu(subMenu);
                
//                     menuMapping.getSubMenuMappings()
//                             .add(subMapping);
//             }
//         }
        
        
//         // 9. SAVE COMPLETE TREE
//     // =====================================================
//         schoolGroupMappingRepository.save(groupMapping);
    

//     System.out.println("School mapping saved successfully.");
// }

//     // LOAD EXISTING MAPPING
//     // =========================================================

//     @Transactional(readOnly = true)
//     public SchoolMappingResponse loadMapping(
//             Long schoolId,
//                   groupId) {

//         SchoolGroupMapping mapping =
//                 schoolGroupMappingRepository
//                         .findBySchoolIdAndUserGroupId(
//                                 schoolId,
//                                 groupId
//                         .orElse(null);


//         if (mapping == null) {

//         }


//     }


//     // CONVERT ENTITY -> RESPONSE
//     // =========================================================

//     private SchoolMappingResponse conv
//                 r olGroupMapping mapping) {

//                 new SchoolMappingResponse();


//                 : mapping.getModuleMappings()) {

//             // MODULE

//             if (moduleMapping.getModule() != null) {

//                 response.getModuleIds()
//                         .add(
//                                 moduleMapping
//                                         .getModule()
//                                         .getId()
//             }

//             // MENU

//                     : moduleMapping.getMenuMappings()) {


//                 if (menuMapping.getMenu() != null) {

//                     response.getMenuIds()
//                             .add(
//                                     menuMapping
//                                             .getMenu()
//                                             .getId()
//                 }

//                 // SUB MENU

//                 for (SchoolSubMenuMapping subMapping
//                                 .getSubMenuMappings()) {


//                     if (subMapping.getSubMenu() != null) {

//                         response.getSubMenuIds()
//                                 .add(
//                                         subMapping
//                                                 .getSubMenu()
//                                                 .getId()
//                                 );
//                     }
//                 }
//         }


//     }


//     // GET SIDEBAR
//     // =========================================================

//     @Transactional(readOnly = true)
//     public List<SidebarModuleResponse> getSidebar(
//             Long groupId) {
                 

//         SchoolGroupMapping mapping =
//                 schoolGroupMappingRepository
//                         .findBySchoolIdAndUserGroupId(
//                                 schoolId,
//                                 groupId
//                         .orElse(null);


//         if (mapping == null) {

//         }
                 

//                 new ArrayList<>();


//         // MODULE
//         // =====================================================

//                 : mapping
//                     g 

//                     moduleMapping.getModule();


//             if (module == null) {
//             }
                     

//                     new SidebarModuleResponse();


//             moduleDto.setId(module.getId());
//             moduleDto.setLabel(module.getModuleName());
//             moduleDto.setIcon(module.getIcon());
//             moduleDto.setPath(module.getPath());
//             moduleDto.setImage(module.getImage());
//             moduleDto.setSequenceNumber(
//             );


//             // MENU
//                      ============================================

//                     new ArrayList<>();


//                     : mod
//                         l 

//                         menuMapping.getMenu();


//                 if (menu == null) {
//                 }
                         

//                         new SidebarMenuResponse();


//                 menuDto.setId(menu.getId());
//                 menuDto.setPath(menu.getMenuUrl());


//                 // SUB MENU
//                          ========================================

//                         new ArrayList<>();


//                 for (SchoolSubMenuMapping subMapping
                               
//                             . 

//                             subMapping.getSubMenu();


//                     if (sub == null) {
//                     }
                             

//                             new SidebarSubMenuResponse();


//                     subDto.setId(sub.getId());
//                     subDto.setLabel(
//                             sub.getSubMenuName()
//                     );
//                     subDto.setPath(
//                     );


//                 }


//                 menuDto.setSubSubMenu(subMenus);

//             }


//             moduleDto.setSubMenus(menus);

//         }



//         return sidebar;
//     }
// }

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
import com.schoolmanagement.schoolmanagementwebsite.repository.MenuRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.ModuleRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolGroupMappingRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.SchoolRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.SubMenuRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserGroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolMappingService {

    private final SchoolRepository schoolRepository;
    private final UserGroupRepository userGroupRepository;

    private final ModuleRepository moduleRepository;
    private final MenuRepository menuRepository;
    private final SubMenuRepository subMenuRepository;

    private final SchoolGroupMappingRepository schoolGroupMappingRepository;


    // =========================================================
    // SAVE / UPDATE SCHOOL MAPPING
    // =========================================================

    public void saveMapping(SaveSchoolMappingRequest request) {

        System.out.println("======================================");
        System.out.println("SCHOOL ID   : " + request.getSchoolId());
        System.out.println("GROUP ID    : " + request.getUserGroupId());
        System.out.println("MODULE IDS  : " + request.getModuleIds());
        System.out.println("MENU IDS    : " + request.getMenuIds());
        System.out.println("SUBMENU IDS : " + request.getSubMenuIds());
        System.out.println("======================================");


        // =====================================================
        // 1. VALIDATE SCHOOL
        // =====================================================

        School school = schoolRepository
                .findById(request.getSchoolId())
                .orElseThrow(() ->
                        new RuntimeException("School not found"));


        // =====================================================
        // 2. VALIDATE USER GROUP
        // =====================================================

        UserGroup group = userGroupRepository
                .findById(request.getUserGroupId())
                .orElseThrow(() ->
                        new RuntimeException("User Group not found"));


        // =====================================================
        // 3. NULL SAFETY
        // =====================================================

        List<Long> moduleIds =
                request.getModuleIds() != null
                        ? request.getModuleIds()
                        : new ArrayList<>();

        List<Long> menuIds =
                request.getMenuIds() != null
                        ? request.getMenuIds()
                        : new ArrayList<>();

        List<Long> subMenuIds =
                request.getSubMenuIds() != null
                        ? request.getSubMenuIds()
                        : new ArrayList<>();


        // =====================================================
        // 4. DELETE OLD MAPPING
        // =====================================================
        //
        // Example:
        //
        // School 1 + Teacher Group
        //
        // Old:
        // Module A
        //   Menu A
        //     Sub A
        //
        // User unchecks Menu A.
        //
        // Old tree completely delete hoga.
        // Neeche selected data fresh create hoga.
        //
        // =====================================================

        schoolGroupMappingRepository
                .deleteBySchoolIdAndUserGroupId(
                        request.getSchoolId(),
                        request.getUserGroupId()
                );

        schoolGroupMappingRepository.flush();


        // =====================================================
        // 5. CREATE NEW GROUP MAPPING
        // =====================================================

        SchoolGroupMapping groupMapping =
                new SchoolGroupMapping();

        groupMapping.setSchool(school);
        groupMapping.setUserGroup(group);


        // =====================================================
        // 6. MODULE MAPPING
        // =====================================================

        for (Long moduleId : moduleIds) {

            Module module = moduleRepository
                    .findById(moduleId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Module not found: " + moduleId
                            ));


            SchoolModuleMapping moduleMapping =
                    new SchoolModuleMapping();

            moduleMapping.setSchoolGroupMapping(
                    groupMapping
            );

            moduleMapping.setModule(module);


            groupMapping
                    .getModuleMappings()
                    .add(moduleMapping);


            // =================================================
            // 7. MENU MAPPING
            // =================================================

            for (Long menuId : menuIds) {

                Menu menu = menuRepository
                        .findById(menuId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Menu not found: " + menuId
                                ));


                // Menu isi module ka hona chahiye

                if (!menu.getModule()
                        .getId()
                        .equals(moduleId)) {

                    continue;
                }


                SchoolMenuMapping menuMapping =
                        new SchoolMenuMapping();

                menuMapping.setSchoolModuleMapping(
                        moduleMapping
                );

                menuMapping.setMenu(menu);


                moduleMapping
                        .getMenuMappings()
                        .add(menuMapping);


                // =============================================
                // 8. SUB MENU MAPPING
                // =============================================

                for (Long subMenuId : subMenuIds) {

                    SubMenu subMenu =
                            subMenuRepository
                                    .findById(subMenuId)
                                    .orElseThrow(() ->
                                            new RuntimeException(
                                                    "SubMenu not found: "
                                                            + subMenuId
                                            ));


                    // SubMenu isi menu ka hona chahiye

                    if (!subMenu.getMenu()
                            .getId()
                            .equals(menuId)) {

                        continue;
                    }


                    SchoolSubMenuMapping subMapping =
                            new SchoolSubMenuMapping();

                    subMapping.setSchoolMenuMapping(
                            menuMapping
                    );

                    subMapping.setSubMenu(subMenu);


                    menuMapping
                            .getSubMenuMappings()
                            .add(subMapping);
                }
            }
        }


        // =====================================================
        // 9. SAVE COMPLETE TREE
        // =====================================================

        schoolGroupMappingRepository.save(groupMapping);


        System.out.println(
                "School mapping saved successfully."
        );
    }


    // =========================================================
    // LOAD EXISTING MAPPING
    // =========================================================

    @Transactional(readOnly = true)
    public SchoolMappingResponse loadMapping(
            Long schoolId,
            Long groupId) {


        SchoolGroupMapping mapping =
                schoolGroupMappingRepository
                        .findBySchoolIdAndUserGroupId(
                                schoolId,
                                groupId
                        )
                        .orElse(null);


        // No mapping found

        if (mapping == null) {

            return new SchoolMappingResponse();
        }


        return convertToResponse(mapping);
    }


    // =========================================================
    // ENTITY -> RESPONSE
    // =========================================================

    private SchoolMappingResponse convertToResponse(
            SchoolGroupMapping mapping) {


        SchoolMappingResponse response =
                new SchoolMappingResponse();


        // =====================================================
        // MODULE
        // =====================================================

        for (SchoolModuleMapping moduleMapping
                : mapping.getModuleMappings()) {


            if (moduleMapping.getModule() == null) {
                continue;
            }


            response.getModuleIds()
                    .add(
                            moduleMapping
                                    .getModule()
                                    .getId()
                    );


            // =================================================
            // MENU
            // =================================================

            for (SchoolMenuMapping menuMapping
                    : moduleMapping.getMenuMappings()) {


                if (menuMapping.getMenu() == null) {
                    continue;
                }


                response.getMenuIds()
                        .add(
                                menuMapping
                                        .getMenu()
                                        .getId()
                        );


                // =============================================
                // SUB MENU
                // =============================================

                for (SchoolSubMenuMapping subMapping
                        : menuMapping
                                .getSubMenuMappings()) {


                    if (subMapping.getSubMenu() == null) {
                        continue;
                    }


                    response.getSubMenuIds()
                            .add(
                                    subMapping
                                            .getSubMenu()
                                            .getId()
                            );
                }
            }
        }


        return response;
    }


    // =========================================================
    // GET SIDEBAR
    // =========================================================

    @Transactional(readOnly = true)
    public List<SidebarModuleResponse> getSidebar(
            Long schoolId,
            Long groupId) {


        SchoolGroupMapping mapping =
                schoolGroupMappingRepository
                        .findBySchoolIdAndUserGroupId(
                                schoolId,
                                groupId
                        )
                        .orElse(null);


        // No mapping

        if (mapping == null) {

            return new ArrayList<>();
        }


        List<SidebarModuleResponse> sidebar =
                new ArrayList<>();


        // =====================================================
        // MODULE LOOP
        // =====================================================

        for (SchoolModuleMapping moduleMapping
                : mapping.getModuleMappings()) {


            Module module =
                    moduleMapping.getModule();


            if (module == null) {
                continue;
            }


            SidebarModuleResponse moduleDto =
                    new SidebarModuleResponse();


            moduleDto.setId(
                    module.getId()
            );

            moduleDto.setLabel(
                    module.getModuleName()
            );

            moduleDto.setIcon(
                    module.getIcon()
            );

            moduleDto.setPath(
                    module.getPath()
            );

            moduleDto.setImage(
                    module.getImage()
            );

            moduleDto.setSequenceNumber(
                    module.getSequenceNumber()
            );


            // =================================================
            // MENU LIST
            // =================================================

            List<SidebarMenuResponse> menus =
                    new ArrayList<>();


            for (SchoolMenuMapping menuMapping
                    : moduleMapping.getMenuMappings()) {


                Menu menu =
                        menuMapping.getMenu();


                if (menu == null) {
                    continue;
                }


                SidebarMenuResponse menuDto =
                        new SidebarMenuResponse();


                menuDto.setId(
                        menu.getId()
                );

                menuDto.setLabel(
                        menu.getMenuName()
                );

                menuDto.setPath(
                        menu.getMenuUrl()
                );


                // =============================================
                // SUB MENU LIST
                // =============================================

                List<SidebarSubMenuResponse> subMenus =
                        new ArrayList<>();


                for (SchoolSubMenuMapping subMapping
                        : menuMapping
                                .getSubMenuMappings()) {


                    SubMenu sub =
                            subMapping.getSubMenu();


                    if (sub == null) {
                        continue;
                    }


                    SidebarSubMenuResponse subDto =
                            new SidebarSubMenuResponse();


                    subDto.setId(
                            sub.getId()
                    );

                    subDto.setLabel(
                            sub.getSubMenuName()
                    );

                    subDto.setPath(
                            sub.getSubMenuUrl()
                    );


                    subMenus.add(subDto);
                }


                menuDto.setSubSubMenu(
                        subMenus
                );


                menus.add(menuDto);
            }


            moduleDto.setSubMenus(
                    menus
            );


            sidebar.add(moduleDto);
        }


        return sidebar;
    }
}