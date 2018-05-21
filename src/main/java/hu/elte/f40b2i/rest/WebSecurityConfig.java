package hu.elte.f40b2i.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/css/**","/js/**","/img/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/tudor/**").hasRole("TUDOR")
                .antMatchers("/customer/**").hasRole("CUSTOMER")
                .antMatchers("/admin_home.html").hasRole("ADMIN")
                .antMatchers("/tudor_home.html").hasRole("TUDOR")
                .antMatchers("/customer_home.html").hasRole("CUSTOMER")
                .antMatchers("/user/**").authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/user/dispatch")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {

        return new AuthUserService();
    }
}