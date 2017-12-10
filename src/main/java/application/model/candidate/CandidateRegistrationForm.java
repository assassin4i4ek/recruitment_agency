package application.model.candidate;

import application.model.user.UserRegistrationForm;

public class CandidateRegistrationForm extends UserRegistrationForm {
    private String email;
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void resetAll() {
        super.resetAll();
        name = email = "";
    }
}
