package application.model.enterprise;

import application.model.user.UserRegistrationForm;

public class EnterpriseRegistrationForm extends UserRegistrationForm{
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void resetAll() {
        super.resetAll();
        email = name = "";
    }
}
