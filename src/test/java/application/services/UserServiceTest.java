package application.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void connected() {
        assertNotNull(userDetailsService);
    }

    @Test
    public void loadExistingUser() {
        String username = "a1";
        UserDetails user = userDetailsService.loadUserByUsername(username);
        assertNotNull(user);
    }

    @Test(expected=UsernameNotFoundException.class)
    public void loadNotExistingUser() {
        String username = "a-1";
        UserDetails user = userDetailsService.loadUserByUsername(username);
        fail();
    }
}
