package com.kaushikmanivannan.hireandseek.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public class JobListingDTO {

    private Long id;

    @NotBlank(message = "Job Title is required")
    @Size(max = 100, message = "Title cannot contain more than 100 characters")
    private String title;

    @NotBlank(message = "Job Description is required")
    private String description;

    @Min(0)
    @NotNull(message= "Salary is required")
    @Range(min = 1)
    private Integer salary;

    @NotBlank(message = "Job Location is required")
    private String location;

    @NotBlank(message = "Company Name is required")
    private String companyName;

    public JobListingDTO() {
    }

    public JobListingDTO(Long id, String title, String description, Integer salary, String location, String companyName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.location = location;
        this.companyName = companyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
