package de.ppi.samples.fuwesta.dbunit.rowbuilder;

import org.dbunit.dataset.builder.BasicDataRowBuilder;

public class TagPostingsRowBuilder extends BasicDataRowBuilder {

    public static final String TABLE_NAME = "TAG_POSTINGS";

    public static final String C_POSTINGS = "POSTINGS";
    public static final String C_TAGS = "TAGS";

    public static final String[] PRIMARY_KEY = {C_TAGS, C_POSTINGS};

    public static final String[] ALL_COLUMNS = {C_POSTINGS, C_TAGS};

    public TagPostingsRowBuilder(String... identifierColumns) {
        super(TABLE_NAME, identifierColumns);
        setAllColumnNames(ALL_COLUMNS);
        addDefaultValue(C_TAGS, new Long("0"));
        addDefaultValue(C_POSTINGS, new Long("0"));
    }

    public final TagPostingsRowBuilder Postings (Number value) {
        with(C_POSTINGS, value);
        return this;
    }

    public final TagPostingsRowBuilder Tags (Number value) {
        with(C_TAGS, value);
        return this;
    }


    public static TagPostingsRowBuilder newTagPostings() {
        return new TagPostingsRowBuilder(PRIMARY_KEY);
    }

    public static TagPostingsRowBuilder newTagPostings(String... identifierColumns) {
        return new TagPostingsRowBuilder(identifierColumns);
    }

}
