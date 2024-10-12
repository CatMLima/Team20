package is.hi.hbv501g.team20.Controllers;

import is.hi.hbv501g.team20.Persistence.Entities.StudyActivity;
import is.hi.hbv501g.team20.Persistence.Entities.User;
import is.hi.hbv501g.team20.Services.LoginService;
import is.hi.hbv501g.team20.Services.StudyActivityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StudyActivityController {
    private StudyActivityService studyActivityService;
    private LoginService loginService;

    @Autowired
    public StudyActivityController(StudyActivityService studyActivityService, LoginService loginService) {
        this.studyActivityService = studyActivityService;
        this.loginService = loginService;
    }

    @RequestMapping(value = "/api/studyactivity-create", method = RequestMethod.GET)
    public String createStudyActivityGet(StudyActivity studyActivity){
        return "studyactivity-details";
    }

    @RequestMapping(value = "/api/studyactivity-create", method = RequestMethod.POST)
    public String createStudyActivity(StudyActivity studyActivity, BindingResult result, Model model){
        if(result.hasErrors()){
            System.out.println(result.getAllErrors());
            return "studyactivity-create";
        }
        studyActivityService.save(studyActivity);
        return "redirect:/studyactivity-details";
    }

    @RequestMapping(value="/studyactivity-details", method= RequestMethod.GET)
    public String getStudyActivityDetailsPage(Model model){
        model.addAttribute("studyacitivity", studyActivityService.findById(1));
        return "studyactivity-details";
    }

    // Feed page stuff is here below
    // Displays feed page
    @RequestMapping("/feed")
    public String showFeed(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "feed";
    }

    // Displays user profile page
    @RequestMapping("/profile")
    public String showProfile() {
        return "user";
    }

    // Create a study activity
    @RequestMapping("/studyactivity-create")
    public String startStudyActivity() {
        return "studyactivity-create";
    }

    //End of feed page stuff
}
