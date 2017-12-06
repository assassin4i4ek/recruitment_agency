package application.model.candidate.service;

import application.model.application.Application;
import application.model.candidate.Applicant;
import application.model.candidate.Candidate;
import application.model.candidate.dao.CandidateDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
}
