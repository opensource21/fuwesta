package de.ppi.samples.fuwesta.frontend;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.ppi.fuwesta.spring.mvc.bind.ServletBindingService;
import de.ppi.fuwesta.spring.mvc.util.PageWrapper;
import de.ppi.fuwesta.spring.mvc.util.ResourceNotFoundException;
import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.model.Tag;
import de.ppi.samples.fuwesta.model.User;
import de.ppi.samples.fuwesta.service.api.PostService;

/**
 * Controller for Create, Read, Update and Delete for the model Post.
 * 
 */
@Controller()
public class PostCRUDController {

    /**
     * The default view.
     */
    private static final String POST_FORM = "example/post/postform";

    /**
     * The partial view.
     */
    private static final String PARTIAL_POST_FORM =
            "example/post/partialpostform";

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(PostCRUDController.class);

    /**
     * Small service which helps to bind requestdata to an object.
     */
    @Resource
    private ServletBindingService servletBindingService;

    /**
     * The PostService instance.
     */
    @Resource
    private PostService postService;

    /**
     * The generic validator.
     */
    @Resource
    private Validator mvcValidator;

    /**
     * List all posts.
     * 
     * @param model the model.
     * @param pageRequest attributes for pagination.
     * @return String which defines the next page.
     */
    @RequestMapping(value = { URL.Post.HOME, URL.Post.LIST },
            method = RequestMethod.GET)
    public String list(Model model, Pageable pageRequest) {
        final PageWrapper<Post> postList =
                new PageWrapper<Post>(postService.getPost(pageRequest),
                        URL.Post.LIST);
        if (postList.getSize() == 0) {
            LOG.info("No post found redirect to create");
            return URL.redirect(URL.Post.CREATE);
        }
        model.addAttribute("pageRequest", pageRequest);
        model.addAttribute("postList", postList);
        return "example/post/list";
    }

    /**
     * Create a new post form.
     * 
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Post.CREATE, method = RequestMethod.GET)
    public String create(Model model) {
        if (postService.getNrOfPosts() == 0) {
            model.addAttribute("message", "post.list_empty");
        }
        addStandardModelData(new Post(), URL.Post.CREATE, false,
                postService.getAllUsers(), postService.getAllActiveTags(),
                model);
        return POST_FORM;
    }

    /**
     * Insert a new post.
     * 
     * @param post the post.
     * @param result the binding result.
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Post.CREATE, method = RequestMethod.POST)
    public String insert(@ModelAttribute("newPost") Post post,
            BindingResult result, Model model) {
        mvcValidator.validate(post, result);
        if (result.hasErrors()) {
            addStandardModelData(post, URL.Post.CREATE, false,
                    postService.getAllUsers(), postService.getAllActiveTags(),
                    model);
            return POST_FORM;
        }
        LOG.debug("Create Post: " + post);
        postService.save(post);
        return URL.redirect(URL.Post.LIST);
    }

    /**
     * Create confirmation for deleting a post.
     * 
     * @param postId the Id of the post.
     * @param model the datamodel.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Post.DELETE, method = RequestMethod.GET)
    public String deleteConfirm(@PathVariable(URL.Post.P_POSTID) Long postId,
            Model model) {
        LOG.debug("Confirm delete PostId: " + postId);
        model.addAttribute("deleteURL", URL.filledURL(URL.Post.DELETE, postId));
        model.addAttribute("cancelURL", URL.filledURL(URL.Post.LIST));
        return "example/confirmDelete";
    }

    /**
     * Delete a post.
     * 
     * @param postId the Id of the post.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Post.DELETE, method = { RequestMethod.DELETE,
            RequestMethod.POST })
    public String delete(@PathVariable(URL.Post.P_POSTID) Long postId) {
        LOG.debug("Delete PostId: " + postId);
        postService.delete(postId);
        LOG.debug("Deleted PostId: " + postId);
        return URL.redirect(URL.Post.LIST);
    }

    /**
     * Show a post.
     * 
     * @param post der Post.
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Post.SHOW, method = RequestMethod.GET)
    public String show(@PathVariable(URL.Post.P_POSTID) Post post, Model model) {
        LOG.debug("Show PostId: " + post.getId());
        addStandardModelData(post, URL.Post.SHOW, true,
                postService.getAllUsers(), postService.getAllActiveTags(),
                model);
        return POST_FORM;
    }

    /**
     * Edit a post.
     * 
     * @param postId the Id of the post.
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Post.EDIT, method = RequestMethod.GET)
    public String
            edit(@PathVariable(URL.Post.P_POSTID) Long postId, Model model) {
        LOG.debug("Edit PostId: " + postId);
        Post post = postService.read(postId);
        if (post == null) {
            throw new ResourceNotFoundException("Post with id " + postId
                    + " doesn't exist.");

        }
        List<Tag> allTags = getAllTags(post);
        addStandardModelData(post, URL.filledURL(URL.Post.EDIT, post.getId()),
                false, postService.getAllUsers(), allTags, model);
        return POST_FORM;
    }

    /**
     * Edit a post partial, i.e. the simple fields.
     * 
     * @param postId the Id of the post.
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Post.PARTIALEDIT, method = RequestMethod.GET)
    public String editSimpleFields(
            @PathVariable(URL.Post.P_POSTID) Long postId, Model model) {
        LOG.debug("Edit partial PostId: " + postId);
        Post post = postService.read(postId);
        if (post == null) {
            throw new ResourceNotFoundException("Post with id " + postId
                    + " doesn't exist.");

        }
        addStandardModelData(post,
                URL.filledURL(URL.Post.PARTIALEDIT, post.getId()), false, null,
                null, model);
        return PARTIAL_POST_FORM;
    }

    /**
     * Get all active tags and tags which are used in the current post.
     * 
     * @param post the current post.
     * @return all active tags and tags which are used in the current post.
     */
    private List<Tag> getAllTags(Post post) {
        List<Tag> allTags = postService.getAllActiveTags();
        if (post != null && !CollectionUtils.isEmpty(post.getTags())) {
            for (Tag tag : post.getTags()) {
                if (!allTags.contains(tag)) {
                    allTags.add(tag);
                }
            }
        }
        return allTags;
    }

    /**
     * Update the post.
     * 
     * @param post the post.
     * @param result the bindingsresult.
     * @param model the model
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Post.EDIT, method = RequestMethod.POST)
    public String update(@ModelAttribute("post") Post post,
            BindingResult result, Model model) {
        mvcValidator.validate(post, result);
        if (result.hasErrors()) {
            final List<Tag> allTags = getAllTags(post);
            addStandardModelData(post,
                    URL.filledURL(URL.Post.EDIT, post.getId()), false,
                    postService.getAllUsers(), allTags, model);
            return POST_FORM;
        }
        LOG.debug("Update Post: " + post);
        postService.save(post);

        return URL.redirect(URL.Post.LIST);
    }

    /**
     * Update the post.
     * 
     * @param post the post.
     * @param request the request with the data.
     * @param model the model
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Post.PARTIALEDIT, method = RequestMethod.POST)
    public String updatePartial(@RequestParam("id") Post post,
            HttpServletRequest request, Model model) {
        if (servletBindingService.bindAndValidate(request, model, post, "post")
                .hasErrors()) {
            addStandardModelData(post,
                    URL.filledURL(URL.Post.PARTIALEDIT, post.getId()), false,
                    null, null, model);
            return PARTIAL_POST_FORM;
        }
        LOG.debug("Update Post: " + post);
        postService.save(post);

        return URL.redirect(URL.Post.LIST);
    }

    /**
     * Adds the standard model data.
     * 
     * @param post the post
     * @param url the action URL.
     * @param disabled true if the data should be only show.
     * @param userList list of all user.
     * @param tagList list of all tags.
     * @param model the model
     */
    private void addStandardModelData(Post post, String url, boolean disabled,
            List<User> userList, List<Tag> tagList, Model model) {
        LOG.info("Post: {}", post);
        if (post == null) {
            throw new ResourceNotFoundException();
        }

        model.addAttribute("post", post);
        model.addAttribute("disabled", Boolean.valueOf(disabled));
        model.addAttribute("url", url);
        model.addAttribute("users", userList);
        model.addAttribute("tags", tagList);
    }

}
