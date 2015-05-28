package de.ppi.samples.fuwesta.dbunit.dataset;

import static de.ppi.samples.fuwesta.dbunit.rowbuilder.PostRowBuilder.newPost;
import static de.ppi.samples.fuwesta.dbunit.rowbuilder.TagPostingsRowBuilder.newTagPostings;
import static org.dbunit.dataset.builder.ObjectFactory.ts;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.builder.DataSetBuilder;

/**
 * Some database-constellation to test post-data.
 */
public class PostTestData {

    @SuppressWarnings("boxing")
    public static IDataSet buildPostCreatedDataSet() throws DataSetException {
        IDataSet oldDataset = TestData.initWithSampleData();

        final DataSetBuilder b = new DataSetBuilder();
        b.addDataSet(oldDataset);
        newPost().Id(103L).Version(0L)
                .Content("This is an example text.\r\nIt contains newlines.")
                .CreationTime(ts("2015-01-30 00:00:00.0")).Title("Test-Title1")
                .UserId(11L).addTo(b);
        newTagPostings().Tags(1L).Postings(103L).addTo(b);
        newTagPostings().Tags(2L).Postings(103L).addTo(b);
        return b.build();
    }

    @SuppressWarnings("boxing")
    public static IDataSet buildPostEditedDataSet() throws DataSetException {
        IDataSet oldDataset = TestData.initWithSampleData();

        final DataSetBuilder b = new DataSetBuilder();
        b.addDataSet(oldDataset);
        newPost()
                .Id(103L)
                .Version(1L)
                .Content(
                        "This is an example text.\r\nIt contains newlines.Not really conntent.")
                .CreationTime(ts("2015-03-30 00:00:00.0"))
                .Title("Test-Title1N").UserId(12L).addTo(b);
        newTagPostings().Tags(1L).Postings(103L).addTo(b);
        return b.build();
    }

}
