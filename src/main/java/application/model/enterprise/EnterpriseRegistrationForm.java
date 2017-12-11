package application.model.enterprise;

import application.model.user.UserRegistrationForm;

public class EnterpriseRegistrationForm extends UserRegistrationForm{
    private String name;
    private String email;
    private String contactPersonName;

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

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    @Override
    public void resetAll() {
        super.resetAll();
        email = name = "";
    }
}
