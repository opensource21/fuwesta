package de.ppi.samples.fuwesta.frontend;

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
import de.ppi.samples.fuwesta.model.Sex;
import de.ppi.samples.fuwesta.model.User;
import de.ppi.samples.fuwesta.service.api.UserService;

/**
 * Controller for Create, Read, Update and Delete for the model User.
 * 
 */
@Controller()
public class UserCRUDController {

    /**
     * View which is used as form.
     */
    private static final String USER_FORM = "example/user/userform";

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserCRUDController.class);

    /**
     * The UserService instance.
     */
    @Resource(name = "userService")
    private UserService userService;

    /**
     * The {@link Validator}.
     */
    @Resource
    private Validator validator;

    /**
     * List all users.
     * 
     * @param model the model.
     * @param pageRequest attributes about paginating.
     * @return String which defines the next page.
     */
    @RequestMapping(value = { URL.User.HOME, URL.User.LIST },
            method = RequestMethod.GET)
    public String
            list(Model model,
                    @PageableDefault(page = 0, value = 5, sort = { "userId" },
                            direction = Direction.ASC) Pageable pageRequest) {
        final PageWrapper<User> userList =
                new PageWrapper<User>(userService.getUser(pageRequest),
                        URL.User.LIST);
        if (userList.getSize() == 0) {
            LOG.info("No user found redirect to create");
            return URL.redirect(URL.User.CREATE);
        }
        model.addAttribute("pageRequest", pageRequest);
        model.addAttribute("userList", userList);
        // model.addAttribute("viewUrl", URL.User.VIEW);
        // model.addAttribute("editUrl", URL.User.EDIT);
        // model.addAttribute("deleteUrl", URL.User.DELETE);
        return "example/user/list";
    }

    /**
     * Create a new user form.
     * 
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.User.CREATE, method = RequestMethod.GET)
    public String create(Model model) {
        if (userService.getNrOfUsers() == 0) {
            model.addAttribute("message", "user.list_empty");
        }
        addStandardModelData(new User(), URL.User.CREATE, false, model);
        return USER_FORM;
    }

    /**
     * Insert a new user.
     * 
     * @param user the user.
     * @param result the bindingsresult.
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.User.CREATE, method = RequestMethod.POST)
    public String insert(@ModelAttribute("user") User user,
            BindingResult result, Model model) {
        validator.validate(user, result);
        if (result.hasErrors()) {
            addStandardModelData(user, URL.User.CREATE, false, model);
            return USER_FORM;
        }
        LOG.debug("Create User: " + user);
        userService.save(user);
        return URL.redirect(URL.User.LIST);
    }

    /**
     * Create confirmation for deleting a user.
     * 
     * @param userId the Id of the user.
     * @param model the datamodel.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.User.DELETE, method = RequestMethod.GET)
    public String deleteConfirm(@PathVariable(URL.User.P_USERID) Long userId,
            Model model) {
        LOG.debug("Confirm delete UserId: " + userId);
        model.addAttribute("deleteURL", URL.filledURL(URL.User.DELETE, userId));
        model.addAttribute("cancelURL", URL.filledURL(URL.User.LIST));
        return "example/confirmDelete";
    }

    /**
     * Delete a user.
     * 
     * @param userId the Id of the user.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.User.DELETE, method = { RequestMethod.DELETE,
            RequestMethod.POST })
    public String delete(@PathVariable(URL.User.P_USERID) Long userId) {
        LOG.debug("Delete UserId: " + userId);
        userService.delete(userId);
        LOG.debug("Deleted UserId: " + userId);
        return URL.redirect(URL.User.LIST);
    }

    /**
     * Show a user.
     * 
     * @param userId the Id of the user.
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.User.SHOW, method = RequestMethod.GET)
    public String
            show(@PathVariable(URL.User.P_USERID) Long userId, Model model) {
        LOG.debug("Show UserId: " + userId);

        addStandardModelData(userService.read(userId), URL.User.LIST, true,
                model);
        return USER_FORM;
    }

    /**
     * Edit a user.
     * 
     * @param userId the Id of the user.
     * @param model the model.
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.User.EDIT, method = RequestMethod.GET)
    public String
            edit(@PathVariable(URL.User.P_USERID) Long userId, Model model) {
        LOG.debug("Edit UserId: " + userId);
        addStandardModelData(userService.read(userId),
                URL.filledURL(URL.User.EDIT, userId), false, model);
        return USER_FORM;
    }

    /**
     * Adds the standard model data.
     * 
     * @param user the user
     * @param disabled true if the data should be only show.
     * @param url the action URL.
     * @param model the model
     */
    private void addStandardModelData(User user, String url, boolean disabled,
            Model model) {
        LOG.info("User: {}", user);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        model.addAttribute("user", user);
        model.addAttribute("posts", userService.getPostingSelectOptions());
        model.addAttribute("sexList", Sex.values());
        model.addAttribute("disabled", Boolean.valueOf(disabled));
        model.addAttribute("url", url);
    }

    /**
     * Update a user.
     * 
     * @param user the user.
     * @param result the bindings result.
     * @param model the model
     * @return String which defines the next page.
     */
    @RequestMapping(value = URL.User.EDIT, method = RequestMethod.POST)
    public String update(@ModelAttribute("user") User user,
            BindingResult result, Model model) {
        validator.validate(user, result);
        if (result.hasErrors()) {
            addStandardModelData(user,
                    URL.filledURL(URL.User.EDIT, user.getId()), false, model);
            return USER_FORM;
        }
        LOG.debug("Update User: " + user);
        userService.save(user);
        return URL.redirect(URL.User.LIST);
    }
}
