package application.model.application;

import application.model.candidate.Applicant;
import application.model.candidate.Candidate;
import application.model.enterprise.Enterprise;

import java.sql.Timestamp;
import java.util.List;

public class Application {
    private int id;
    private int agentId;
    private int enterpriseId;
    private Timestamp registrationTimestamp;
    private String profession;
    private short quantity;
    private String agentNote;
    private List<Applicant> applicants;
    private boolean agentCollapsed;
    private boolean agentCollapsedApplicants;
    private boolean enterpriseCollapsed;
    private Enterprise enterprise;
    private EmploymentType employmentType;
    private int salaryCuPerMonth;
    private String demandedSkills;
    private List<Applicant> possibleApplicants;

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public int getSalaryCuPerMonth() {
        return salaryCuPerMonth;
    }

    public void setSalaryCuPerMonth(int salaryCuPerMonth) {
        this.salaryCuPerMonth = salaryCuPerMonth;
    }

    public String getDemandedSkills() {
        return demandedSkills;
    }

    public void setDemandedSkills(String demandedSkills) {
        this.demandedSkills = demandedSkills;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public Timestamp getRegistrationTimestamp() {
        return registrationTimestamp;
    }

    public void setRegistrationTimestamp(Timestamp registrationDate) {
        this.registrationTimestamp = registrationDate;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public String getAgentNote() {
        return agentNote;
    }

    public void setAgentNote(String agentNote) {
        this.agentNote = agentNote;
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Applicant> applicants) {
        this.applicants = applicants;
    }

    public boolean isAgentCollapsed() {
        return agentCollapsed;
    }

    public void setAgentCollapsed(boolean agentCollapsed) {
        this.agentCollapsed = agentCollapsed;
    }

    public boolean isAgentCollapsedApplicants() {
        return agentCollapsedApplicants;
    }

    public void setAgentCollapsedApplicants(boolean agentCollapsedApplicants) {
        this.agentCollapsedApplicants = agentCollapsedApplicants;
    }

    public boolean isEnterpriseCollapsed() {
        return enterpriseCollapsed;
    }

    public void setEnterpriseCollapsed(boolean enterpriseCollapsed) {
        this.enterpriseCollapsed = enterpriseCollapsed;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public int getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public List<Applicant> getPossibleApplicants() {
        return possibleApplicants;
    }

    public void setPossibleApplicants(List<Applicant> possibleApplicants) {
        this.possibleApplicants = possibleApplicants;
    }
}
