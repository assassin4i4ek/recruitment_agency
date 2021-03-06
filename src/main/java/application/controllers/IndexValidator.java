package application.controllers;

import application.model.application.Application;
import application.model.candidate.Applicant;
import application.model.candidate.Candidate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IndexValidator {
    public boolean validateApplicationIndexes(List<Application> applications, int appIndex) {
        return applications != null && 0 <= appIndex && appIndex < applications.size();
    }

    public boolean validateApplicationIndexes(List<Application> applications, int prevAppIndex, int newAppIndex) {
        return applications != null && 0 <= prevAppIndex && prevAppIndex < applications.size() &&
                0 <= newAppIndex && newAppIndex < applications.size();
    }

    public boolean validateApplicantsIndexes(List<Applicant> applicants,
                                             int applicantIndex) {
        return applicants != null && 0 <= applicantIndex && applicantIndex < applicants.size();
    }

    public boolean validateApplicantsIndexes(List<Applicant> applicants,
                                             int prevApplicantIndex, int newApplicantIndex) {
        return applicants != null && 0 <= prevApplicantIndex && prevApplicantIndex < applicants.size() &&
                0 <= newApplicantIndex && newApplicantIndex < applicants.size();
    }
}
