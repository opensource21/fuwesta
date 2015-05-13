package de.ppi.samples.fuwesta.dbunit;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Testclass to generate some files.
 *
 */

public class ProjectSetup extends AbstractFuWeStaSampleDbUnitTest {

    /**
     * Print all TableNames.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @Ignore("Only for project-setup")
    public void printTableNames() throws Exception {
        FuWeStaSampleDataSet.printTableNames(getFuWeStaSampleDatabase()
                .getConnection());
    }

    /**
     * @see de.ppi.samples.fuwesta.dbunit.FuWeStaSampleDatabase#generateRowBuilder()
     */
    @Test
    public void generateRowBuilder() {
        getFuWeStaSampleDatabase().generateRowBuilder();
    }

}
