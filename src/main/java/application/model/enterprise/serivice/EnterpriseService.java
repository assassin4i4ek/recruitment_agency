package application.model.enterprise.serivice;

import application.model.enterprise.Enterprise;

public interface EnterpriseService {
    Enterprise findEnterpriseById(int enterpriseId);
    String findEnterpriseNameById(int enterpriseId);
}
