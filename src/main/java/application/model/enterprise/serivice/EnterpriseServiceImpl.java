package application.model.enterprise.serivice;

import application.model.application.Application;
import application.model.application.ApplicationRegistrationForm;
import application.model.application.service.ApplicationService;
import application.model.enterprise.Enterprise;
import application.model.enterprise.EnterpriseRegistrationForm;
import application.model.enterprise.dao.EnterpriseDao;
import application.model.user.User;
import application.model.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserDetailsService userService;

    @Override
    public Enterprise findEnterpriseById(int enterpriseId) {
        Enterprise enterprise = enterpriseDao.findEnterpriseById(enterpriseId);
        enterprise.setApplications(applicationService.findApplicationsByEnterpriseId(enterpriseId));
        return enterprise;
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

    @Override
    public void reorderApplicationsOfEnterprise(Enterprise enterprise) {
        applicationService.reorderApplicationsOfEnterprise(enterprise.getApplications());
    }

    @Override
    public boolean validateProfession(String profession) {
        return applicationService.validateProfession(profession);
    }

    @Override
    public boolean validateQuantity(String quantity) {
        return applicationService.validateQuantity(quantity);
    }

    @Override
    public void updateEnterpriseApplicationInfo(Application application) {
        applicationService.updateEnterpriseApplicationInfo(application);
    }

    @Override
    public void updateApplicationCollapsed(Application application) {
        applicationService.updateApplicationEnterpriseCollapsed(application);
    }

    @Override
    public void checkForUpdates(Enterprise enterprise) {
        Enterprise modernEnterprise = findEnterpriseById(enterprise.getId());
        enterprise.setName(modernEnterprise.getName());
        enterprise.setEmail(modernEnterprise.getEmail());
        enterprise.setApplications(modernEnterprise.getApplications());
    }

    @Override
    public void registerNewApplication(User user, ApplicationRegistrationForm form) {
        applicationService.registerNewApplication(user, form);
    }
}
