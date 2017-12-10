package application.model.user.service;

import application.model.candidate.CandidateRegistrationForm;
import application.model.candidate.service.CandidateService;
import application.model.enterprise.EnterpriseRegistrationForm;
import application.model.user.UserRegistrationForm;
import application.model.user.dao.UserDao;
import application.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private CandidateService candidateService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Username wasn't found.");
        return user;
    }

    @Override
    public boolean validateUsername(UserRegistrationForm form) {
        return userDao.findUserByUsername(form.getUsername()) == null;
    }

    @Override
    public boolean validatePassword(UserRegistrationForm form) {
        return !form.getPassword().isEmpty() && form.getPassword().equals(form.getConfirmPassword());
    }

    @Override
    public boolean validateEmail(CandidateRegistrationForm form) {
        return !form.getEmail().isEmpty();
    }

    @Override
    public boolean validateEmail(EnterpriseRegistrationForm form) {
        return !form.getEmail().isEmpty();
    }

    @Override
    public void registerNewUser(EnterpriseRegistrationForm form) {
        userDao.createNewUser(form);
    }

    @Override
    public void registerNewUser(CandidateRegistrationForm form) {
        userDao.createNewUser(form);
        User user = (User) loadUserByUsername(form.getUsername());
        candidateService.registerNewCandidate(user, form);
    }
}
