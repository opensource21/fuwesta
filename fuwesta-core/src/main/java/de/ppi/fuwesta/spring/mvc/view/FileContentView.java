package de.ppi.fuwesta.spring.mvc.view;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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

        //J- MS-Types from http://filext.com/faq/office_mime_types.php
        MIME_TYPES.put("doc", "application/msword");
        MIME_TYPES.put("dot", "application/msword");
        MIME_TYPES.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        MIME_TYPES.put("dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");
        MIME_TYPES.put("docm", "application/vnd.ms-word.document.macroEnabled.12");
        MIME_TYPES.put("dotm", "application/vnd.ms-word.template.macroEnabled.12");
        MIME_TYPES.put("xls", "application/vnd.ms-excel");
        MIME_TYPES.put("xlt", "application/vnd.ms-excel");
        MIME_TYPES.put("xla", "application/vnd.ms-excel");
        MIME_TYPES.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        MIME_TYPES.put("xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
        MIME_TYPES.put("xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12");
        MIME_TYPES.put("xltm", "application/vnd.ms-excel.template.macroEnabled.12");
        MIME_TYPES.put("xlam", "application/vnd.ms-excel.addin.macroEnabled.12");
        MIME_TYPES.put("xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12");
        MIME_TYPES.put("ppt", "application/vnd.ms-powerpoint");
        MIME_TYPES.put("pot", "application/vnd.ms-powerpoint");
        MIME_TYPES.put("pps", "application/vnd.ms-powerpoint");
        MIME_TYPES.put("ppa", "application/vnd.ms-powerpoint");
        MIME_TYPES.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        MIME_TYPES.put("potx", "application/vnd.openxmlformats-officedocument.presentationml.template");
        MIME_TYPES.put("ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow");
        MIME_TYPES.put("ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12");
        MIME_TYPES.put("pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
        MIME_TYPES.put("potm", "application/vnd.ms-powerpoint.template.macroEnabled.12");
        MIME_TYPES.put("ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
      //J+
    }

    private final File content;
    private final String fileName;
    private final String specialMimeType;

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
     * @param content the content.
     * @param fileName the name of the file which should be shown at save
     *            dialog.
     */
    public FileContentView(File content, String fileName) {
        this(content, fileName, null);
    }

    /**
     * Initiates an object of type FileContentView.
     *
     * @param content the content.
     * @param fileName the name of the file which should be shown at save
     *            dialog.
     * @param mimeType a specific mimetype.
     */
    public FileContentView(File content, String fileName, String mimeType) {
        super();
        this.content = content;
        this.fileName = fileName;
        this.specialMimeType = mimeType;
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
        final String contentType;
        if (StringUtils.isEmpty(specialMimeType)) {
            contentType = MIME_TYPES.get(extension);
        } else {
            contentType = specialMimeType;
        }
        if (contentType == null) {
            LOG.error("No content-type defined for extension {}.", extension);
        } else {
            response.setContentType(contentType);
        }

        FileInputStream contentStream = new FileInputStream(content);
        try {
            IOUtils.copy(contentStream, response.getOutputStream());
            response.flushBuffer();
        } finally {
            contentStream.close();
        }

    }
}
