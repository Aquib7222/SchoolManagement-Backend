// package com.schoolmanagement.schoolmanagementwebsite.ServiceImpl;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.schoolmanagement.schoolmanagementwebsite.dto.MenuDto;
// import com.schoolmanagement.schoolmanagementwebsite.dto.SubMenuDto;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Menu;
// import com.schoolmanagement.schoolmanagementwebsite.entity.Module;
// import com.schoolmanagement.schoolmanagementwebsite.entity.SubMenu;
// import com.schoolmanagement.schoolmanagementwebsite.repository.MenuRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.ModuleRepository;
// import com.schoolmanagement.schoolmanagementwebsite.repository.SubMenuRepository;
// import com.schoolmanagement.schoolmanagementwebsite.service.MenuService;

// @Service
// public class MenuServiceImpl implements MenuService {

//     @Autowired
//     private MenuRepository menuRepository;

//     @Autowired
//     private ModuleRepository moduleRepository;

//     @Autowired
//     private SubMenuRepository subMenuRepository;

//     @Override
//     public String save(MenuDto dto) {

//         Module module = moduleRepository.findById(dto.getModuleId())
//                 .orElseThrow(() -> new RuntimeException("Module not found"));

//         Menu menu = new Menu();

//         menu.setModule(module);
//         menu.setMenuName(dto.getMenuName());
//         menu.setMenuUrl(dto.getMenuUrl());
//         menu.setMenuIcon(dto.getMenuIcon());
//         menu.setDisplayOrder(dto.getDisplayOrder());
//         menu.setStatus(dto.getStatus());
//         menu.setHasSubMenu(dto.getHasSubMenu());

//         List<SubMenu> subMenuList = new ArrayList<>();

//         if (Boolean.TRUE.equals(dto.getHasSubMenu())
//                 && dto.getSubMenus() != null
//                 && !dto.getSubMenus().isEmpty()) {

//             for (SubMenuDto subDto : dto.getSubMenus()) {

//                 SubMenu subMenu = new SubMenu();

//                 subMenu.setMenu(menu);
//                 subMenu.setSubMenuName(subDto.getSubMenuName());
//                 subMenu.setSubMenuUrl(subDto.getSubMenuUrl());
//                 subMenu.setSubMenuIcon(subDto.getSubMenuIcon());
//                 subMenu.setDisplayOrder(subDto.getDisplayOrder());

//                 subMenuList.add(subMenu);
//             }
//         }

//         menu.setSubMenus(subMenuList);

//         menuRepository.save(menu);

//         return "Menu Created Successfully";
//     }

//     @Override
//     public List<Menu> getAllMenus() {

//         return menuRepository.findAll();

//     }

//     @Override
//     public Menu getMenuById(Long id) {

//         return menuRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Menu not found"));

//     }

//     @Override
//     public List<Menu> getMenusByModule(Long moduleId) {

//         Module module = moduleRepository.findById(moduleId)
//                 .orElseThrow(() -> new RuntimeException("Module not found"));

//         return menuRepository.findByModule(module);

//     }

//     @Override
//     public String updateMenu(Long id, MenuDto dto) {

//         throw new UnsupportedOperationException(
//                 "Update API will be implemented in next part");

//     }

//     @Override
//     public String deleteMenu(Long id) {

//         throw new UnsupportedOperationException(
//                 "Delete API will be implemented in next part");

//     }

// }


package com.schoolmanagement.schoolmanagementwebsite.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.schoolmanagement.schoolmanagementwebsite.dto.MenuDto;
import com.schoolmanagement.schoolmanagementwebsite.dto.SubMenuDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.Menu;
import com.schoolmanagement.schoolmanagementwebsite.entity.Module;
import com.schoolmanagement.schoolmanagementwebsite.entity.SubMenu;
import com.schoolmanagement.schoolmanagementwebsite.repository.MenuRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.ModuleRepository;
import com.schoolmanagement.schoolmanagementwebsite.repository.SubMenuRepository;
import com.schoolmanagement.schoolmanagementwebsite.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private SubMenuRepository subMenuRepository;

    @Override
    public String save(MenuDto dto) {

        Module module = moduleRepository.findById(dto.getModuleId())
                .orElseThrow(() -> new RuntimeException("Module not found"));

        Menu menu = new Menu();

        menu.setModule(module);
        menu.setMenuName(dto.getMenuName());
        menu.setMenuUrl(dto.getMenuUrl());
        menu.setMenuIcon(dto.getMenuIcon());
        menu.setDisplayOrder(dto.getDisplayOrder());
        menu.setStatus(dto.getStatus());
        menu.setHasSubMenu(dto.getHasSubMenu());

        List<SubMenu> subMenuList = new ArrayList<>();

        if (Boolean.TRUE.equals(dto.getHasSubMenu())
                && dto.getSubMenus() != null
                && !dto.getSubMenus().isEmpty()) {

            for (SubMenuDto subDto : dto.getSubMenus()) {

                SubMenu subMenu = new SubMenu();

                subMenu.setMenu(menu);
                subMenu.setSubMenuName(subDto.getSubMenuName());
                subMenu.setSubMenuUrl(subDto.getSubMenuUrl());
                subMenu.setSubMenuIcon(subDto.getSubMenuIcon());
                subMenu.setDisplayOrder(subDto.getDisplayOrder());

                subMenuList.add(subMenu);
            }
        }

        menu.setSubMenus(subMenuList);

        menuRepository.save(menu);

        return "Menu Created Successfully";
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu getMenuById(Long id) {

        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

    }

    @Override
    public List<Menu> getMenusByModule(Long moduleId) {

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        return menuRepository.findByModule(module);

    }

    // ============================================================
    // UPDATE MENU
    // ============================================================

  @Override
@Transactional
public String updateMenu(Long id, MenuDto dto) {

    Menu menu = menuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Menu not found"));

    Module module = moduleRepository.findById(dto.getModuleId())
            .orElseThrow(() -> new RuntimeException("Module not found"));

    // ==============================
    // UPDATE MENU
    // ==============================

    menu.setModule(module);
    menu.setMenuName(dto.getMenuName());
    menu.setMenuUrl(dto.getMenuUrl());
    menu.setMenuIcon(dto.getMenuIcon());
    menu.setDisplayOrder(dto.getDisplayOrder());
    menu.setStatus(dto.getStatus());
    menu.setHasSubMenu(dto.getHasSubMenu());

    // ==============================
    // UPDATE EXISTING SUB MENUS ONLY
    // ==============================

    if (Boolean.TRUE.equals(dto.getHasSubMenu())
            && dto.getSubMenus() != null) {

        List<SubMenu> existingSubMenus = menu.getSubMenus();

        if (existingSubMenus != null) {

            for (int i = 0; i < dto.getSubMenus().size(); i++) {

                // Sirf existing submenu update hoga
                if (i < existingSubMenus.size()) {

                    SubMenuDto subDto = dto.getSubMenus().get(i);

                    SubMenu subMenu = existingSubMenus.get(i);

                    subMenu.setSubMenuName(subDto.getSubMenuName());
                    subMenu.setSubMenuUrl(subDto.getSubMenuUrl());
                    subMenu.setSubMenuIcon(subDto.getSubMenuIcon());
                    subMenu.setDisplayOrder(subDto.getDisplayOrder());
                }
            }
        }
    }

    /*
     * IMPORTANT:
     *
     * Existing submenu ko delete nahi karna.
     * clear(), remove(), orphan removal kuch nahi karna.
     *
     * New submenu bhi create nahi karna.
     */

    menuRepository.save(menu);

    return "Menu Updated Successfully";
}

    // ============================================================
    // DELETE MENU
    // ============================================================

    @Override
    @Transactional
    public String deleteMenu(Long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Menu not found with id: " + id));

        // Delete submenus first
        if (menu.getSubMenus() != null
                && !menu.getSubMenus().isEmpty()) {

            subMenuRepository.deleteAll(menu.getSubMenus());
        }

        // Delete menu
        menuRepository.delete(menu);

        return "Menu Deleted Successfully";
    }
}