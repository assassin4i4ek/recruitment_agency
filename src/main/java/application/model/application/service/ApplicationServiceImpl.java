package application.model.application.service;

import application.model.application.Application;
import application.model.application.dao.ApplicationDao;
import application.model.candidate.Applicant;
import application.model.candidate.ApplicantStage;
import application.model.candidate.service.CandidateService;
import application.model.enterprise.serivice.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService{
    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Override
    public List<Application> findApplicationsByAgentId(int agentId) {
        List<Application> applications = applicationDao.findApplicationsByAgentId(agentId);
        for (Application application : applications) {
            application.setApplicants(candidateService.findApplicantsForApplication(application));
            application.setEnterpriseName(enterpriseService.findEnterpriseNameById(application.getEnterpriseId()));
        }
        return applications;
    }

    @Override
    public void reorderApplicationsOfAgent(List<Application> applications) {
        applicationDao.reorderApplicationsOfAgent(applications);
    }

    @Override
    public void updateApplicationInfo(Application application) {
        applicationDao.updateApplication(application);
    }

    @Override
    public void reorderApplicantsOfApplication(Application application) {
        candidateService.reorderApplicantsOfApplication(application);
    }

    @Override
    public void updateApplicationCollapsed(Application application) {
        applicationDao.updateApplicationCollapsed(application);
    }

    @Override
    public void updateApplicationCollapsedApplicants(Application application) {
        applicationDao.updateApplicationCollapsedApplicants(application);
    }

    @Override
    public boolean finalizeApplication(Application application) {
        int workersRequired = application.getQuantity();
        int applicantsGotJobCounter = 0;
        for (Applicant applicant : application.getApplicants()) {
            if (applicant.getApplicantStage() == ApplicantStage.GOT_JOB) {
                ++applicantsGotJobCounter;
            }
        }
        if (applicantsGotJobCounter == workersRequired) {
            if (candidateService.deleteApplicantsForApplication(application)) {
                applicationDao.deleteApplication(application);
                return true;
            }
        }

        return false;
    }
}
