package application.controllers.parameters;

public class RegisterRequestParameter {
    private boolean usernameError = false;
    private boolean emailError = false;
    private boolean passwordError = false;
    private boolean success = false;
    private boolean professionError = false;
    private boolean quantityError = false;
    private boolean salaryError;

    public boolean isQuantityError() {
        return quantityError;
    }

    public void setQuantityError(boolean quantityError) {
        this.quantityError = quantityError;
    }

    public boolean isProfessionError() {
        return professionError;
    }

    public void setProfessionError(boolean professionError) {
        this.professionError = professionError;
    }

    public boolean isPasswordError() {
        return passwordError;
    }

    public void setPasswordError(boolean passwordError) {
        this.passwordError = passwordError;
    }

    public boolean isUsernameError() {
        return usernameError;
    }

    public void setUsernameError(boolean usernameError) {
        this.usernameError = usernameError;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isEmailError() {
        return emailError;
    }

    public void setEmailError(boolean emailError) {
        this.emailError = emailError;
    }

    public void setSalaryError(boolean salaryError) {
        this.salaryError = salaryError;
    }

    public boolean isSalaryError() {
        return salaryError;
    }
}
