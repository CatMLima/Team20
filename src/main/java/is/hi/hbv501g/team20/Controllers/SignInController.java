package is.hi.hbv501g.team20.Controllers;

import is.hi.hbv501g.team20.Persistence.Entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignInController {

    @RequestMapping("/api/signup")
    public ResponseEntity<User> createUser(@RequestBody User user){
        System.out.println("You tried to create a user.");
        return null;
    }

    @GetMapping("/sign-up")
    public String getSignUpPage(){
        return "signup";
    }
}
