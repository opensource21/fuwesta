// PostService.java
//
// (c) SZE-Development-Team

package de.ppi.samples.fuwesta.service.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.model.Tag;
import de.ppi.samples.fuwesta.model.User;

/**
 * Service that handles work which must be done for postings.
 * 
 * @author niels
 * 
 */
public interface PostService {

    /**
     * Deliver a page of posts.
     * 
     * @param page information about pagination.
     * @return the page of posts.
     */
    Page<Post> getPost(Pageable page);

    /**
     * Save the given post.
     * 
     * @param post the post object.
     * @return the post object which may changed.
     * 
     */
    Post save(Post post);

    /**
     * Read the post.
     * 
     * @param postId the ID of the post object.
     * @return the post object.
     * 
     */
    Post read(Long postId);

    /**
     * Delete the post.
     * 
     * @param postId the ID of the post object.
     * 
     */
    void delete(Long postId);

    /**
     * Return the number of posts.
     * 
     * @return number of posts.
     */
    long getNrOfPosts();

    /**
     * Get all users.
     * 
     * @return List of users.
     */
    List<User> getAllUsers();

    /**
     * Get all active tags.
     * 
     * @return List of tags.
     */
    List<Tag> getAllActiveTags();

}
