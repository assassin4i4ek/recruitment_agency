package application.model.agent.service;

import application.model.agent.Agent;
import application.model.agent.dao.AgentDao;
import application.model.application.Application;
import application.model.application.ApplicationRegistrationForm;
import application.model.application.service.ApplicationService;
import application.model.candidate.Applicant;
import application.model.candidate.Candidate;
import application.model.candidate.service.CandidateService;
import application.model.enterprise.Enterprise;
import application.model.enterprise.serivice.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private AgentDao agentDao;

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
        applicationService.updateAgentApplicationInfo(application);
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
        applicationService.updateApplicationAgentCollapsed(application);
    }

    @Override
    public void updateApplicationCollapsedApplicants(Application application) {
        applicationService.updateApplicationCollapsedApplicants(application);
    }

    @Override
    public boolean finalizeApplication(Application application) {
        if (applicationService.finalizeApplication(application)) {
            agentDao.increaseAgentsLevel(application);
            return true;
        }
        return false;
    }

    @Override
    public void updateEnterpriseInfo(Enterprise enterprise) {
        enterpriseService.updateEnterpriseInfo(enterprise);
    }

    @Override
    public void updateCandidateInfo(Candidate candidate) {
        candidateService.updateCandidateInfo(candidate);
    }

    @Override
    public int getAppropriateAgentIdForApplication(ApplicationRegistrationForm form) {
        Map<Integer, Long> amounts = applicationService.listAgentIdsAndApplicationAmounts();
        List<Integer> skillsInSphereOrderedByLevel = agentDao.getAllAgentsSkillsInSphereSortedByLevel(form.getProfession());
        int amountThreshold = 3;
        int agentIdWithMinCongestion = skillsInSphereOrderedByLevel.get(0);
        for (Integer agentId : skillsInSphereOrderedByLevel) {
            if (amounts.get(agentId) <= amountThreshold) {
                return agentId;
            }
            else if (amounts.get(agentId) <= amounts.get(agentIdWithMinCongestion)) {
                agentIdWithMinCongestion = agentId;
            }
        }

        return agentIdWithMinCongestion;
    }

    @Override
    public boolean validateProfession(String profession) {
        return applicationService.validateProfession(profession);
    }

    @Override
    public void checkForUpdates(Agent agent) {
        agent.setApplications(applicationService.findApplicationsByAgentId(agent.getId()));
    }

    @Override
    public List<Applicant> getPossibleApplicants(Application application) {
        return candidateService.getPossibleApplicants(application);
    }

    @Override
    public boolean validateSalary(String salaryCuPerMonth) {
        return applicationService.validateSalary(salaryCuPerMonth);
    }


}
