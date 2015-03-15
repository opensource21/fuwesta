package de.ppi.samples.fuwesta.model;

/**
 * Sex.
 *
 */
public enum Sex {

    /** The male. */
    MALE('m'),
    /** The female. */
    FEMALE('f'),
    /** The other. */
    OTHER('o');

    /** Characteristic identifier. */
    private final Character id;

    /**
     * Initiates an object of type Sex.
     *
     * @param id the identifier.
     */
    private Sex(char id) {
        this.id = Character.valueOf(id);
    }

    /**
     * Get the characteristic id.
     *
     * @return the id.
     */
    public Character getId() {
        return id;
    }

    /**
     * Parse the identifier and delivers the enum.
     *
     * @param id the identifier.
     * @return the enum.
     */
    public static Sex parse(Character id) {
        if (id == null) {
            return null;
        }
        switch (id.charValue()) {
        case 'm':
        case 'M':
            return MALE;
        case 'f':
        case 'F':
            return FEMALE;
        case 'o':
        case 'O':
            return OTHER;
        default:
            throw new IllegalStateException("Unknow sex-id: " + id + ".");
        }
    }

}
