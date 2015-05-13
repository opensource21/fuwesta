package de.ppi.samples.fuwesta.dbunit.rowbuilder;

import org.dbunit.dataset.builder.BasicDataRowBuilder;
import org.dbunit.validator.IValidator;

public class TUserRowBuilder extends BasicDataRowBuilder {

    public static final String TABLE_NAME = "T_USER";

    public static final String C_FIRST_NAME = "FIRST_NAME";
    public static final String C_ID = "ID";
    public static final String C_LAST_NAME = "LAST_NAME";
    public static final String C_SEX = "SEX";
    public static final String C_USER_ID = "USER_ID";
    public static final String C_VERSION = "VERSION";

    public static final String[] PRIMARY_KEY = {C_ID};

    public static final String[] ALL_COLUMNS = {C_FIRST_NAME, C_ID, C_LAST_NAME, C_SEX, C_USER_ID, C_VERSION};

    public TUserRowBuilder(String... identifierColumns) {
        super(TABLE_NAME, identifierColumns);
        setAllColumnNames(ALL_COLUMNS);
        addDefaultValue(C_USER_ID, "");
        addDefaultValue(C_VERSION, new Long("0"));
        addDefaultValue(C_ID, new Long("0"));
    }

    public final TUserRowBuilder FirstName (String value) {
        with(C_FIRST_NAME, value);
        return this;
    }

    public final TUserRowBuilder FirstName (IValidator<?> value) {
        with(C_FIRST_NAME, value);
        return this;
    }

    public final TUserRowBuilder Id (Long value) {
        with(C_ID, value);
        return this;
    }

    public final TUserRowBuilder Id (IValidator<?> value) {
        with(C_ID, value);
        return this;
    }

    public final TUserRowBuilder LastName (String value) {
        with(C_LAST_NAME, value);
        return this;
    }

    public final TUserRowBuilder LastName (IValidator<?> value) {
        with(C_LAST_NAME, value);
        return this;
    }

    public final TUserRowBuilder Sex (String value) {
        with(C_SEX, value);
        return this;
    }

    public final TUserRowBuilder Sex (IValidator<?> value) {
        with(C_SEX, value);
        return this;
    }

    public final TUserRowBuilder UserId (String value) {
        with(C_USER_ID, value);
        return this;
    }

    public final TUserRowBuilder UserId (IValidator<?> value) {
        with(C_USER_ID, value);
        return this;
    }

    public final TUserRowBuilder Version (Long value) {
        with(C_VERSION, value);
        return this;
    }

    public final TUserRowBuilder Version (IValidator<?> value) {
        with(C_VERSION, value);
        return this;
    }


    public static TUserRowBuilder newTUser() {
        return new TUserRowBuilder(PRIMARY_KEY);
    }

    public static TUserRowBuilder newTUser(String... identifierColumns) {
        return new TUserRowBuilder(identifierColumns);
    }

}
