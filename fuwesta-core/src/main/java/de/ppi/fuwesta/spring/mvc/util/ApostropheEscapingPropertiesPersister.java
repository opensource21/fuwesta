package de.ppi.fuwesta.spring.mvc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.util.PropertiesPersister;

/**
 * Strategy which replaces all simple apostrophes by double apostrophe during
 * the initial loading of properties files. The storage and usage of XML files
 * is not supported.
 */

public final class ApostropheEscapingPropertiesPersister implements
        PropertiesPersister {

    /**
     * Replace simple apostrophe with double.
     * 
     * @param props the properties.
     */
    private void escape(Properties props) {
        for (Entry<Object, Object> element : props.entrySet()) {
            // ' -> '' because it's convenient to work with a single ' in
            // properties.
            String newValue = ((String) element.getValue()).replace("'", "''");
            // For Oval we must replace {content} with '{'content'}' otherwise
            // the message-source try to format.
            newValue = newValue.replaceAll("\\{([^0-9][^}]*)\\}", "'{'$1'}'");
            element.setValue(newValue);
        }
    }

    @Override
    public void load(Properties props, InputStream is) throws IOException {
        props.load(is);
        escape(props);
    }

    @Override
    public void load(Properties props, Reader reader) throws IOException {
        props.load(reader);
        escape(props);
    }

    @Override
    public void store(Properties props, OutputStream os, String header)
            throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void store(Properties props, Writer writer, String header)
            throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadFromXml(Properties props, InputStream is)
            throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void storeToXml(Properties props, OutputStream os, String header)
            throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void storeToXml(Properties props, OutputStream os, String header,
            String encoding) throws IOException {
        throw new UnsupportedOperationException();
    }

}
