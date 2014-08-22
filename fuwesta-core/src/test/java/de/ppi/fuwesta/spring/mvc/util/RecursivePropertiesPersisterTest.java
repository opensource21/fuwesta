package de.ppi.fuwesta.spring.mvc.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.input.NullInputStream;
import org.apache.commons.io.input.NullReader;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.io.output.NullWriter;
import org.assertj.core.data.MapEntry;
import org.junit.Test;
import org.springframework.util.PropertiesPersister;

/**
 * Class RecursivePropertiesPersisterTest
 * 
 */
public class RecursivePropertiesPersisterTest {

    private PropertiesPersister propertiesPersister =
            mock(PropertiesPersister.class);

    private RecursivePropertiesPersister testee =
            new RecursivePropertiesPersister(propertiesPersister);

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.util.RecursivePropertiesPersister#load(java.util.Properties, java.io.InputStream)}
     * .
     * 
     * @throws Exception if something goes wrong.
     */
    @Test
    public void testLoadPropertiesInputStream() throws Exception {
        // Arrange
        final Properties inputProps = getInputProps();
        final InputStream is = new NullInputStream(10);
        // Act
        testee.load(inputProps, is);
        // Assert
        verify(propertiesPersister).load(inputProps, is);
        assertThat(inputProps).containsOnly(getOutputProps());
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.util.RecursivePropertiesPersister#load(java.util.Properties, java.io.Reader)}
     * .
     * 
     * @throws Exception if something goes wrong.
     */
    @Test
    public void testLoadPropertiesReader() throws Exception {
        // Arrange
        final Properties inputProps = getInputProps();
        final Reader is = new NullReader(10);
        // Act
        testee.load(inputProps, is);
        // Assert
        verify(propertiesPersister).load(inputProps, is);
        assertThat(inputProps).containsOnly(getOutputProps());
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.util.RecursivePropertiesPersister#store(java.util.Properties, java.io.OutputStream, java.lang.String)}
     * .
     * 
     * @throws IOException exception in test.
     */
    @Test
    public void testStorePropertiesOutputStreamString() throws IOException {
        // Arrange
        final Properties props = new Properties();
        final OutputStream os = new ByteArrayOutputStream();
        final String header = "Test";
        // Act
        testee.store(props, os, header);
        // Assert
        verify(propertiesPersister).store(props, os, header);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.util.RecursivePropertiesPersister#store(java.util.Properties, java.io.Writer, java.lang.String)}
     * .
     * 
     * @throws Exception if something goes wrong.
     */
    @Test
    public void testStorePropertiesWriterString() throws Exception {
        // Arrange
        final Properties props = new Properties();
        final Writer os = new NullWriter();
        final String header = "Test";
        // Act
        testee.store(props, os, header);
        // Assert
        verify(propertiesPersister).store(props, os, header);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.util.RecursivePropertiesPersister#loadFromXml(java.util.Properties, java.io.InputStream)}
     * .
     * 
     * @throws Exception if something goes wrong.
     */
    @Test
    public void testLoadFromXml() throws Exception {
        // Arrange
        final Properties inputProps = getInputProps();
        final InputStream is = new NullInputStream(10);
        // Act
        testee.loadFromXml(inputProps, is);
        // Assert
        verify(propertiesPersister).loadFromXml(inputProps, is);
        assertThat(inputProps).containsOnly(getOutputProps());

    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.util.RecursivePropertiesPersister#storeToXml(java.util.Properties, java.io.OutputStream, java.lang.String)}
     * .
     * 
     * @throws Exception if something goes wrong.
     */
    @Test
    public void testStoreToXmlPropertiesOutputStreamString() throws Exception {
        // Arrange
        final Properties props = new Properties();
        final OutputStream os = new NullOutputStream();
        final String header = "Test";
        // Act
        testee.storeToXml(props, os, header);
        // Assert
        verify(propertiesPersister).storeToXml(props, os, header);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.util.RecursivePropertiesPersister#storeToXml(java.util.Properties, java.io.OutputStream, java.lang.String, java.lang.String)}
     * .
     * 
     * @throws Exception if something goes wrong.
     */
    @Test
    public void testStoreToXmlPropertiesOutputStreamStringString()
            throws Exception {
        // Arrange
        final Properties props = new Properties();
        final OutputStream os = new NullOutputStream();
        final String header = "Test";
        final String encoding = "UTF-8";
        // Act
        testee.storeToXml(props, os, header, encoding);
        // Assert
        verify(propertiesPersister).storeToXml(props, os, header, encoding);
    }

    private Properties getInputProps() {
        final Properties props = new Properties();
        props.put("with.resolvedvar.recursive", "${with.resolvedvar}");
        props.put("with.unresolvedvar",
                "This is a ${value} in a ${properties}.");
        props.put("with.resolvedvar",
                "This is a ${prop.value} in a ${prop.properties}.");
        props.put("with.resolvedvarquoted",
                "This is a $'{'prop.value'}' in a $'{'prop.properties'}'.");
        props.put("prop.value", "nonsense");
        props.put("prop.properties", "sentence");
        return props;
    }

    private MapEntry[] getOutputProps() {
        final Properties props = new Properties();
        props.put("with.resolvedvar.recursive",
                "This is a nonsense in a sentence.");
        props.put("with.unresolvedvar", "This is a value in a properties.");
        props.put("with.resolvedvar", "This is a nonsense in a sentence.");
        props.put("with.resolvedvarquoted", "This is a nonsense in a sentence.");
        props.put("prop.value", "nonsense");
        props.put("prop.properties", "sentence");
        final MapEntry[] result = new MapEntry[props.size()];
        int i = 0;
        for (Map.Entry<Object, Object> prop : props.entrySet()) {
            result[i] = MapEntry.entry(prop.getKey(), prop.getValue());
            i++;
        }
        return result;
    }
}
