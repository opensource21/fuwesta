package de.ppi.fuwesta.spring.mvc.util;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Test for {@link URLCleaner}.
 *
 */
@RunWith(Parameterized.class)
public class URLCleanerTest {

    @Parameterized.Parameters(name = "{0} - {1}")
    public static List<Object[]> testdata() {
        return Arrays
                .asList(new Object[][] {
                        { "/a", "/a" },
                        { "/a{c:[a-z]{1,5}}", "/a{c}" },
                        { "/a{a}{b:.*}{c:[a-z]{1,5}}", "/a{a}{b}{c}" },
                        { "/a{a}{b:.*}{c:[a-z]{1,}}", "/a{a}{b}{c}" },
                        { "/a{a}{b:.*}{c:[a-z]{1,20}[A-Z]{1,13}}",
                                "/a{a}{b}{c}" },
                        { "/user/edit/{user:[A-Z]{1,2}}", "/user/edit/{user}" },
                        { "/user/edit/{user_idn:[A-Z]{1,2}}",
                                "/user/edit/{user_idn}" } });
    }

    private final String inputUrlPattern;

    private final String expectedOutput;

    /**
     * Initiates an object of type URLCleanerTest.
     *
     * @param inputUrlPattern the URL with regexps.
     * @param expectedOutput the expected result.
     */
    public URLCleanerTest(String inputUrlPattern, String expectedOutput) {
        this.inputUrlPattern = inputUrlPattern;
        this.expectedOutput = expectedOutput;
    }

    @Test
    public void testRemoveRegexpFromUrl() throws Exception {
        Assert.assertEquals(expectedOutput,
                URLCleaner.removeRegexpFromUrl(inputUrlPattern));
    }
}
