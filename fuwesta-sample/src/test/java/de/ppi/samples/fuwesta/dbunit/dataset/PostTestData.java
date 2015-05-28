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

/**
 * Some database-constellation to test post-data.
 */
public class PostTestData {

    @SuppressWarnings("boxing")
    public static IDataSet buildPostCreatedDataSet() throws DataSetException {
        final DataSetBuilder b = new DataSetBuilder();
        newTUser().Id(11L).Version(0L).FirstName("Ben").LastName("Nutzer")
                .Sex("m").UserId("ben").addTo(b);
        newTUser().Id(12L).Version(0L).FirstName("Finda").LastName("Bug")
                .Sex("f").UserId("test").addTo(b);
        newPost().Id(101L).Version(0L).Content("Ein erster Inhalt")
                .CreationTime(ts("2014-03-12 00:00:00.0")).Title("Title 1")
                .UserId(11L).addTo(b);
        newPost().Id(102L).Version(0L).Content("der zweite Text")
                .Title("Titel 2").UserId(12L).addTo(b);
        newPost().Id(103L).Version(0L)
                .Content("This is an example text.\r\nIt contains newlines.")
                .CreationTime(ts("2015-01-30 00:00:00.0")).Title("Test-Title1")
                .UserId(11L).addTo(b);
        newTag().Id(1L).Version(1L).Active(TRUE).Name("Test1").addTo(b);
        newTag().Id(2L).Version(1L).Active(TRUE).Name("Test2").addTo(b);
        newTag().Id(3L).Version(0L).Active(FALSE).Name("Test3").addTo(b);
        newTagPostings().Tags(1L).Postings(101L).addTo(b);
        newTagPostings().Tags(1L).Postings(103L).addTo(b);
        newTagPostings().Tags(2L).Postings(101L).addTo(b);
        newTagPostings().Tags(2L).Postings(102L).addTo(b);
        newTagPostings().Tags(2L).Postings(103L).addTo(b);
        return b.build();
    }
}
