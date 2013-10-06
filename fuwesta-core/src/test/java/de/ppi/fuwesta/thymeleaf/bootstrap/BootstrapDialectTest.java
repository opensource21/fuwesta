package de.ppi.fuwesta.thymeleaf.bootstrap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.ppi.fuwesta.thymeleaf.bootstrap.BootstrapDialect;

/**
 * Test for {@link BootstrapDialect}.
 * 
 */
public class BootstrapDialectTest {

    private final BootstrapDialect bsDialect = new BootstrapDialect();

    /**
     * Test method for
     * {@link de.ppi.fuwesta.thymeleaf.bootstrap.BootstrapDialect#getPrefix()}.
     */
    @Test
    public void testGetPrefix() {
        assertThat(bsDialect.getPrefix()).isEqualTo("bs");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.thymeleaf.bootstrap.BootstrapDialect#isLenient()}.
     */
    @Test
    public void testIsLenient() {
        assertThat(bsDialect.isLenient()).isFalse();
    }

}
