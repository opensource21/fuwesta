package de.ppi.fuwesta.spring.mvc.view;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.h2.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

/**
 * A view which shows the content of a File.
 * 
 */
public class FileContentView extends AbstractView {

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(FileContentView.class);

    private static final Map<String, String> MIME_TYPES = new HashMap<>();

    static {
        MIME_TYPES.put("html", "text/html");
        MIME_TYPES.put("xml", "text/xml");
        MIME_TYPES.put("text", "text/plain");
        MIME_TYPES.put("js", "text/javascript");
        MIME_TYPES.put("rss", "application/rss+xml");
        MIME_TYPES.put("atom", "application/atom+xml");
        MIME_TYPES.put("css", "text/css");
        MIME_TYPES.put("csv", "text/csv");
        MIME_TYPES.put("json", "application/json");
        MIME_TYPES.put("form", "application/x-www-form-urlencoded");
        MIME_TYPES.put("odt", "application/vnd.oasis.opendocument.text");
        MIME_TYPES.put("pdf", "application/pdf");
        MIME_TYPES.put("multipartForm", "multipart/form-data");
    }

    private final File content;
    private final String fileName;

    private static final String FILE_NAME_ENCODING = "UTF-8";

    /**
     * Initiates an object of type FileContentView.
     * 
     * @param content the content.
     */
    public FileContentView(File content) {
        this(content, content.getName());
    }

    /**
     * Initiates an object of type FileContentView.
     * 
     * @param content teh content.
     * @param fileName the name of the file which should be shown at save
     *            dialog.
     */
    public FileContentView(File content, String fileName) {
        super();
        this.content = content;
        this.fileName = fileName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // The filename is a real-problem I choose a solution which works
        // for FF see
        // http://stackoverflow.com/questions/93551/how-to-encode-the-filename-parameter-of-content-disposition-header-in-http
        final String fileNameEncoded =
                URLEncoder.encode(fileName, FILE_NAME_ENCODING);
        response.setHeader("Content-disposition", "attachment;  filename*="
                + FILE_NAME_ENCODING + "''" + fileNameEncoded);
        final String extension = FilenameUtils.getExtension(content.getName());
        final String contentType = MIME_TYPES.get(extension);
        if (contentType == null) {
            LOG.error("No content-type defined for extension {}.", extension);
        } else {
            response.setContentType(contentType);
        }

        FileInputStream zeugnisStream = new FileInputStream(content);
        try {
            IOUtils.copy(zeugnisStream, response.getOutputStream());
            response.flushBuffer();
        } finally {
            zeugnisStream.close();
        }

    }
}
