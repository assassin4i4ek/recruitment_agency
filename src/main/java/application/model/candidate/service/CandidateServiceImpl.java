package application.model.candidate.service;

import application.model.application.Application;
import application.model.candidate.Applicant;
import application.model.candidate.Candidate;
import application.model.candidate.dao.CandidateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
