package de.ppi.samples.fuwesta.dbunit;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.filter.IColumnFilter;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.validator.ValidatorFailureHandler;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import de.ppi.samples.fuwesta.dbunit.rowbuilder.PostRowBuilder;
import de.ppi.samples.fuwesta.dbunit.rowbuilder.TUserRowBuilder;
import de.ppi.samples.fuwesta.dbunit.rowbuilder.TagPostingsRowBuilder;
import de.ppi.samples.fuwesta.dbunit.rowbuilder.TagRowBuilder;

/**
 *
 * Rule to get access to the database.
 *
 */
public class FuWeStaSampleDatabase implements TestRule {

    /**
     * Batchsize for SQL-Statements.
     */
    private static final Integer BATCH_SIZE = Integer.valueOf(100);

    /**
     * The database-connection.
     */
    private final Connection connection;

    /**
     * The database schema.
     */
    private final String schema;

    /**
     * DBUnit- Class to test the database connection.
     */
    private static IDatabaseTester databaseTester = null;

    /**
     * Dataset of tables which should be delete.
     */
    private static IDataSet deleteDataSet = null;

    /**
     * Map a table-name to a unique-key.
     */
    private static Map<String, String[]> tableToUniqueKey = new HashMap<>();

    /**
     * Map a table-name to a primary-key.
     */
    private static Map<String, String[]> tableToPrimaryKey = new HashMap<>();

    static {
        tableToPrimaryKey.put(PostRowBuilder.TABLE_NAME,
                PostRowBuilder.PRIMARY_KEY);
        tableToPrimaryKey.put(TUserRowBuilder.TABLE_NAME,
                TUserRowBuilder.PRIMARY_KEY);
        tableToPrimaryKey.put(TagRowBuilder.TABLE_NAME,
                TagRowBuilder.PRIMARY_KEY);
        tableToPrimaryKey.put(PostRowBuilder.TABLE_NAME,
                PostRowBuilder.PRIMARY_KEY);
        tableToPrimaryKey.put(TagPostingsRowBuilder.TABLE_NAME,
                TagPostingsRowBuilder.ALL_COLUMNS);

        tableToUniqueKey.putAll(tableToPrimaryKey);
        tableToUniqueKey.put(TUserRowBuilder.TABLE_NAME,
                new String[] { TUserRowBuilder.C_USER_ID });
        tableToUniqueKey.put(TagRowBuilder.TABLE_NAME,
                new String[] { TagRowBuilder.C_NAME });
    }

    /**
     * Initiates an object of type {@link FuWeStaSampleDatabase}.
     *
     * @param connection the database connection.
     * @param schema the database schema.
     */
    public FuWeStaSampleDatabase(Connection connection, String schema) {
        super();
        this.connection = connection;
        this.schema = schema;
    }

    /**
     * Initialisiert DBUnit.
     *
     * @throws Exception wenn was schief geht.
     */
    public void initDatabase() throws Exception {
        if (databaseTester == null) {
            databaseTester = new GenericDatabaseTester(connection, schema);
            setUpDatabaseConfig(databaseTester.getConnection().getConfig());
        }
        if (deleteDataSet == null) {
            deleteDataSet =
                    new FuWeStaSampleDataSet(databaseTester.getConnection()
                            .createDataSet());
        }
    }

    /**
     * Print all tablenames of the database.
     */
    void printTableNames() {
        try {
            FuWeStaSampleDataSet
                    .printTableNames(databaseTester.getConnection());
        } catch (Exception e) {
            throw new IllegalStateException("error getting connection", e);
        }
    }

    /**
     * Configure database.
     *
     * @param config the database-config.
     */
    private void setUpDatabaseConfig(DatabaseConfig config) {
        config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, BATCH_SIZE);
        config.setProperty(DatabaseConfig.FEATURE_BATCHED_STATEMENTS,
                Boolean.TRUE);
        config.setProperty(DatabaseConfig.PROPERTY_PRIMARY_KEY_FILTER,
                new IColumnFilter() {

                    @Override
                    public boolean accept(String tableName, Column column) {
                        if (tableToPrimaryKey.containsKey(tableName)) {
                            return Arrays.asList(
                                    tableToPrimaryKey.get(tableName)).contains(
                                    column.getColumnName());
                        } else {
                            return column.getColumnName()
                                    .equalsIgnoreCase("id");
                        }
                    }
                });
    }

    /**
     * Call db-unit about the end of the test.
     */
    public void tearDownDb() {
        try {
            databaseTester.onTearDown();
        } catch (Exception e) {
            throw new IllegalStateException("Error tearDownDb", e);
        }
    }

    /**
     * Shutdown the database.
     *
     */
    public static void destroyDatabase() {
        try {
            deleteDataSet = null;
            databaseTester = null;
        } catch (Exception e) {
            throw new IllegalStateException("Error during database shutdown", e);
        }
    }

    /**
     * Delete all data first and insert the dataset.
     *
     * @param dataSet the set with data.
     */
    public void cleanlyInsert(IDataSet dataSet) {
        try {
            InsertIdentityOperation.DELETE.execute(
                    databaseTester.getConnection(), deleteDataSet);
            databaseTester.setSetUpOperation(DatabaseOperation.INSERT);
            databaseTester.setDataSet(new FuWeStaSampleDataSet(dataSet));
            databaseTester.onSetup();
        } catch (Exception e) {
            throw new IllegalStateException("Error during clean-insert", e);
        }
    }

    /**
     * Delete all data.
     *
     */
    public void clean() {
        try {
            InsertIdentityOperation.DELETE.execute(
                    databaseTester.getConnection(), deleteDataSet);
        } catch (Exception e) {
            throw new IllegalStateException("Error during clean", e);
        }
    }

    /**
     * Check if the content of the database as expected.
     *
     * @param expected the expected dataset.
     */
    public void checkResult(final IDataSet expected) {
        try {
            final IDataSet actual =
                    databaseTester.getConnection().createDataSet();
            final ITableIterator expectdTables = expected.iterator();
            while (expectdTables.next()) {
                final String tableName =
                        expectdTables.getTable().getTableMetaData()
                                .getTableName();
                final String[] uk = tableToUniqueKey.get(tableName);
                if (uk == null) {
                    throw new IllegalStateException(
                            "You must define a unique-key for each table.");
                }
                final ITable expectedTable =
                        new SortedTable(expected.getTable(tableName), uk);
                final ITable actualTable =
                        new SortedTable(actual.getTable(tableName), uk);
                Assertion.assertEquals(expectedTable, actualTable,
                        new ValidatorFailureHandler());
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error during checkResult", e);
        }

    }

    /**
     * Helper method to dump the current database.
     *
     * @param fileName the file where to store.
     * @param tableNames the name of the tables.
     * @throws Exception
     */
    public void dumpResult(String fileName, String... tableNames) {
        final FuWeStaSampleBuilderDataSetWriter writer =
                new FuWeStaSampleBuilderDataSetWriter(
                        "de.ppi.samples.fuwesta.dbunit.dataset", fileName);
        try {
            if (tableNames != null && tableNames.length > 0) {
                writer.write(new FilteredDataSet(tableNames, databaseTester
                        .getConnection().createDataSet()));
            } else {
                writer.write(deleteDataSet);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error during dump result", e);
        }

    }

    /**
     * Generate RowBuilder.
     */
    void generateRowBuilder() {
        try {
            FuWeStaRowBuilderGenerator rowBuilder =
                    new FuWeStaRowBuilderGenerator();
            rowBuilder.generate(databaseTester.getConnection().createDataSet());
        } catch (Exception e) {
            throw new IllegalStateException("Error during dump result", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    initDatabase();
                    base.evaluate();
                } finally {
                    tearDownDb();
                }
            }
        };
    }

}
