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
        assertNotNull(applications);
        assertTrue(applications.size() == 1);
    }

    @Test
    public void emptyApplications() {
        int agentId = 1;
        List<Application> applications = applicationService.findApplicationsByAgentId(agentId);
        assertNotNull(applications);
        assertTrue(applications.size() == 0);
    }
}
