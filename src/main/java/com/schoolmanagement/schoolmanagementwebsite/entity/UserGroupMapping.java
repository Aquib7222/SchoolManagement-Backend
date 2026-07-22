// package com.schoolmanagement.schoolmanagementwebsite.entity;

// import jakarta.persistence.*;

// @Entity
// @Table(
//     name = "user_group_mapping",
//     uniqueConstraints = {
//         @UniqueConstraint(columnNames = {"user_group_id", "module_id"})
//     }
// )
// public class UserGroupMapping {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne
//     @JoinColumn(name = "user_group_id")
//     private UserGroup userGroup;

//     @ManyToOne
//     @JoinColumn(name = "module_id")
//     private Module module;

//     public UserGroupMapping() {
//     }

//     public Long getId() {
//         return id;
//     }

//     public UserGroup getUserGroup() {
//         return userGroup;
//     }

//     public void setUserGroup(UserGroup userGroup) {
//         this.userGroup = userGroup;
//     }

//     public Module getModule() {
//         return module;
//     }

//     public void setModule(Module module) {
//         this.module = module;
//     }
// }

package com.schoolmanagement.schoolmanagementwebsite.entity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(
    name = "user_group_mapping",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_group_id", "module_id"})
    }
)
public class UserGroupMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @OneToMany(
    mappedBy = "mapping",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
)
@JsonManagedReference
private Set<UserGroupMenuMapping> menuMappings = new LinkedHashSet<>();


@OneToMany(
    mappedBy = "mapping",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
)
@JsonManagedReference
private Set<UserGroupSubMenuMapping> subMenuMappings = new LinkedHashSet<>();

    public UserGroupMapping() {
    }

    public Long getId() {
        return id;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Set<UserGroupMenuMapping> getMenuMappings() {
        return menuMappings;
    }

    public void setMenuMappings(Set<UserGroupMenuMapping> menuMappings) {
        this.menuMappings = menuMappings;
    }

    public Set<UserGroupSubMenuMapping> getSubMenuMappings() {
        return subMenuMappings;
    }

    public void setSubMenuMappings(Set<UserGroupSubMenuMapping> subMenuMappings) {
        this.subMenuMappings = subMenuMappings;
    }
}