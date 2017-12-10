package application.model.enterprise.serivice;

import application.model.enterprise.Enterprise;
import application.model.enterprise.EnterpriseRegistrationForm;
import application.model.user.User;

public interface EnterpriseService {
    Enterprise findEnterpriseById(int enterpriseId);
    String findEnterpriseNameById(int enterpriseId);
    void updateEnterpriseInfo(Enterprise enterprise);
    void registerNewEnterprise(User user, EnterpriseRegistrationForm form);
}
