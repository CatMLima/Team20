package is.hi.hbv501g.team20.Controllers;

import is.hi.hbv501g.team20.Persistence.Entities.User;
import is.hi.hbv501g.team20.Services.Implementations.LoginServiceImplementation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private LoginServiceImplementation loginService;

    @Autowired
    public LoginController(LoginServiceImplementation loginService){
        this.loginService = loginService;
    }

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

    @RequestMapping(value="/sign-up", method= RequestMethod.GET)
    public String getSignUpPage(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String getLogInPage(){ return "login"; }

    @RequestMapping(value="/api/login", method=RequestMethod.POST)
    public String loginPOST(@ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session){
        if (result.hasErrors()) {
            return "login";
        }

        User existing = loginService.findByEmail(user.getEmail());
        System.out.println(existing.getEmail());
        if (existing != null) {
            session.setAttribute("user", existing);
            model.addAttribute("user", existing);
            return "redirect:/feed";
        }
        return "redirect:/";
    }


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

    @PostMapping("/settings/privacy")
    public String changePrivacy(@RequestParam int privacy, HttpSession session, Model model) {

        User user = (User) session.getAttribute("user"); // Retrieve user from session

        if (user == null) {
            model.addAttribute("error", "User not logged in.");
            return "login";  // Redirect to login page
        }

        // Log the user and privacy values for debugging
        System.out.println("User before update: " + user.getName());
        System.out.println("Privacy before update: " + user.getPrivacy());

        // Update the user's privacy setting and save
        user = loginService.updatePrivacy(user.getId(), privacy);

        // Log the updated user object
        System.out.println("User after update: " + user.getName());
        System.out.println("Privacy after update: " + user.getPrivacy());

        // Update session with the latest user data
        session.setAttribute("user", user);

        // Show the updated settings
        model.addAttribute("user", user);
        model.addAttribute("message", "Privacy setting updated successfully.");
        return "settings"; // Return to settings form with updated values
    }



    @PostMapping("/delete-account")
    public String deleteAccount(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            model.addAttribute("error", "User not logged in.");
            return "login";
        }

        // Delete the user account
        loginService.delete(user);
        session.invalidate(); // Invalidate session after account deletion

        // Redirect to home
        return "redirect:/home";
    }

}
