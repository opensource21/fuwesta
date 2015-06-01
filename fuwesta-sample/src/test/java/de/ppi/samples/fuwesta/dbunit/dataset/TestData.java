package de.ppi.samples.fuwesta.dbunit.dataset;

import static de.ppi.samples.fuwesta.dbunit.rowbuilder.PostRowBuilder.newPost;
import static de.ppi.samples.fuwesta.dbunit.rowbuilder.TUserRowBuilder.newTUser;
import static de.ppi.samples.fuwesta.dbunit.rowbuilder.TagPostingsRowBuilder.newTagPostings;
import static de.ppi.samples.fuwesta.dbunit.rowbuilder.TagRowBuilder.newTag;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.dbunit.dataset.builder.ObjectFactory.ts;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.builder.DataSetBuilder;
import org.dbunit.validator.Validators;

/**
 * Common test-data.
 *
 */
public class TestData {

    @SuppressWarnings("boxing")
    public static IDataSet initWithSampleData() throws DataSetException {
        final DataSetBuilder b = new DataSetBuilder();
        newTUser().Id(11L).FirstName("Ben").LastName("Nutzer").Sex("m")
                .UserId("ben").addTo(b);
        newTUser().Id(12L).FirstName("Finda").LastName("Bug").Sex("f")
                .UserId("test").addTo(b);
        newPost().Id(101L).Content("Ein erster Inhalt")
                .CreationTime(ts("2014-03-12 00:00:00.0")).Title("Title 1")
                .UserId(11L).Version(Validators.gt(-1)).addTo(b);
        newPost().Id(102L).Content("der zweite Text").Title("Titel 2")
                .UserId(12L).addTo(b);

        newTag().Id(1L).Active(TRUE).Name("Test1").Version(Validators.gt(-1))
                .addTo(b);
        newTag().Id(2L).Active(TRUE).Name("Test2").Version(Validators.gt(-1))
                .addTo(b);
        newTag().Id(3L).Active(FALSE).Name("Test3").addTo(b);
        newTagPostings().Tags(2L).Postings(101L).addTo(b);
        newTagPostings().Tags(2L).Postings(102L).addTo(b);
        newTagPostings().Tags(1L).Postings(101L).addTo(b);
        return b.build();
    }

    @SuppressWarnings("boxing")
    public static IDataSet createPostData(int nrOfPost) throws DataSetException {
        final DataSetBuilder b = new DataSetBuilder();
        newTUser().Id(11L).FirstName("Ben").LastName("Nutzer").Sex("m")
                .UserId("ben").addTo(b);
        newTUser().Id(12L).FirstName("Finda").LastName("Bug").Sex("f")
                .UserId("test").addTo(b);
        for (int i = 1; i <= nrOfPost; i++) {
            newPost().Id(100L + i).Content("Post number " + i)
                    .CreationTime(ts((2000 + i) + "-03-01 00:00:00.0"))
                    .Title("Title " + i).UserId(11L).addTo(b);

        }

        newTag().Id(1L).Active(TRUE).Name("Test1").addTo(b);
        newTag().Id(2L).Active(TRUE).Name("Test2").addTo(b);
        newTag().Id(3L).Active(FALSE).Name("Test3").addTo(b);
        newTagPostings().Tags(2L).Postings(101L).addTo(b);
        newTagPostings().Tags(2L).Postings(102L).addTo(b);
        newTagPostings().Tags(1L).Postings(101L).addTo(b);
        return b.build();
    }
}
