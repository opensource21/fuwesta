package de.ppi.samples.fuwesta.dbunit.dataset;

import static de.ppi.samples.fuwesta.dbunit.rowbuilder.PostRowBuilder.newPost;
import static de.ppi.samples.fuwesta.dbunit.rowbuilder.TagPostingsRowBuilder.newTagPostings;
import static org.dbunit.dataset.builder.ObjectFactory.ts;
import static org.dbunit.validator.Validators.*;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.builder.DataSetBuilder;

/**
 * Some database-constellation to test post-data.
 */
public class PostTestData {

    /** Name of the vairable to store the id of the new POST. */
    private static final String VARIABLE_TO_STORE_POST_ID = "newPostId";

    @SuppressWarnings("boxing")
    public static IDataSet buildPostCreatedDataSet() throws DataSetException {
        IDataSet oldDataset = TestData.initWithSampleData();

        final DataSetBuilder b = new DataSetBuilder();
        b.addDataSet(oldDataset);

        newPost().Id(gt(102, VARIABLE_TO_STORE_POST_ID)).Version(0L)
                .Content("This is an example text.\r\nIt contains newlines.")
                .CreationTime(ts("2015-01-30 00:00:00.0")).Title("Test-Title1")
                .UserId(11L).addTo(b);
        newTagPostings().Tags(1L).Postings(nprop(VARIABLE_TO_STORE_POST_ID))
                .addTo(b);
        newTagPostings().Tags(2L).Postings(nprop(VARIABLE_TO_STORE_POST_ID))
                .addTo(b);
        return b.build();
    }

    @SuppressWarnings("boxing")
    public static IDataSet buildPostEditedDataSet() throws DataSetException {
        IDataSet oldDataset = TestData.initWithSampleData();

        final DataSetBuilder b = new DataSetBuilder();
        b.addDataSet(oldDataset);
        newPost()
                .Id(gt(102, VARIABLE_TO_STORE_POST_ID))
                .Version(1L)
                .Content(
                        "This is an example text.\r\nIt contains newlines.Not really conntent.")
                .CreationTime(ts("2015-03-30 00:00:00.0"))
                .Title("Test-Title1N").UserId(12L).addTo(b);
        newTagPostings().Tags(1L).Postings(nprop(VARIABLE_TO_STORE_POST_ID))
                .addTo(b);
        return b.build();
    }

    @SuppressWarnings("boxing")
    public static IDataSet buildPostPartialEditedDataSet()
            throws DataSetException {
        IDataSet oldDataset = TestData.initWithSampleData();

        final DataSetBuilder b = new DataSetBuilder();
        b.addDataSet(oldDataset);
        newPost().Id(gt(102, VARIABLE_TO_STORE_POST_ID)).Version(2L)
                .Content("This is an example text.\r\nIt contains newlines.")
                .CreationTime(ts("2015-01-30 00:00:00.0")).Title("Test-Title1")
                .UserId(12L).addTo(b);
        newTagPostings().Tags(1L).Postings(nprop(VARIABLE_TO_STORE_POST_ID))
                .addTo(b);
        return b.build();
    }

}
