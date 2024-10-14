package is.hi.hbv501g.team20.Controllers;

import is.hi.hbv501g.team20.Persistence.Entities.User;
import is.hi.hbv501g.team20.Services.Implementations.LoginServiceImplementation;
import is.hi.hbv501g.team20.Services.Implementations.SettingsServiceImplementation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class SettingsController {

    private final SettingsServiceImplementation settingsService;

    @Autowired
    public SettingsController(SettingsServiceImplementation settingsService) {
        this.settingsService = settingsService;
    }

    //@RequestMapping(value = "/user", method = {RequestMethod.PATCH, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    //public String changePrivacy(HttpSession session, @RequestParam boolean privacy, Model model) {

    //    User user = (User) session.getAttribute("user"); // Retrieve user from session

    //    if (user == null) {
    //        model.addAttribute("error", "User not logged in.");
    //        session.invalidate();  // Invalidate session if no user is found
    //        return "login";  // Redirect to login page
    //    }
//
    //    if (!privacy) {
    //        privacy = false;
    //    }

        // Update privacy settings
    //    user.setPrivacy(privacy);
    //    settingsService.save(user);
//
    //    model.addAttribute("user", user);
    //    return "settings";  // Redirect to settings page with success message
    //}

    @PostMapping("/settings/privacy")
    public String changePrivacy(@RequestParam boolean privacy, HttpSession session, Model model) {

        User user = (User) session.getAttribute("user"); // Retrieve user from session

        if (user == null) {
            model.addAttribute("error", "User not logged in.");
            session.invalidate();  // Invalidate session if no user is found
            return "login";  // Redirect to login page
        }

        // Update the user's privacy setting and save
        user = settingsService.updatePrivacy(user.getId(), privacy);

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
        settingsService.delete(user);
        session.invalidate(); // Invalidate session after account deletion

        // Redirect to home
        return "redirect:/home";
    }

}
