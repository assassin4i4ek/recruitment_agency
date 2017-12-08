package application.model.enterprise.dao;

import application.model.enterprise.Enterprise;

public interface EnterpriseDao {
    Enterprise findEnterpriseById(int enterpriseId);
    String findEnterpriseNameById(int enterpriseId);
}
