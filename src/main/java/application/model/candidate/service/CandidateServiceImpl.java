package application.model.candidate.service;

import application.model.application.Application;
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
    public List<Candidate> getPossibleApplicants(Application application) {
        double salaryCoef = 0.75;
        List<Candidate> candidates = candidateDao.findCandidatesWithApplicationProfession(application, salaryCoef);
        //у кандидатов с указанными профессиями изначальную прибавку к итоговому коеффициенту?
        Map<Integer, Integer> resultMap = new HashMap<>();
        String[] demandedSkills = application.getDemandedSkills().split(",\\s*|\\.\\s+|;\\s*|\\n");
        for (Candidate candidate : candidates) {
            int matchLevel = 0;
            for (String skill : demandedSkills) {
                if (candidate.getSkills().contains(skill)) {
                    ++matchLevel;
                }
            }
            resultMap.put(candidate.getId(), matchLevel);
        }

        Collections.sort(candidates, Comparator.comparingInt(a -> resultMap.get(a.getId())));
        return candidates;
    }
}
