package application.model.agent;

import application.model.application.Application;

import java.util.List;

public class Agent {
    private int id;
    private List<Application> applications;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
