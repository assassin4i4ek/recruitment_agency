package application.model.application;

public class ApplicationRegistrationForm {
    private String profession;
    private String quantity;
    private EmploymentType employmentType = EmploymentType.FULL_TIME;
    private String salaryCuPerMonth;
    private String demandedSkills;

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public String getSalaryCuPerMonth() {
        return salaryCuPerMonth;
    }

    public void setSalaryCuPerMonth(String salaryCuPerMonth) {
        this.salaryCuPerMonth = salaryCuPerMonth;
    }

    public String getDemandedSkills() {
        return demandedSkills;
    }

    public void setDemandedSkills(String demandedSkills) {
        this.demandedSkills = demandedSkills;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void resetAll() {
        employmentType = EmploymentType.FULL_TIME;
        quantity = profession = salaryCuPerMonth = demandedSkills = "";
    }
}
