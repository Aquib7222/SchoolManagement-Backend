package com.schoolmanagement.schoolmanagementwebsite.dto.Assessment;

public class AssessmentNatureResponse {

    private String name;
    private String shortCode;
    private String description;
    private String status;

    // Constructor
    

    public AssessmentNatureResponse(String name, String shortCode, String description, String status) {
        this.name = name;
        this.shortCode = shortCode;
        this.description = description;
        this.status = status;
    }



    // Getters
    public String getName(){
        return name;
    }
        
    

    public void setName(String name) {
        this.name = name;
    }

   
    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus(){
        return status; 
    }
    public void setStatus(String status){

        this.status = status; 
    }

}
