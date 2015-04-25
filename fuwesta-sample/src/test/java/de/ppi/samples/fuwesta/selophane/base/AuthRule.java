package de.ppi.samples.fuwesta.selophane.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import de.ppi.samples.fuwesta.selophane.module.AuthModule;

/**
 * Rule which handles default authentication issues.
 *
 */
public class AuthRule implements TestRule {

    /**
     * Authetication module.
     */
    private final AuthModule authModule;

    /**
     * Initiates an object of type AuthRule.
     *
     * @param authModule the module which do the authentication work.
     */
    public AuthRule(AuthModule authModule) {
        super();
        this.authModule = authModule;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                String username = "admin";
                String password = "123";
                boolean specialUser = false;
                final Auth authInfo = description.getAnnotation(Auth.class);

                if (authInfo != null) {
                    username = authInfo.user();
                    password = authInfo.password();
                    authModule.logoutIfNecessary();
                    specialUser = true;
                }
                if (specialUser || !authModule.isLogedIn()) {
                    authModule.openLoginMask();
                    authModule.login(username, password, false);
                }
                try {
                    base.evaluate();
                } finally {
                    if (specialUser) {
                        authModule.logout();
                    }
                }
            }
        };
    }

    /**
     * Annotation to define authentication details.
     *
     */
    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Auth {

        /**
         * Username.
         */
        String user() default "admin";

        /**
         * Password.
         */
        String password() default "123";

    }

}
