package application.model.candidate.service;

import application.model.application.Application;
import application.model.application.service.ApplicationService;
import application.model.candidate.Applicant;
import application.model.candidate.Candidate;
import application.model.candidate.CandidateRegistrationForm;
import application.model.candidate.dao.CandidateDao;
import application.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.MapUtils;

import java.util.*;

@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private ApplicationService applicationService;

    @Override
    public Candidate findCandidateById(int candidateId) {
        return candidateDao.findCandidateById(candidateId);
    }

    @Override
    public List<Applicant> findApplicantsForApplication(Application application) {
        return candidateDao.findApplicantsForApplication(application);
    }

    @Override
    public void reorderApplicantsOfApplication(Application application) {
        candidateDao.reorderApplicantsOfApplication(application);
    }

    @Override
    public void updateApplicantOfApplicationStage(Application application, Applicant applicant) {
        candidateDao.updateApplicantOfApplicationStage(application, applicant);
    }

    @Override
    public boolean deleteApplicantsForApplication(Application application) {
        candidateDao.deleteApplicantsForApplication(application);
        return true;
    }

    @Override
    public void updateCandidateInfo(Candidate candidate) {
        candidateDao.updateCandidateInfo(candidate);
    }

    @Override
    public void registerNewCandidate(User user, CandidateRegistrationForm form) {
        candidateDao.createNewCandidate(user, form);
    }

    @Override
    public List<Applicant> getPossibleApplicants(Application application) {
        double salaryCoef = 0.75;
        List<Applicant> candidates = candidateDao.findCandidatesWithApplicationProfession(application, salaryCoef);
        Map<Integer, Integer> resultMap = new HashMap<>();
        String[] demandedSkills = application.getDemandedSkills().split(",\\s*|\\.\\s+|;\\s*|\\n");

        for (Candidate candidate : candidates) {
            int skillMatchIncrement = 2, experienceMatchIncrement = 1;
            int matchLevel = 0;
            if (candidate.getExperience().contains(candidate.getProfession())) {
                matchLevel += experienceMatchIncrement;
            }
            for (String skill : demandedSkills) {
                if (candidate.getSkills().contains(skill)) {
                    matchLevel += skillMatchIncrement;
                }
            }
            resultMap.put(candidate.getId(), matchLevel);
        }

        candidates.sort(Comparator.comparingInt(a -> resultMap.get(a.getId())));
        return candidates;
    }

    @Override
    public List<String> getAvailableProfessionsList() {
        return applicationService.getAvailableProfessionsList();
    }
}
