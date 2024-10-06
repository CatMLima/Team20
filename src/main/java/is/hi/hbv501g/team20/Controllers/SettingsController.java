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

    private SettingsServiceImplementation settingsService;

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 HttpSession session,
                                 Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            model.addAttribute("error", "User not logged in.");
            return "settings";
        }

        // Add logic here to validate and change the password --> not sure that I know how to do that on the ui
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirmation do not match.");
            return "settings";  // Return the same form page in case of an error
        }

        // Validate password requirements
        if (!newPassword.matches("^(?=.*[a-zA-Z])(?=.*\\d)[A-Za-z\\d]{10,}$")) {
            model.addAttribute("error", "Password must be at least 10 characters long and contain both letters and numbers.");
            return "settings";
        }

        // Implement logic for checking current password (assuming a method exists to validate password)
        if (!user.getPassword().equals(currentPassword)) {  // This should ideally be hashed and checked
            model.addAttribute("error", "Current password is incorrect.");
            return "settings";
        }

        // Update password and save
        user.setPassword(newPassword);  // This should be hashed before saving
        settingsService.save(user);

        model.addAttribute("success", "Password changed successfully.");
        return "redirect:/settings";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            model.addAttribute("error", "User not logged in.");
            return "settings";
        }

        // Delete the user account
        settingsService.delete(user);
        session.invalidate(); // Invalidate session after account deletion

        // Redirect to an account deletion success page
        return "redirect:/account-deleted-success";
    }

    @PostMapping("/change-privacy")
    public String changePrivacy(@RequestParam boolean privateAccount, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            model.addAttribute("error", "User not logged in.");
            return "settings";
        }

        // Update privacy settings
        user.setPrivacy(privateAccount);
        settingsService.save(user);

        model.addAttribute("success", "Privacy settings updated successfully.");
        return "redirect:/settings";  // Redirect to settings page with success message
    }
}
