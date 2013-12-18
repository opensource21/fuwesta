// HomeController.java
//
// (c) SZE-Development-Team

package de.ppi.samples.fuwesta.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Simple start-controller.
 * 
 */
@Controller
public class HomeController {

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(HomeController.class);

    /**
     * Show the home page.
     * 
     * @return the logical view-name.
     */
    @RequestMapping({ "/", URL.HOME })
    public String showHomePage() {
        return "example/home";
    }

    /**
     * Show an edit view for a user.
     * 
     * @param userId the id of the user.
     * @param model the model where to safe the data for the view.
     * @return the logical view name.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/example/{userId}")
    public String editUser(@PathVariable("userId") String userId, Model model) {
        LOG.info("UserId: {}", userId);
        model.addAttribute("userId", userId);
        return "example/editBootstrap";
    }

    /**
     * Shows the login-view.
     * 
     * @return the login-view.
     */
    @RequestMapping(value = URL.LOGIN, method = RequestMethod.GET)
    public String loginPage() {
        return "example/login";
    }
}
