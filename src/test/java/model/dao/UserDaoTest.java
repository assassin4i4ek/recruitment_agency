package model.dao;

import application.Application;
import model.TestMyApplication;
import model.dao.UserDao;
import model.user_model.Login;
import model.user_model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;

import model.user_model.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    void validateUserTest() {
        Login login = new Login("a1", "a1");
        User user = userDao.validateUser(login);
        assertEquals(user, null);
    }
}