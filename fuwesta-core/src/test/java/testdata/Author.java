package testdata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The Class Author.
 */
@Entity
public class Author {

    /**
     * The identifier of the entity.
     */
    @Id
    @GeneratedValue()
    @Column(unique = true, nullable = false)
    private Long id;

    /** The name. */
    private String name;

    /** The books. */
    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();

    /** The birthday. */
    private Date birthday;

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
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the books.
     * 
     * @return the books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the books.
     * 
     * @param books the new books
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Gets the birthday.
     * 
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday.
     * 
     * @param birthday the new birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

}
