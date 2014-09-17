package de.ppi.samples.fuwesta.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.ppi.samples.fuwesta.frontend.URL;

/**
 * Config for secure the application.
 * 
 */
@Configuration
public class SecurityConfig {

    /**
     * Key for {@link FormAuthenticationFilter}.
     */
    private static final String AUTHC = "authc";

    /**
     * Defines the realms.
     * 
     * @return a list of {@link Realm}.
     */
    private List<Realm> defineRealms() {
        final List<Realm> realms = new ArrayList<Realm>();
        realms.add(new IniRealm("classpath:userAndRoles.ini"));
        return realms;
    }

    /**
     * Map urls to specific filters.
     * 
     * @param filterMap a Map with existing definitions.
     * 
     */
    private void defineSecurityFilter(Map<String, String> filterMap) {
        filterMap.put("/resources/**/*", DefaultFilter.anon.name());
        filterMap.put("/logout", DefaultFilter.logout.name());
        filterMap.put("/post/**", AUTHC + ", perms[post:*]");
        filterMap.put("/user/**", AUTHC + ", perms[user:*]");
        filterMap.put("/tag/**", AUTHC + ", perms[tag:*]");
        filterMap.put("/**", AUTHC);
    }

    /**
     * Init the shiro-filter bean.
     * 
     * @return the shiro-filter bean.
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        final ShiroFilterFactoryBean result = new ShiroFilterFactoryBean();
        result.setSecurityManager(securityManager());
        result.setLoginUrl(URL.LOGIN);
        result.setSuccessUrl(URL.HOME);
        // result.setUnauthorizedUrl(null);
        defineSecurityFilter(result.getFilterChainDefinitionMap());
        return result;
    }

    /**
     * Creates a well configured {@link FormAuthenticationFilter}.
     * 
     * @return a well configured {@link FormAuthenticationFilter}.
     */
    @Bean(name = AUTHC)
    public Filter createCustomFormAuthentficationFilter() {
        FormAuthenticationFilter result = new FormAuthenticationFilter();
        result.setRememberMeParam("remember-me");
        return result;
    }

    /**
     * Makes sure the init-methods will be call. Unsure if it necessary.
     * 
     * @return a {@link LifecycleBeanPostProcessor}.
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * Init the security-manager which holds the realms.
     * 
     * @return the security-manager.
     */
    private org.apache.shiro.mgt.SecurityManager securityManager() {
        final DefaultWebSecurityManager securityManager =
                new DefaultWebSecurityManager();
        final DefaultWebSessionManager sessionManager =
                new DefaultWebSessionManager();
        sessionManager.setSessionIdCookieEnabled(true);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealms(defineRealms());
        return securityManager;
    }

}
