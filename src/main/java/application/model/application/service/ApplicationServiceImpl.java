package application.model.application.service;

import application.model.application.Application;
import application.model.application.dao.ApplicationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService{
    @Autowired
    private ApplicationDao applicationDao;

    @Override
    public List<Application> findApplicationsByAgentId(int agentId) {
        List<Application> applications = applicationDao.findApplicationsByAgentId(agentId);
        return applications != null ? applications : new ArrayList<>(0);
    }

    @Override
    public void reorderApplicationsOfAgent(List<Application> applications) {
        applicationDao.reorderApplicationsOfAgent(applications);
    }
}
