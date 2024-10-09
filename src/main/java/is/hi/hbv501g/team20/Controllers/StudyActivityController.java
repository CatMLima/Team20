package is.hi.hbv501g.team20.Controllers;

import is.hi.hbv501g.team20.Persistence.Entities.StudyActivity;
import is.hi.hbv501g.team20.Persistence.Entities.User;
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

    @Autowired
    public StudyActivityController(StudyActivityService studyActivityService) {
        this.studyActivityService = studyActivityService;
    }

    @RequestMapping(value = "/getsa", method = RequestMethod.GET)
    public String createActivityGet(StudyActivity studyActivity){
        return "newStudyActivity";
    }
    @RequestMapping(value = "/postsa", method = RequestMethod.POST)
    public String createActivityPOST(StudyActivity studyActivity, BindingResult result, Model model){
        // If the information given was faulty, return to newItem page to try again.
        if(result.hasErrors()){
            return "newStudyActivity";
        }
        // Otherwise, save the new item and return to the front page.
        studyActivityService.save(studyActivity);
        return "redirect:/home";
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
    @RequestMapping("/create-study")
    public String startStudyActivity() {
        return "studyactivity";
    }
    //End of feed page stuff
}
