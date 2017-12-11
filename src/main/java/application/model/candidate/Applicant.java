package application.model.candidate;

public class Applicant extends Candidate {
    private ApplicantStage applicantStage = ApplicantStage.NOT_CONSIDERED;

    public ApplicantStage getApplicantStage() {
        return applicantStage;
    }

    public void setApplicantStage(ApplicantStage applicantStage) {
        this.applicantStage = applicantStage;
    }
}
