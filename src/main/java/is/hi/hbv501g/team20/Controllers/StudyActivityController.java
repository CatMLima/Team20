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

    @RequestMapping(value = "/studyactivity-create", method = RequestMethod.GET)
    public String createStudyActivityGet(Model model) {
        model.addAttribute("studyactivity", new StudyActivity());
        return "studyactivity-create";
    }

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
// displays study activity details
    @RequestMapping(value="/studyactivity-details/{id}", method= RequestMethod.GET)
    public String getStudyActivityDetailsPage(@PathVariable("id") long id, Model model, HttpSession httpSession) {
        StudyActivity studyActivity = studyActivityService.findById(id);
        model.addAttribute("studyactivity", studyActivity);
        return "studyactivity-details";
    }

    // Feed page stuff is here below
    // Displays feed page
    @RequestMapping("/feed")
    public String showFeed(@RequestParam(value = "search", required = false) String search,
                           HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        //List<StudyActivity> allStudyActivities = studyActivityService.findAll();
        //model.addAttribute("studyactivity", allStudyActivities);

        List<StudyActivity> activities;

        if (user != null) {
            model.addAttribute("user", user);
        }
        if (search != null && !search.isEmpty()) {
            activities = studyActivityService.searchByTitleOrDescription(search);
        } else {
            activities = studyActivityService.findAllPublicAndUserActivities(user);
        }

        model.addAttribute("studyActivities", activities);
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

    //End of feed page stuff
}
