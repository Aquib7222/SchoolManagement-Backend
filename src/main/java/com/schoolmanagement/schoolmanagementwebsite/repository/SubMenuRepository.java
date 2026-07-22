package com.schoolmanagement.schoolmanagementwebsite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schoolmanagement.schoolmanagementwebsite.entity.Menu;
import com.schoolmanagement.schoolmanagementwebsite.entity.SubMenu;

@Repository
public interface SubMenuRepository extends JpaRepository<SubMenu, Long> {

    List<SubMenu> findByMenu(Menu menu);

}