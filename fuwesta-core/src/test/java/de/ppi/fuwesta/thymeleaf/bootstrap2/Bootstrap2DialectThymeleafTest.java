package de.ppi.fuwesta.thymeleaf.bootstrap2;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.ppi.fuwesta.thymeleaf.basic.ThymeleafTest;
import de.ppi.fuwesta.thymeleaf.bootstrap2.Bootstrap2Dialect;

/**
 * Test for {@link Bootstrap2Dialect}.
 * 
 */
@RunWith(Parameterized.class)
public class Bootstrap2DialectThymeleafTest extends ThymeleafTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> getFilenames() throws IOException {
        return ThymeleafTest
                .getFilenames("classpath:/thymeleaf/bootstrap2/**/*.thtest");
    }

    public Bootstrap2DialectThymeleafTest(String name, File testSpec) {
        super(name, testSpec);
    }
}
