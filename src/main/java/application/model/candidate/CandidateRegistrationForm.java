package application.model.candidate;

import application.model.application.EmploymentType;
import application.model.user.UserRegistrationForm;

public class CandidateRegistrationForm extends UserRegistrationForm {
    private String email;
    private String name;
    private String profession;
    private String employmentType = "NOT_IMPORTANT";
    private String requiredSalaryCuPerMonth;
    private String experience;
    private String skills;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void resetAll() {
        super.resetAll();
        name = email = employmentType = requiredSalaryCuPerMonth = experience = skills = "";
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getRequiredSalaryCuPerMonth() {
        return requiredSalaryCuPerMonth;
    }

    public void setRequiredSalaryCuPerMonth(String requiredSalaryCuPerMonth) {
        this.requiredSalaryCuPerMonth = requiredSalaryCuPerMonth;
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
