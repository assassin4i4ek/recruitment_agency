package application.model.agent.service;

import application.model.agent.Agent;
import application.model.application.Application;
import application.model.application.ApplicationRegistrationForm;
import application.model.candidate.Applicant;
import application.model.candidate.Candidate;
import application.model.enterprise.Enterprise;

import java.util.List;

public interface AgentService {
    Agent findAgentById(int agentId);
    void reorderApplicationsOfAgent(Agent agent);
    Enterprise findEnterpriseOfApplication(Application application);
    void updateApplicationInfo(Application application);
    void reorderApplicantsOfApplication(Application application);
    void updateApplicantOfApplicationStage(Application application, Applicant applicant);
    void updateApplicationCollapsed(Application application);
    void updateApplicationCollapsedApplicants(Application application);
    boolean finalizeApplication(Application application);
    void updateEnterpriseInfo(Enterprise enterprise);
    void updateCandidateInfo(Candidate candidate);
    int getAppropriateAgentIdForApplication(ApplicationRegistrationForm form);
    boolean validateProfession(String profession);
    void checkForUpdates(Agent agent);
    List<Applicant> getPossibleApplicants(Application application);
}
