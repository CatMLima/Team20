package is.hi.hbv501g.team20.Controllers;

import is.hi.hbv501g.team20.Persistence.Entities.StudyActivity;
import is.hi.hbv501g.team20.Persistence.Entities.User;
import is.hi.hbv501g.team20.Services.LoginService;
import is.hi.hbv501g.team20.Services.StudyActivityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Controller
public class StudyActivityController {
    private StudyActivityService studyActivityService;
    private LoginService loginService;

    @Autowired
    public StudyActivityController(StudyActivityService studyActivityService, LoginService loginService) {
        this.studyActivityService = studyActivityService;
        this.loginService = loginService;
    }

    // Displays the Create a studyactivity page
    @RequestMapping(value = "/studyactivity-create", method = RequestMethod.GET)
    public String createStudyActivityGet(Model model) {
        model.addAttribute("studyactivity", new StudyActivity());
        return "studyactivity-create";
    }

    // Assigns a user to the studyactivity as well as a date
    // start and end variables are for a timer that will be implemented later
    // creates a studyactivity and saves it to the database
    @RequestMapping(value = "/api/studyactivity-create", method = RequestMethod.POST)
    public String createStudyActivity(HttpSession httpSession, StudyActivity studyActivity, BindingResult result, Model model){

        User user = (User) httpSession.getAttribute("user");
        studyActivity.setUser(user);
        studyActivity.setDate(new Date());
        studyActivity.setStart(LocalTime.now());
        studyActivity.setEnd(LocalTime.now().plusHours(1));

        if(result.hasErrors()){
            return "studyactivity-create";
        }
        studyActivityService.save(studyActivity);
        return "redirect:/feed";
    }

    // deletes a selected studyactivity and removes it from the database
    @GetMapping("/studyactivity-delete/{id}")
    public String deleteStudyActicity (@PathVariable("id") long id, Model model){
        StudyActivity studyActivityToDelete = studyActivityService.findById(id);
        studyActivityService.delete(studyActivityToDelete);
        return "redirect:/feed";
    }

    // displays study activity details
    @RequestMapping(value="/studyactivity-details/{id}", method= RequestMethod.GET)
    public String getStudyActivityDetailsPage(@PathVariable("id") long id, Model model) {
        StudyActivity studyActivity = studyActivityService.findById(id);
        model.addAttribute("studyactivity", studyActivity);
        return "studyactivity-details";
    }

    // Displays a page containing a list of the user's studyactivities
    @RequestMapping(value="/studyactivity-list", method= RequestMethod.GET)
    public String getStudyActivityDetailsPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<StudyActivity> studyActivities = studyActivityService.findByUser(user);
            model.addAttribute("studyactivity", studyActivities);
        }
        return "studyactivity-list";
    }

    // Feed page stuff is here below
    // Displays feed page
    @RequestMapping("/feed")
    public String showFeed(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<StudyActivity> allStudyActivities = studyActivityService.findAllPublicAndUserActivities(user);
        model.addAttribute("studyactivity", allStudyActivities);
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

    // Add a picture to a study activity
    @PostMapping("/uploadActivityPicture")
    public String uploadActivityPicture(@RequestParam("activityPicture") MultipartFile activityPicture, @RequestParam("activityId") Long activityId,
                                       HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        StudyActivity studyActivity = studyActivityService.findById(activityId);

        if (!activityPicture.isEmpty()) {
            try{
                byte[] bytes = activityPicture.getBytes();
                studyActivity.setActivityPicture(bytes);
                studyActivityService.save(studyActivity);
                model.addAttribute("user", user);
                model.addAttribute("activityPicture", studyActivity.getActivityPicture());
                model.addAttribute("studyActivity", studyActivity);
                return "redirect:/studyactivity-details/" + studyActivity.getId();
            } catch (Exception e) {
                e.printStackTrace();
                return "Error uploading activity picture";
            }
        }

        return "No picture uploaded";
    }

    // Retrieve and display the study activity picture
    @GetMapping("/activity/{id}/activityPicture")
    public ResponseEntity<byte[]> getActivityPicture(@PathVariable Long id){
        StudyActivity activity = studyActivityService.findById(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(activity.getActivityPicture());
    }

    // Method for handling the search functionality
    @RequestMapping(value = "/feed-search", method = RequestMethod.GET)
    public String searchStudyActivities(@RequestParam("query") String query, Model model) {
        List<StudyActivity> searchResults = studyActivityService.searchByTitleOrDescription(query);
        model.addAttribute("studyactivity", searchResults);
        return "feed";
    }

    //End of feed page stuff
}
