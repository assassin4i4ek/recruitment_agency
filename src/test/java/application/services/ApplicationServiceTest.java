package application.services;

import application.model.application.Application;
import application.model.application.ApplicationRegistrationForm;
import application.model.application.service.ApplicationService;
import application.model.user.User;
import application.model.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationServiceTest {
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserDetailsService userService;

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

    @Test
    public void registerNewApplicationTest() {
        for (int i = 0; i < 3; ++i) {
            ApplicationRegistrationForm form = new ApplicationRegistrationForm();
            form.setProfession("System administrator");
            form.setQuantity("1");
            User user = (User) userService.loadUserByUsername("e1");
            applicationService.registerNewApplication(user, form);
        }
    }
}
