package com.schoolmanagement.schoolmanagementwebsite.service;

import java.util.List;

import com.schoolmanagement.schoolmanagementwebsite.dto.MenuDto;
import com.schoolmanagement.schoolmanagementwebsite.entity.Menu;

public interface MenuService {

    String save(MenuDto dto);

    List<Menu> getAllMenus();

    List<Menu> getMenusByModule(Long moduleId);

    Menu getMenuById(Long id);

    String updateMenu(Long id, MenuDto dto);

    String deleteMenu(Long id);

}