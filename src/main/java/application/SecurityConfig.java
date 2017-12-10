package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
                .antMatchers(
                    "/","/registerCandidate","registerEnterprise",
                    "/js/**",
                    "/css/**",
                    "/img/**",
                    "/webjars/**").permitAll()
                .antMatchers("/agent/**").hasRole("AGENT")
                .antMatchers("/candidate/**").hasRole("CANDIDATE")
                .antMatchers("/enterprise/**").hasRole("ENTERPRISE")
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
            .accessDeniedHandler((request, response, event) -> {
                event.printStackTrace();
                response.sendRedirect("/error/access-denied");
            })
    .and()
        .csrf().disable()
        .userDetailsService(userDetailsService);
    }
}
