package de.ppi.samples.fuwesta.dbunit;

import java.sql.SQLException;

import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.filter.ITableFilter;

/**
 * Dataset which holds the order of the sample-db.
 *
 */
public class FuWeStaSampleDataSet extends FilteredDataSet {

    /**
     * Tablename ordered by dependencies.
     */
    private static final String[] TABLE_NAMES = { "T_USER", "POST", "TAG",
            "TAG_POSTINGS" };

    /**
     * Initiates an object of type FuWeStaSampleDataSet.
     *
     * @param dataSet the dataset.
     * @throws AmbiguousTableNameException if multiple tables has the same name.
     */
    public FuWeStaSampleDataSet(IDataSet dataSet)
            throws AmbiguousTableNameException {
        super(TABLE_NAMES, dataSet);
    }

    /**
     * Print the tables in the order of the dependencies.
     *
     * @param conn the database-connection.
     * @throws DataSetException error in the dataset.
     * @throws SQLException error communicate with db.
     */
    // //CSOFF: RegexpSinglelineJava It's only helper to dump something to
    // console.
    public static void printTableNames(IDatabaseConnection conn)
            throws DataSetException, SQLException {
        final ITableFilter filter = new DatabaseSequenceFilter(conn);
        final ITableIterator tables =
                filter.iterator(conn.createDataSet(), false);
        System.out.println("########### Database tables");
        while (tables.next()) {
            System.out.print("\"" + tables.getTableMetaData().getTableName()
                    + "\", ");
        }
        System.out.println();
    }
    // CSON: RegexpSinglelineJava

}
