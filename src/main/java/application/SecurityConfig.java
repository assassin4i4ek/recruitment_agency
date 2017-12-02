package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT username, password, enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery(
                        "SELECT username, role FROM users WHERE username=?");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
                .antMatchers(
                    "/",
                    "/js/**",
                    "/css/**",
                    "/img/**",
                    "/webjars/**").permitAll()
                .antMatchers("/user").hasRole("AGENT")
                .anyRequest().authenticated()
    .and()
    .formLogin()
            .loginPage("/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .permitAll()
    .and()
    .logout()
            .logoutSuccessUrl("/login?logout").permitAll()
    .and()
    .exceptionHandling()
            .accessDeniedPage("/error/access-denied")
    .and()
    .csrf();
    }


/*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("manager").password("password").roles("MANAGER");
    }*/
}
