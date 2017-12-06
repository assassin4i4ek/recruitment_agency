package application.model.candidate.dao;

import application.model.application.Application;
import application.model.candidate.Applicant;
import application.model.candidate.Candidate;

import java.util.List;

public interface CandidateDao {
    Candidate findCandidateById(int candidateId);
    List<Applicant> findApplicantsForApplication(Application application);
}
