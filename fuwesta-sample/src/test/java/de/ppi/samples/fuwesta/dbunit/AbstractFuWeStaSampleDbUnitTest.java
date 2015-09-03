package de.ppi.samples.fuwesta.dbunit;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.dbunit.dataset.IDataSet;
import org.junit.AfterClass;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import de.ppi.samples.fuwesta.FuWeStaServer;

/**
 * Class AbstractDbUnitTest.
 *
 */
@SpringApplicationConfiguration(classes = FuWeStaServer.class)
@WebAppConfiguration
public abstract class AbstractFuWeStaSampleDbUnitTest extends
        AbstractJUnit4SpringContextTests {

    /**
     * The datasource.
     */
    @Resource
    private DataSource dataSource;

    /**
     * DB-Schema.
     */
    @Value("${spring.datasource.schema}")
    private String schema;

    /**
     * The specific database-information.
     */
    private FuWeStaSampleDatabase fuWeStaSampleDatabase;

    /**
     * @param dataSet the Dataset.
     * @see de.ppi.samples.fuwesta.dbunit.
     *      FuWeStaSampleDatabase#cleanlyInsert(org.dbunit.dataset.IDataSet)
     */
    public void cleanlyInsert(IDataSet dataSet) {
        fuWeStaSampleDatabase.cleanlyInsert(dataSet);
    }

    /**
     *
     * @param expected the expected data.
     * @see de.ppi.samples.fuwesta.dbunit.
     *      FuWeStaSampleDatabase#checkResult(org.dbunit.dataset.IDataSet)
     */
    public void checkResult(IDataSet expected) {
        fuWeStaSampleDatabase.checkResult(expected);
    }

    /**
     * Return the database-class.
     *
     * @return the database-class.
     */
    @Rule
    public FuWeStaSampleDatabase getFuWeStaSampleDatabase() {
        if (fuWeStaSampleDatabase == null) {
            try {
                fuWeStaSampleDatabase =
                        new FuWeStaSampleDatabase(dataSource.getConnection(),
                                schema);
            } catch (SQLException e) {
                throw new IllegalArgumentException(
                        "dataSource has not a valid connection", e);
            }
        }
        return fuWeStaSampleDatabase;
    }

    /**
     * Destroy the database.
     */
    @AfterClass
    public static void closeDB() {
        FuWeStaSampleDatabase.destroyDatabase();
    }

}
