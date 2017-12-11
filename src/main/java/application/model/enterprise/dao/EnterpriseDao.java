package application.model.enterprise.dao;

import application.model.enterprise.Enterprise;
import application.model.enterprise.EnterpriseRegistrationForm;
import application.model.user.User;

public interface EnterpriseDao {
    Enterprise findEnterpriseById(int enterpriseId);
    void updateEnterpriseInfo(Enterprise enterprise);
    void createNewEnterprise(User user, EnterpriseRegistrationForm form);
}
