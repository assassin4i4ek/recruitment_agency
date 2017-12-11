package application.model.enterprise.serivice;

import application.model.application.Application;
import application.model.application.ApplicationRegistrationForm;
import application.model.enterprise.Enterprise;
import application.model.enterprise.EnterpriseRegistrationForm;
import application.model.user.User;

public interface EnterpriseService {
    Enterprise findEnterpriseById(int enterpriseId);
    void updateEnterpriseInfo(Enterprise enterprise);
    void registerNewEnterprise(User user, EnterpriseRegistrationForm form);
    void reorderApplicationsOfEnterprise(Enterprise enterprise);
    boolean validateProfession(String profession);
    boolean validateQuantity(String quantity);
    void updateEnterpriseApplicationInfo(Application application);
    void updateApplicationCollapsed(Application application);
    void checkForUpdates(Enterprise enterprise);
    void registerNewApplication(User user, ApplicationRegistrationForm form);
}
