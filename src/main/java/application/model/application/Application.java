package application.model.application;

import java.sql.Date;
import java.sql.Timestamp;

public class Application {
    private int id;
    private int enterpriseId;
    private int agentId;
    private Timestamp registrationDate;
    private String profession;
    private short quantity;
    private String agentNote;

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
        return registrationDate;
    }

    public void setRegistrationTimestamp(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
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
}
