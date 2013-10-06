package testdata;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import de.ppi.fuwesta.oval.validation.Unique;

/**
 * The Class Book.
 */
@Entity
public class Book {

    /**
     * The identifier of the entity.
     */
    @Id
    @GeneratedValue()
    @Column(name = "c_id", unique = true, nullable = false)
    private Long id;

    /** The title. */
    @Unique("author")
    @Column(name = "c_title")
    private String title;

    /** The isbn. */
    @Column(unique = true)
    private String isbn;

    /** The author. */
    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;

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
     * @param id the new identifier of the entity
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the author.
     * 
     * @return the author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the author.
     * 
     * @param author the new author
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * 
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the isbn.
     * 
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the isbn.
     * 
     * @param isbn the new isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
