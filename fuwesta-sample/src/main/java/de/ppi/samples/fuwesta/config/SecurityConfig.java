package de.ppi.samples.fuwesta.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import de.ppi.samples.fuwesta.frontend.URL;

/**
 * Config for secure the application.
 * 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Config the {@link AuthenticationManagerBuilder}.
     * 
     * @param auth the builder
     * @throws Exception if something goes wrong
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        //J-
        auth.inMemoryAuthentication().
            withUser("user").password("123").roles("USER").and().
            withUser("post").password("123").roles("POST").and().
            withUser("tag").password("123").roles("TAG").and().
            withUser("admin").password("123").roles("ADMIN");
        //J+
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //J-
        http.authorizeRequests().antMatchers(URL.LOGIN, "/logout").permitAll().and().
            authorizeRequests().antMatchers("/tag/**").hasAnyRole("TAG", "ADMIN").and().
            authorizeRequests().antMatchers("/user/**").hasAnyRole("USER", "ADMIN").and().
            authorizeRequests().antMatchers("/post/**").hasAnyRole("POST", "ADMIN").and().
            authorizeRequests().antMatchers("/").permitAll().and().
            authorizeRequests().anyRequest().denyAll().and().
            formLogin().loginPage(URL.LOGIN).and().
            rememberMe().key("PPI-SECRET");
        //J+
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
}
