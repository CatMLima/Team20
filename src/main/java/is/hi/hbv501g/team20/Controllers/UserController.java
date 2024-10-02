package is.hi.hbv501g.team20.Controllers;

import is.hi.hbv501g.team20.Persistence.Entities.User;
import is.hi.hbv501g.team20.Services.Implementations.LoginServiceImplementation;
import is.hi.hbv501g.team20.Services.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

    private LoginServiceImplementation loginService;

    @Autowired
    public UserController(LoginServiceImplementation loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/api/uploadProfilePicture")
    public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile profilePicture,
                                       HttpSession session, Model model) {
        // Find the user by ID (you might use a service here to get the user)
        User user = (User) session.getAttribute("user");
        System.out.println(user.toString());

        if (!profilePicture.isEmpty()) {
            try{
                byte[] bytes = profilePicture.getBytes();
                user.setProfilePicture(bytes);
                loginService.save(user);
                model.addAttribute("user", user);
                return "user";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error uploading profile picture";
            }
        }

        return "No picture uploaded";
    }

    /*
    Still working on this - Cat
     */
    @GetMapping("/user/{id}/profilePicture")
    public ResponseEntity<byte[]> getProfilePicture(Session session, Model model){
        User user = (User) session.getAttribute("user");
        byte[] picture = user.getProfilePicture();

        if (picture != null){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(picture, headers, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
