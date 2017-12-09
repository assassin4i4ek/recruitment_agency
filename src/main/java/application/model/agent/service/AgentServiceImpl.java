package application.model.agent.service;

import application.model.agent.Agent;
import application.model.application.Application;
import application.model.application.service.ApplicationService;
import application.model.candidate.Applicant;
import application.model.candidate.service.CandidateService;
import application.model.enterprise.Enterprise;
import application.model.enterprise.serivice.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private CandidateService candidateService;

    @Override
    public Agent findAgentById(int agentId) {
        Agent agent = new Agent();
        agent.setId(agentId);
        agent.setApplications(applicationService.findApplicationsByAgentId(agentId));
        return agent;
    }

    @Override
    public void reorderApplicationsOfAgent(Agent agent) {
        applicationService.reorderApplicationsOfAgent(agent.getApplications());
    }

    @Override
    public Enterprise findEnterpriseOfApplication(Application application) {
        return enterpriseService.findEnterpriseById(application.getEnterpriseId());
    }

    @Override
    public void updateApplicationInfo(Application application) {
        applicationService.updateApplicationInfo(application);
    }

    @Override
    public void reorderApplicantsOfApplication(Application application) {
        applicationService.reorderApplicantsOfApplication(application);
    }

    @Override
    public void updateApplicantOfApplicationStage(Application application, Applicant applicant) {
        candidateService.updateApplicantOfApplicationStage(application, applicant);
    }

    @Override
    public void updateApplicationCollapsed(Application application) {
        applicationService.updateApplicationCollapsed(application);
    }

    @Override
    public void updateApplicationCollapsedApplicants(Application application) {
        applicationService.updateApplicationCollapsedApplicants(application);
    }


}
