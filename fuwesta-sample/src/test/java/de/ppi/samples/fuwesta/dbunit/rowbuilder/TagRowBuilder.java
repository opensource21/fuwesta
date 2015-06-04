package de.ppi.samples.fuwesta.dbunit.rowbuilder;

import org.dbunit.dataset.builder.BasicDataRowBuilder;

public class TagRowBuilder extends BasicDataRowBuilder {

    public static final String TABLE_NAME = "TAG";

    public static final String C_ACTIVE = "ACTIVE";
    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static final String C_VERSION = "VERSION";

    public static final String[] PRIMARY_KEY = {C_ID};

    public static final String[] ALL_COLUMNS = {C_ACTIVE, C_ID, C_NAME, C_VERSION};

    public TagRowBuilder(String... identifierColumns) {
        super(TABLE_NAME, identifierColumns);
        setAllColumnNames(ALL_COLUMNS);
        addDefaultValue(C_NAME, "");
        addDefaultValue(C_VERSION, new Long("0"));
        addDefaultValue(C_ID, new Long("0"));
        addDefaultValue(C_ACTIVE, Boolean.FALSE);
    }

    public final TagRowBuilder Active (Boolean value) {
        with(C_ACTIVE, value);
        return this;
    }

    public final TagRowBuilder Id (Number value) {
        with(C_ID, value);
        return this;
    }

    public final TagRowBuilder Name (String value) {
        with(C_NAME, value);
        return this;
    }

    public final TagRowBuilder Version (Number value) {
        with(C_VERSION, value);
        return this;
    }


    public static TagRowBuilder newTag() {
        return new TagRowBuilder(PRIMARY_KEY);
    }

    public static TagRowBuilder newTag(String... identifierColumns) {
        return new TagRowBuilder(identifierColumns);
    }

}
