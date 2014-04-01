package de.ppi.fuwesta.jpa.helper;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import de.ppi.fuwesta.oval.validation.OptimisticLock;

/**
 * Basic Model which contains the id and version.
 * 
 */
@MappedSuperclass
@OptimisticLock
public class VersionedModel {

    /**
     * The identifier of the entity.
     */
    @Id
    @GeneratedValue()
    @Column(unique = true, nullable = false)
    private Long id;

    /**
     * The version of the entity.
     */
    @Version
    @Column(nullable = false)
    private Long version;

    @Transient
    private transient boolean optimisiticLockingViolated = false;

    /**
     * Gets the identifier of the entity.
     * 
     * @return the identifier of the entity
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the identifier of the entity.
     * 
     * @param id the identifier of the entity
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the version of the entity.
     * 
     * @return the version of the entity
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version of the entity.
     * 
     * @param newVersion the new version of the entity
     */
    public void setVersion(final Long newVersion) {
        if (newVersion != null) {
            if (version != null && version.longValue() > newVersion.longValue()) {
                optimisiticLockingViolated = true;
            } else {
                version = newVersion;
            }
        }
    }
}
