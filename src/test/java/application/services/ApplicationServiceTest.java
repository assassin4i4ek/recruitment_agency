package application.services;

import application.model.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationServiceTest {
    @Autowired
    private ApplicationService applicationService;

    @Test
    public void connected() {
        assertNotNull(applicationService);
    }

    @Test
    public void notEmptyApplications() {
        int agentId = 2;
        List<Application> applications = applicationService.findApplicationsByAgentId(agentId);
        assertTrue(applications.size() > 0);
    }

    @Test
    public void emptyApplications() {
        int agentId = 1;
        List<Application> applications = applicationService.findApplicationsByAgentId(agentId);
        assertTrue(applications.size() == 0);
    }

    @Test
    public void remainsApplicationsOrder() {
        int agentId = 2;
        List<Application> oldApplications = applicationService.findApplicationsByAgentId(agentId);
        List<Application> newApplications = new ArrayList<>(oldApplications);
        Collections.reverse(newApplications);
        applicationService.reorderApplicationsOfAgent(newApplications);
        Collections.reverse(newApplications);
        applicationService.reorderApplicationsOfAgent(newApplications);
        newApplications = applicationService.findApplicationsByAgentId(agentId);
        for (int i = 0; i < newApplications.size(); ++i)
            assertEquals(newApplications.get(i).getId(), oldApplications.get(i).getId());
    }
}
