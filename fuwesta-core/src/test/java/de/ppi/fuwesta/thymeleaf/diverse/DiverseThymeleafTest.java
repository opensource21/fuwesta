package de.ppi.fuwesta.thymeleaf.diverse;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.ppi.fuwesta.thymeleaf.basic.ThymeleafTest;

/**
 * Class DiverseThymeleafTest
 * 
 */
@RunWith(Parameterized.class)
public class DiverseThymeleafTest extends ThymeleafTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> getFilenames() throws IOException {
        return ThymeleafTest
                .getFilenames("classpath:/thymeleaf/div/**/*.thtest");
    }

    /**
     * Initiates an object of type DiverseThymeleafTest.
     * 
     * @param name
     * @param testSpec
     */
    public DiverseThymeleafTest(String name, File testSpec) {
        super(name, testSpec);
    }

}
