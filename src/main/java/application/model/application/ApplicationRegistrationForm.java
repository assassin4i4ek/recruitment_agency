package application.model.application;

public class ApplicationRegistrationForm {
    private String profession;
    private String quantity;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void resetAll() {
        quantity = profession = "";
    }
}
