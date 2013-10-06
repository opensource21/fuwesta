package de.ppi.samples.fuwesta.frontend;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.ppi.fuwesta.spring.mvc.util.PageWrapper;
import de.ppi.fuwesta.spring.mvc.util.ResourceNotFoundException;
import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.model.Tag;
import de.ppi.samples.fuwesta.service.api.TagService;

/**
 * Controller for Create, Read, Update and Delete for the model Tag.
 * 
 */
@Controller()
public class TagCRUDController {

    /**
     * The default view.
     */
    private static final String TAG_FORM = "example/tag/tagform";

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(TagCRUDController.class);

    /**
     * The TagService instance.
     */
    @Resource
    private TagService tagService;

    /**
     * The frontend validator.
     */
    @Resource
    private Validator validator;

    /**
     * List all tags.
     * 
     * @param model the model.
     * @param pageRequest attributes about pagination.
     * @return String which defines the next page.
     */
    @RequestMapping(value = { URL.Tag.HOME, URL.Tag.LIST },
            method = RequestMethod.GET)
    public String list(Model model, @PageableDefault(page = 0, value = 5,
            sort = { "name" }, direction = Direction.ASC) Pageable pageRequest) {
        final PageWrapper<Tag> tagList =
                new PageWrapper<Tag>(tagService.getTag(pageRequest),
                        URL.Tag.LIST);
        if (tagList.getSize() == 0) {
            LOG.info("No tag found redirect to create");
            return URL.redirect(URL.Tag.CREATE);
        }
        model.addAttribute("pageRequest", pageRequest);
        model.addAttribute("tagList", tagList);
        return "example/tag/list";
    }

    /**
     * Create a new tag form.
     * 
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Tag.CREATE, method = RequestMethod.GET)
    public String create(Model model) {
        if (tagService.getNrOfTags() == 0) {
            model.addAttribute("message", "tag.list_empty");
        }
        addStandardModelData(new Tag(), URL.Tag.CREATE, false,
                tagService.getPostingSelectOptions(), model);

        return TAG_FORM;
    }

    /**
     * Insert the new tag.
     * 
     * @param tag the tag.
     * @param result the bindingsresult.
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Tag.CREATE, method = RequestMethod.POST)
    public String insert(@ModelAttribute("newTag") Tag tag,
            BindingResult result, Model model) {
        validator.validate(tag, result);
        if (result.hasErrors()) {
            addStandardModelData(new Tag(), URL.Tag.CREATE, false,
                    tagService.getPostingSelectOptions(), model);
            return TAG_FORM;
        }
        LOG.debug("Create Tag: " + tag);
        tagService.save(tag);
        return URL.redirect(URL.Tag.LIST);
    }

    /**
     * Create confirmation for deleting a tag.
     * 
     * @param tagId the Id of the tag.
     * @param model the datamodel.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Tag.DELETE, method = RequestMethod.GET)
    public String deleteConfirm(@PathVariable(URL.Tag.P_TAGID) Long tagId,
            Model model) {
        LOG.debug("Confirm delete TagId: " + tagId);
        model.addAttribute("deleteURL", URL.filledURL(URL.Tag.DELETE, tagId));
        model.addAttribute("cancelURL", URL.filledURL(URL.Tag.LIST));
        return "example/confirmDelete";
    }

    /**
     * Delete a tag.
     * 
     * @param tagId the Id of the tag.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Tag.DELETE, method = { RequestMethod.DELETE,
            RequestMethod.POST })
    public String delete(@PathVariable(URL.Tag.P_TAGID) Long tagId) {
        LOG.debug("Delete TagId: " + tagId);
        tagService.delete(tagId);
        LOG.debug("Deleted TagId: " + tagId);
        return URL.redirect(URL.Tag.LIST);
    }

    /**
     * Show a tag.
     * 
     * @param tagId the Id of the tag.
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Tag.SHOW, method = RequestMethod.GET)
    public String show(@PathVariable(URL.Tag.P_TAGID) Long tagId, Model model) {
        LOG.debug("Show TagId: " + tagId);
        model.addAttribute("posts", tagService.getPostingSelectOptions());
        model.addAttribute("tag", tagService.read(tagId));
        addStandardModelData(tagService.read(tagId), URL.Tag.CREATE, true,
                tagService.getPostingSelectOptions(), model);
        return TAG_FORM;
    }

    /**
     * Edit a tag.
     * 
     * @param tagId the Id of the tag.
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Tag.EDIT, method = RequestMethod.GET)
    public String edit(@PathVariable(URL.Tag.P_TAGID) Long tagId, Model model) {
        LOG.debug("Edit TagId: " + tagId);
        addStandardModelData(tagService.read(tagId), URL.Tag.CREATE, false,
                tagService.getPostingSelectOptions(), model);
        return TAG_FORM;
    }

    /**
     * Update a tag.
     * 
     * @param tag the tag.
     * @param result the bindings result.
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.Tag.EDIT, method = RequestMethod.POST)
    public String update(@ModelAttribute("tag") Tag tag, BindingResult result,
            Model model) {
        validator.validate(tag, result);
        if (result.hasErrors()) {
            addStandardModelData(tag, URL.Tag.EDIT, false,
                    tagService.getPostingSelectOptions(), model);

            return TAG_FORM;
        }
        LOG.debug("Update Tag: " + tag);
        tagService.save(tag);
        return URL.redirect(URL.Tag.LIST);
    }

    /**
     * Adds the standard model data.
     * 
     * @param tag the post
     * @param url the action URL.
     * @param disabled true if the data should be only show.
     * @param posts the list of posts.
     * @param model the model
     */
    private void addStandardModelData(Tag tag, String url, boolean disabled,
            List<Post> posts, Model model) {
        LOG.info("Tag: {}", tag);
        if (tag == null) {
            throw new ResourceNotFoundException();
        }
        model.addAttribute("tag", tag);
        model.addAttribute("disabled", Boolean.valueOf(disabled));
        model.addAttribute("url", url);
        model.addAttribute("posts", posts);
    }
}
