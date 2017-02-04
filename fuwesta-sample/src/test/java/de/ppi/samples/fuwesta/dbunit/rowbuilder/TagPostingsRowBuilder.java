package de.ppi.samples.fuwesta.dbunit.rowbuilder;

import org.dbunit.dataset.builder.BasicDataRowBuilder;

public class TagPostingsRowBuilder extends BasicDataRowBuilder {

    public static final String TABLE_NAME = "TAG_POSTINGS";

    public static final String C_POSTINGS_ID = "POSTINGS_ID";
    public static final String C_TAGS_ID = "TAGS_ID";

    public static final String[] PRIMARY_KEY = {C_TAGS_ID, C_POSTINGS_ID};

    public static final String[] ALL_COLUMNS = {C_POSTINGS_ID, C_TAGS_ID};

    public TagPostingsRowBuilder(String... identifierColumns) {
        super(TABLE_NAME, identifierColumns);
        setAllColumnNames(ALL_COLUMNS);
        addDefaultValue(C_TAGS_ID, new Long("0"));
        addDefaultValue(C_POSTINGS_ID, new Long("0"));
    }

    public final TagPostingsRowBuilder PostingsId (Number value) {
        with(C_POSTINGS_ID, value);
        return this;
    }

    public final TagPostingsRowBuilder TagsId (Number value) {
        with(C_TAGS_ID, value);
        return this;
    }


    public static TagPostingsRowBuilder newTagPostings() {
        return new TagPostingsRowBuilder(PRIMARY_KEY);
    }

    public static TagPostingsRowBuilder newTagPostings(String... identifierColumns) {
        return new TagPostingsRowBuilder(identifierColumns);
    }

}
