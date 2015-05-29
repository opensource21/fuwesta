package de.ppi.samples.fuwesta.dbunit.rowbuilder;

import org.dbunit.dataset.builder.BasicDataRowBuilder;
import java.sql.Timestamp;

public class PostRowBuilder extends BasicDataRowBuilder {

    public static final String TABLE_NAME = "POST";

    public static final String C_CONTENT = "CONTENT";
    public static final String C_CREATION_TIME = "CREATION_TIME";
    public static final String C_ID = "ID";
    public static final String C_TITLE = "TITLE";
    public static final String C_USER_ID = "USER_ID";
    public static final String C_VERSION = "VERSION";

    public static final String[] PRIMARY_KEY = {C_ID};

    public static final String[] ALL_COLUMNS = {C_CONTENT, C_CREATION_TIME, C_ID, C_TITLE, C_USER_ID, C_VERSION};

    public PostRowBuilder(String... identifierColumns) {
        super(TABLE_NAME, identifierColumns);
        setAllColumnNames(ALL_COLUMNS);
        addDefaultValue(C_VERSION, new Long("0"));
        addDefaultValue(C_ID, new Long("0"));
        addDefaultValue(C_TITLE, "");
    }

    public final PostRowBuilder Content (String value) {
        with(C_CONTENT, value);
        return this;
    }

    public final PostRowBuilder CreationTime (Timestamp value) {
        with(C_CREATION_TIME, value);
        return this;
    }

    public final PostRowBuilder Id (Number value) {
        with(C_ID, value);
        return this;
    }

    public final PostRowBuilder Title (String value) {
        with(C_TITLE, value);
        return this;
    }

    public final PostRowBuilder UserId (Number value) {
        with(C_USER_ID, value);
        return this;
    }

    public final PostRowBuilder Version (Number value) {
        with(C_VERSION, value);
        return this;
    }


    public static PostRowBuilder newPost() {
        return new PostRowBuilder(PRIMARY_KEY);
    }

    public static PostRowBuilder newPost(String... identifierColumns) {
        return new PostRowBuilder(identifierColumns);
    }

}
