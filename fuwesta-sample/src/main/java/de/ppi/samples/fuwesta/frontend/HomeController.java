// HomeController.java
//

package de.ppi.samples.fuwesta.frontend;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
     * Show the login-view.
     *
     * @param username optional the username
     * @param password optional the password
     * @param rememberMe optional the rememberMe flag
     * @param model the Spring model
     * @return the login-view-name
     */
    //J-
    @RequestMapping(value = URL.Auth.LOGIN, method = { RequestMethod.GET,
            RequestMethod.POST })
    public String loginPage(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "remember-me", required = false) Boolean rememberMe,
            Model model) {
        if (StringUtils.isEmpty(username)) {
            model.addAttribute("username", SecurityUtils.getSubject()
                    .getPrincipal());
        } else {
            model.addAttribute("username", username);
        }
        model.addAttribute("password", password);
        model.addAttribute("rememberMe", rememberMe);

        return "example/login";
    }
}
