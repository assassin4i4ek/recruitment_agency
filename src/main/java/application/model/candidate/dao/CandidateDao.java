package application.model.candidate.dao;

import application.model.application.Application;
import application.model.candidate.Applicant;
import application.model.candidate.Candidate;
import application.model.candidate.CandidateRegistrationForm;
import application.model.user.User;

import java.util.List;

public interface CandidateDao {
    Candidate findCandidateById(int candidateId);
    List<Applicant> findApplicantsForApplication(Application application);
    void reorderApplicantsOfApplication(Application application);
    void updateApplicantOfApplicationStage(Application application, Applicant applicant);
    void deleteApplicantsForApplication(Application application);
    void updateCandidateInfo(Candidate candidate);
    void createNewCandidate(User user, CandidateRegistrationForm form);
    List<Candidate> findCandidatesWithApplicationProfession(Application application, double salaryCoef);
}
