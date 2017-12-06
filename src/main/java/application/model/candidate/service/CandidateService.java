package application.model.candidate.service;

import application.model.application.Application;
import application.model.candidate.Applicant;
import application.model.candidate.Candidate;

import java.util.List;

public interface CandidateService {
    Candidate findCandidateById(int candidateId);
    List<Applicant> findApplicantsForApplication(Application application);
}