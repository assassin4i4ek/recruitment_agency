package application.model.candidate.service;

import application.model.application.Application;
import application.model.candidate.Applicant;
import application.model.candidate.Candidate;
import application.model.candidate.CandidateRegistrationForm;
import application.model.user.User;

import java.util.List;

public interface CandidateService {
    Candidate findCandidateById(int candidateId);
    List<Applicant> findApplicantsForApplication(Application application);
    void reorderApplicantsOfApplication(Application application);
    void updateApplicantOfApplicationStage(Application application, Applicant applicant);
    boolean deleteApplicantsForApplication(Application application);
    void updateCandidateInfo(Candidate candidate);
    void registerNewCandidate(User user, CandidateRegistrationForm form);
    List<Applicant> getPossibleApplicants(Application application);
    List<String> getAvailableProfessionsList();
}
