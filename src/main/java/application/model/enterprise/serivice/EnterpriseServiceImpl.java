package application.model.enterprise.serivice;

import application.model.enterprise.Enterprise;
import application.model.enterprise.EnterpriseRegistrationForm;
import application.model.enterprise.dao.EnterpriseDao;
import application.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    @Autowired
    private EnterpriseDao enterpriseDao;

    @Override
    public Enterprise findEnterpriseById(int enterpriseId) {
        return enterpriseDao.findEnterpriseById(enterpriseId);
    }

    @Override
    public String findEnterpriseNameById(int enterpriseId) {
        return enterpriseDao.findEnterpriseNameById(enterpriseId);
    }

    @Override
    public void updateEnterpriseInfo(Enterprise enterprise) {
        enterpriseDao.updateEnterpriseInfo(enterprise);
    }

    @Override
    public void registerNewEnterprise(User user, EnterpriseRegistrationForm form) {
        enterpriseDao.createNewEnterprise(user, form);
    }
}
