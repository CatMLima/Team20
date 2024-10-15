package is.hi.hbv501g.team20.Controllers;

import is.hi.hbv501g.team20.Persistence.Entities.User;
import is.hi.hbv501g.team20.Services.Implementations.LoginServiceImplementation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    private LoginServiceImplementation loginService;

    @Autowired
    public LoginController(LoginServiceImplementation loginService){
        this.loginService = loginService;
    }

    // Checks if the user is a new one before allowing them to create an account and saving it to the database.
    @RequestMapping(value="/api/signup", method= RequestMethod.POST)
    public String signupPOST(User user, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "redirect:/signup";
        }

        User alreadyExisting = loginService.findByEmail(user.getEmail());
        if (alreadyExisting == null) {
            loginService.save(user);
        }
        return "redirect:/login";

    }

    // Displays the sign up page
    @RequestMapping(value="/sign-up", method= RequestMethod.GET)
    public String getSignUpPage(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    // Displays the login page
    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String getLogInPage(){ return "login"; }


    // When an existing user logs in, we add them as a session and model attribute while they are online.
    @RequestMapping(value="/api/login", method=RequestMethod.POST)
    public String loginPOST(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session){
        if (result.hasErrors()) {
            return "login";
        }

        User existing = loginService.findByEmail(user.getEmail());

        if (existing != null) {
            session.setAttribute("user", existing);
            model.addAttribute("user", existing);
            return "redirect:/feed";
        }
        return "redirect:/";
    }


    // To move to the user page, control then passed to the UserController
    @RequestMapping(value="/user", method=RequestMethod.GET)
    public String getUserPage(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            System.out.println("User in session: " + user.getName());
            return "user";
        }
        return "login";
    }
}
