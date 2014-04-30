package de.ppi.fuwesta.spring.mvc.formatter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.ppi.fuwesta.spring.mvc.formatter.StringTrimmerConverter;

/**
 * Class StringTrimmingConverterTest
 * 
 */
@RunWith(Parameterized.class)
public class StringTrimmerConverterTest {

    @Parameters(name = "{0}.{1}.{2}")
    public static Collection<Object[]> getValues() {
        final List<Object[]> testdata = new ArrayList<>();
        testdata.add(new Object[] { Boolean.FALSE, null, null });
        testdata.add(new Object[] { Boolean.FALSE, "", "" });
        testdata.add(new Object[] { Boolean.FALSE, "   ", "" });
        testdata.add(new Object[] { Boolean.FALSE, " a  ", "a" });
        testdata.add(new Object[] { Boolean.FALSE, " a b ", "a b" });

        testdata.add(new Object[] { Boolean.TRUE, null, null });
        testdata.add(new Object[] { Boolean.TRUE, "", null });
        testdata.add(new Object[] { Boolean.TRUE, "   ", null });
        testdata.add(new Object[] { Boolean.TRUE, " a  ", "a" });
        testdata.add(new Object[] { Boolean.TRUE, " a b ", "a b" });
        return testdata;
    }

    private final Boolean emptyAsNull;

    private final String input;

    private final String expectedOutput;

    public StringTrimmerConverterTest(Boolean emptyAsNull, String input,
            String expectedOutput) {
        this.emptyAsNull = emptyAsNull;
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.formatter.StringTrimmerConverter#convert(java.lang.String)}
     * .
     */
    @Test
    public void testConvertValue() {
        final StringTrimmerConverter stringTrimmingConverter =
                new StringTrimmerConverter(emptyAsNull.booleanValue());
        assertThat(stringTrimmingConverter.convert(input)).isEqualTo(
                expectedOutput);
    }

}
