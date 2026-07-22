package com.schoolmanagement.schoolmanagementwebsite.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolmanagement.schoolmanagementwebsite.dto.UserGroupMappingDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.Menu;
import com.schoolmanagement.schoolmanagementwebsite.entity.Module;
import com.schoolmanagement.schoolmanagementwebsite.entity.SubMenu;
import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroup;
import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroupMapping;
import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroupMenuMapping;
import com.schoolmanagement.schoolmanagementwebsite.entity.UserGroupSubMenuMapping;
import com.schoolmanagement.schoolmanagementwebsite.repository.MenuRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.ModuleRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.SubMenuRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserGroupMappingRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserGroupMenuMappingRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserGroupRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.UserGroupSubMenuMappingRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.UserGroupMappingService;

@Service
public class UserGroupMappingServiceImpl implements UserGroupMappingService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private SubMenuRepository subMenuRepository;

    @Autowired
    private UserGroupMappingRepository mappingRepository;

    @Autowired
    private UserGroupMenuMappingRepository menuMappingRepository;

    @Autowired
    private UserGroupSubMenuMappingRepository subMenuMappingRepository;

    @Override
    @Transactional
    public String saveMapping(UserGroupMappingDto dto) {

        UserGroup userGroup = userGroupRepository
                .findById(dto.getUserGroupId())
                .orElseThrow(() -> new RuntimeException("User Group Not Found"));

        Module module = moduleRepository
                .findById(dto.getModuleId())
                .orElseThrow(() -> new RuntimeException("Module Not Found"));

        Optional<UserGroupMapping> existing
                = mappingRepository.findByUserGroupIdAndModuleId(
                        dto.getUserGroupId(),
                        dto.getModuleId());

        UserGroupMapping mapping;

        if (existing.isPresent()) {

            mapping = existing.get();

            menuMappingRepository.deleteByMapping(mapping);
            subMenuMappingRepository.deleteByMapping(mapping);

        } else {

            mapping = new UserGroupMapping();
            mapping.setUserGroup(userGroup);
            mapping.setModule(module);

            mapping = mappingRepository.save(mapping);
        }

        if (dto.getMenuIds() != null) {
            for (Long menuId : dto.getMenuIds()) {

                Menu menu = menuRepository.findById(menuId)
                        .orElseThrow(() -> new RuntimeException("Menu Not Found"));

                UserGroupMenuMapping menuMapping = new UserGroupMenuMapping();
                menuMapping.setMapping(mapping);
                menuMapping.setMenu(menu);

                menuMappingRepository.save(menuMapping);
            }
        }

        if (dto.getSubMenuIds() != null) {
            for (Long subMenuId : dto.getSubMenuIds()) {

                SubMenu subMenu = subMenuRepository.findById(subMenuId)
                        .orElseThrow(() -> new RuntimeException("Sub Menu Not Found"));

                UserGroupSubMenuMapping subMenuMapping
                        = new UserGroupSubMenuMapping();

                subMenuMapping.setMapping(mapping);
                subMenuMapping.setSubMenu(subMenu);

                subMenuMappingRepository.save(subMenuMapping);
            }
        }

        return "User Group Mapping Saved Successfully";
    }

    @Override
    public List<UserGroupMapping> getAllMappings() {
        return mappingRepository.findAll();
    }

    @Override
    public UserGroupMapping getMappingById(Long id) {
        return mappingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mapping Not Found"));
    }

    @Override
    @Transactional
    public void deleteMapping(Long id) {

        UserGroupMapping mapping = mappingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mapping Not Found"));

        menuMappingRepository.deleteByMapping(mapping);
        subMenuMappingRepository.deleteByMapping(mapping);

        mappingRepository.delete(mapping);
    }
}
