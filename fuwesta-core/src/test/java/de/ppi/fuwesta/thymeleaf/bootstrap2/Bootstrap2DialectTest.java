package de.ppi.fuwesta.thymeleaf.bootstrap2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.ppi.fuwesta.thymeleaf.bootstrap2.Bootstrap2Dialect;

/**
 * Test for {@link Bootstrap2Dialect}.
 * 
 */
public class Bootstrap2DialectTest {

    private final Bootstrap2Dialect bsDialect = new Bootstrap2Dialect();

    /**
     * Test method for
     * {@link de.ppi.fuwesta.thymeleaf.bootstrap2.Bootstrap2Dialect#getPrefix()}.
     */
    @Test
    public void testGetPrefix() {
        assertThat(bsDialect.getPrefix()).isEqualTo("bs");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.thymeleaf.bootstrap2.Bootstrap2Dialect#isLenient()}.
     */
    @Test
    public void testIsLenient() {
        assertThat(bsDialect.isLenient()).isFalse();
    }

}
