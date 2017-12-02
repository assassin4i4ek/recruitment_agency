package application.model.user;

import application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void validateTrueUserTest() {
        Login login = new Login("a1", "a1");
        User user = userService.validateUser(login);
        assertNotEquals(user, null);
    }

    @Test
    public void validateWrongUserTest() {
        Login login = new Login("a-1","a-1");
        User user = userService.validateUser(login);
        assertEquals(user, null);
    }
}
