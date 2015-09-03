package de.ppi.samples.fuwesta.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
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
     * Enables or disable the Filter.
     */
    private final boolean enabled = true;

    /**
     * Key for {@link FormAuthenticationFilter}.
     */
    private static final String AUTHC = DefaultFilter.authc.name();

    /**
     * Defines the realms.
     *
     * @return a list of {@link Realm}.
     */
    private List<Realm> defineRealms() {
        final List<Realm> realms = new ArrayList<Realm>();

        final IniRealm iniRealm = new IniRealm("classpath:userAndRoles.ini");
        iniRealm.setCredentialsMatcher(new PasswordMatcher());
        realms.add(iniRealm);
        return realms;
    }

    /**
     * Map urls to specific filters.
     *
     * @param filterMap a Map with existing definitions.
     *
     */
    private void defineSecurityFilter(Map<String, String> filterMap) {
        filterMap.put("/css/**/*", DefaultFilter.anon.name());
        filterMap.put("/js/**/*", DefaultFilter.anon.name());
        filterMap.put("/img/**/*", DefaultFilter.anon.name());
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
        result.setLoginUrl(URL.Auth.LOGIN);
        result.setSuccessUrl(URL.HOME);
        result.setUnauthorizedUrl(URL.Auth.UNAUTHORIZED);
        result.getFilters().put(DefaultFilter.authc.name(),
                createCustomFormAuthentficationFilter());
        result.getFilters().put(DefaultFilter.perms.name(),
                createCustomPermissionsAuthorizationFilter());
        defineSecurityFilter(result.getFilterChainDefinitionMap());
        return result;
    }

    /**
     * Creates a well configured {@link FormAuthenticationFilter}.
     *
     * @return a well configured {@link FormAuthenticationFilter}.
     */
    private Filter createCustomFormAuthentficationFilter() {
        FormAuthenticationFilter authc = new FormAuthenticationFilter();
        authc.setRememberMeParam("remember-me");
        authc.setEnabled(enabled);
        return authc;
    }

    /**
     * Creates a well configured {@link PermissionsAuthorizationFilter}.
     *
     * @return a well configured {@link PermissionsAuthorizationFilter}.
     */
    private Filter createCustomPermissionsAuthorizationFilter() {
        PermissionsAuthorizationFilter authc =
                new PermissionsAuthorizationFilter();
        authc.setEnabled(enabled);
        return authc;
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
        // final DefaultWebSessionManager sessionManager =
        // new DefaultWebSessionManager();
        // sessionManager.setSessionIdCookieEnabled(true);
        // securityManager.setSessionManager(sessionManager);
        securityManager.setRealms(defineRealms());
        return securityManager;
    }

}
