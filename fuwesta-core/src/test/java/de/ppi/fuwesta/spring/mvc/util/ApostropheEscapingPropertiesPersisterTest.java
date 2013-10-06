package de.ppi.fuwesta.spring.mvc.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.junit.Test;

import de.ppi.fuwesta.spring.mvc.util.ApostropheEscapingPropertiesPersister;

/**
 * Test for {@link ApostropheEscapingPropertiesPersister}.
 * 
 */
public class ApostropheEscapingPropertiesPersisterTest {

    /** Testobject. */
    private ApostropheEscapingPropertiesPersister testee =
            new ApostropheEscapingPropertiesPersister();

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.util.ApostropheEscapingPropertiesPersister#load(java.util.Properties, java.io.InputStream)}
     * .
     * 
     * @throws Exception if something goes wrong.
     */
    @Test
    public void testLoadPropertiesInputStream() throws Exception {
        final Properties props = new Properties();

        try (final InputStream stream =
                new ByteArrayInputStream(
                        "p1=v'alue'1\np2=va\"lue2\np3=test{content}and 'so on"
                                .getBytes())) {
            testee.load(props, stream);
        }

        assertThat(props.get("p1")).isEqualTo("v''alue''1");
        assertThat(props.get("p2")).isEqualTo("va\"lue2");
        assertThat(props.get("p3")).isEqualTo("test'{'content'}'and ''so on");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.util.ApostropheEscapingPropertiesPersister#load(java.util.Properties, java.io.Reader)}
     * .
     * 
     * @throws Exception if something goes wrong.
     */
    @Test
    public void testLoadPropertiesReader() throws Exception {
        final Properties props = new Properties();

        try (final Reader stream =
                new InputStreamReader(new ByteArrayInputStream(
                        "p1=v'alue'1\np2=va\"lue2".getBytes()))) {
            testee.load(props, stream);
        }

        assertThat(props.get("p1")).isEqualTo("v''alue''1");
        assertThat(props.get("p2")).isEqualTo("va\"lue2");
    }
}
