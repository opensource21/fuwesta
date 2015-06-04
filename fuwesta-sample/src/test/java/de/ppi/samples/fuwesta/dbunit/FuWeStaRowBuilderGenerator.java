package de.ppi.samples.fuwesta.dbunit;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.dbunit.dataset.builder.CustomRowBuilderGenerator;
import org.dbunit.dataset.builder.JavaFriendlyNameCreator;
import org.dbunit.dataset.builder.RowBuilderNameCreator;

/**
 * Generator for FuWeSta-Rowbuilder.
 *
 */
public class FuWeStaRowBuilderGenerator extends CustomRowBuilderGenerator {

    /**
     * The naming-strategy for the RowBuilder.
     */
    public static final RowBuilderNameCreator ROW_BUILDER_NAME_CREATOR =
            new JavaFriendlyNameCreator();
    /**
     * The file-endcoding.
     */
    public static final String FILE_ENCODING = "UTF-8";
    /**
     * Packagename of the row-builder.
     */
    public static final String ROW_BUILDER_PACKAGE_NAME =
            "de.ppi.samples.fuwesta.dbunit.rowbuilder";
    /**
     * Target directory where the generated files should be saved.
     */
    public static final File DESTINATION_DIR = new File("src/test/java");

    /**
     * Initiates an object of type {@link FuWeStaRowBuilderGenerator}.
     */
    public FuWeStaRowBuilderGenerator() {
        super(DESTINATION_DIR, ROW_BUILDER_PACKAGE_NAME, FILE_ENCODING,
                ROW_BUILDER_NAME_CREATOR);
        this.addTypeMapping(BigInteger.class, Long.class);
        this.addTypeMapping(BigDecimal.class, Double.class);
    }

}
