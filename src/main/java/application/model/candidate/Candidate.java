package application.model.candidate;

import application.model.application.EmploymentType;

public class Candidate {
    private int id;
    private String name;
    private String email;
    private String profession;
    private int requiredSalaryCuPerMonth;
    private EmploymentType employmentType;
    private String experience;
    private String skills;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRequiredSalaryCuPerMonth() {
        return requiredSalaryCuPerMonth;
    }

    public void setRequiredSalaryCuPerMonth(int requiredSalaryCuPerMonth) {
        this.requiredSalaryCuPerMonth = requiredSalaryCuPerMonth;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
