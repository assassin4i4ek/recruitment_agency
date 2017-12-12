package application.controllers.parameters;

public class CandidateRequestParameter {
    private boolean edit = false;
    private boolean salaryError = false;
    private boolean professionError = false;

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isSalaryError() {
        return salaryError;
    }

    public void setSalaryError(boolean salaryError) {
        this.salaryError = salaryError;
    }

    public boolean isProfessionError() {
        return professionError;
    }

    public void setProfessionError(boolean professionError) {
        this.professionError = professionError;
    }
}
