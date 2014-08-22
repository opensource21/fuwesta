package de.ppi.fuwesta.spring.mvc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.PropertiesPersister;

/**
 * {@link PropertiesPersister} which search for #propertyName# in each
 * property-value during load. During save there is no additional operation.
 * 
 */
public class RecursivePropertiesPersister implements PropertiesPersister {

    private final PropertiesPersister propertiesPersister;

    private final Pattern propPattern = Pattern
            .compile("\\$'?\\{'?([^}']*)'?\\}'?");

    /**
     * Initiates an object of type RecursivePropertiesPersister.
     * 
     * @param propertiesPersister
     */
    public RecursivePropertiesPersister(PropertiesPersister propertiesPersister) {
        super();
        this.propertiesPersister = propertiesPersister;
    }

    private void replace(Properties props) {
        for (Entry<Object, Object> element : props.entrySet()) {
            String oldValue = ((String) element.getValue());
            String newValue = oldValue;
            do {
                oldValue = newValue;
                newValue = replaceMessages(oldValue, props);
            } while (!newValue.equals(oldValue));
            element.setValue(newValue);
        }
    }

    private String replaceMessages(String newValue, Properties props) {
        Matcher matcher = propPattern.matcher(newValue);
        matcher.reset();
        boolean result = matcher.find();
        if (result) {
            StringBuffer sb = new StringBuffer();
            do {
                final String key = matcher.group(1);
                String replacement = props.getProperty(key, key);
                replacement = replacement.replaceAll("\\$", "\\\\\\$");
                matcher.appendReplacement(sb, replacement);
                result = matcher.find();
            } while (result);
            matcher.appendTail(sb);
            return sb.toString();
        } else {
            return newValue;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(Properties props, InputStream is) throws IOException {
        propertiesPersister.load(props, is);
        replace(props);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(Properties props, Reader reader) throws IOException {
        propertiesPersister.load(props, reader);
        replace(props);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(Properties props, OutputStream os, String header)
            throws IOException {
        propertiesPersister.store(props, os, header);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(Properties props, Writer writer, String header)
            throws IOException {
        propertiesPersister.store(props, writer, header);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadFromXml(Properties props, InputStream is)
            throws IOException {
        propertiesPersister.loadFromXml(props, is);
        replace(props);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeToXml(Properties props, OutputStream os, String header)
            throws IOException {
        propertiesPersister.storeToXml(props, os, header);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeToXml(Properties props, OutputStream os, String header,
            String encoding) throws IOException {
        propertiesPersister.storeToXml(props, os, header, encoding);
    }

}
