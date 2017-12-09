package application.model.application;

import application.model.candidate.Applicant;

import java.sql.Timestamp;
import java.util.List;

public class Application {
    private int id;
    private int enterpriseId;
    private int agentId;
    private Timestamp registrationTimestamp;
    private String profession;
    private short quantity;
    private String agentNote;
    private List<Applicant> applicants;
    private String enterpriseName;
    private boolean agentCollapsed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
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

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public boolean isAgentCollapsed() {
        return agentCollapsed;
    }

    public void setAgentCollapsed(boolean agentCollapsed) {
        this.agentCollapsed = agentCollapsed;
    }
}
