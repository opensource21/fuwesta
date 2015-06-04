//GenericDatabaseTester.java
package de.ppi.samples.fuwesta.dbunit;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.dbunit.AbstractDatabaseTester;
import org.dbunit.DefaultOperationListener;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.ext.mysql.MySqlConnection;

/**
 * Class GenericDatabaseTester.
 *
 */
public class GenericDatabaseTester extends AbstractDatabaseTester {

    /**
     * H2-Database.
     */
    public static final String PRODUCT_H2 = "H2";
    /**
     * MYSQL-Database.
     */
    public static final String PRODUCT_MYSQL = "MySQL";

    /**
     * Database-Connection.
     */
    private final IDatabaseConnection connection;

    /**
     *
     * Initiates an object of type GenericDatabaseTester.
     *
     * @param jdbcConnection database-connection .
     * @param schema database schema of the database.
     */
    public GenericDatabaseTester(Connection jdbcConnection, String schema) {
        super();
        try {
            DatabaseMetaData metaData = jdbcConnection.getMetaData();
            final String productName = metaData.getDatabaseProductName();
            setOperationListener(new DefaultOperationListener() {
                @Override
                public void operationSetUpFinished(
                        IDatabaseConnection aConnection) {
                    // Do not invoke the "super" method to avoid that the
                    // connection is closed
                }

                @Override
                public void operationTearDownFinished(
                        IDatabaseConnection aConnection) {
                    // Do not invoke the "super" method to avoid that the
                    // connection is closed
                }
            });
            if (PRODUCT_H2.equals(productName)) {
                connection = new H2Connection(jdbcConnection, schema);
            } else if (PRODUCT_MYSQL.equals(productName)) {
                connection = new MySqlConnection(jdbcConnection, schema);
            } else {
                throw new IllegalStateException("Der Treiber " + productName
                        + " ist unbekannt.");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Couldn't init database", e);
        }

    }

    @Override
    public IDatabaseConnection getConnection() throws Exception {
        return connection;
    }

}
