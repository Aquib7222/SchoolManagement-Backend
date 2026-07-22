package com.schoolmanagement.schoolmanagementwebsite.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_group")
public class UserGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;
    private String groupCode;
    private String status;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getGroupCode() {
        return groupCode;
    }
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}



