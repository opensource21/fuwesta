package de.ppi.samples.fuwesta.dbunit;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.dbunit.dataset.builder.BuilderDataSetWriter;

/**
 * Class which generates BuilderDataSets.
 */
public class FuWeStaSampleBuilderDataSetWriter extends BuilderDataSetWriter {

    /**
     * Initiates an object.
     *
     * @param packageName the package name of the class.
     * @param className the name of the class.
     * @param importStatements some additional imports.
     */
    public FuWeStaSampleBuilderDataSetWriter(String packageName,
            String className, String... importStatements) {
        super(FuWeStaRowBuilderGenerator.DESTINATION_DIR, packageName,
                className, FuWeStaRowBuilderGenerator.FILE_ENCODING,
                FuWeStaRowBuilderGenerator.ROW_BUILDER_PACKAGE_NAME, true,
                FuWeStaRowBuilderGenerator.ROW_BUILDER_NAME_CREATOR,
                importStatements);
        this.addTypeMapping(BigInteger.class, Long.class);
        this.addTypeMapping(BigDecimal.class, Double.class);
    }

}
