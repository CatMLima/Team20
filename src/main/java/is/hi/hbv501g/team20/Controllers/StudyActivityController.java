package is.hi.hbv501g.team20.Controllers;

import is.hi.hbv501g.team20.Persistence.Entities.Coffee;
import is.hi.hbv501g.team20.Persistence.Entities.StudyActivity;
import is.hi.hbv501g.team20.Persistence.Entities.User;
import is.hi.hbv501g.team20.Services.CoffeeService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        studyActivity.setPrivacy(user);
        studyActivity.setDate(new Date());
        studyActivity.setStart(LocalTime.now());
        studyActivity.setIsActive(0);

        if(result.hasErrors()){
            return "studyactivity-create";
        }
        studyActivityService.save(studyActivity);
        return "redirect:/feed";
    }

    @RequestMapping(value = "/api/studyactivity-finish/{id}", method = RequestMethod.POST)
    public String finishStudyActivity(@PathVariable("id") long id, Model model) {

        StudyActivity active = studyActivityService.findById(id);
        active.setEnd(LocalTime.now());
        active.setIsActive(1);
        studyActivityService.save(active);
        return "redirect:/feed";

    }


    // deletes a selected studyactivity and removes it from the database
    @GetMapping("/studyactivity-delete/{id}")
    public String deleteStudyActicity (@PathVariable("id") long id, Model model){
        StudyActivity studyActivityToDelete = studyActivityService.findById(id);
        studyActivityService.delete(studyActivityToDelete);
        return "redirect:/studyactivity-list";
    }

    // displays study activity details
    @RequestMapping(value="/studyactivity-details/{id}", method= RequestMethod.GET)
    public String getStudyActivityDetailsPage(@PathVariable("id") long id, Model model) {
        StudyActivity studyActivity = studyActivityService.findById(id);
        model.addAttribute("studyactivity", studyActivity);
        return "studyactivity-details";
    }

    // displays page for editing study activity
    @RequestMapping(value="/studyactivity-edit/{id}", method= RequestMethod.GET)
    public String getStudyActivityEditPage(@PathVariable("id") long id, Model model) {
        StudyActivity studyActivity = studyActivityService.findById(id);
        model.addAttribute("studyactivity", studyActivity);
        return "studyactivity-edit";
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

        Map<Long, Long> coffeeStatus = new HashMap<>(); // Map to track amount of coffees
        Map<Long, Boolean> userHasGivenCoffee = new HashMap<>(); // Map to track user's coffee status
        model.addAttribute("userHasGivenCoffee", userHasGivenCoffee);
        for (StudyActivity activity : allStudyActivities) {
            coffeeStatus.put(activity.getId(), coffeeService.countCoffeesForActivity(activity));
            // Check if the user has given coffee for this activity
            Coffee userCoffee = coffeeService.findCoffeeByUserAndActivity(user, activity);
            userHasGivenCoffee.put(activity.getId(), userCoffee != null);
        }
        model.addAttribute("coffeeStatus", coffeeStatus);

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

    @RequestMapping(value = "/feed-search", method = RequestMethod.GET)
    public String searchStudyActivities(@RequestParam("query") String query, Model model, HttpSession session) {
        // Get the user from the session
        User sessionUser = (User) session.getAttribute("user");

        // Ensure the user is not null before querying
        if (sessionUser != null) {
            // Pass the managed User entity to the service
            List<StudyActivity> searchResults = studyActivityService.searchByTitleOrDescription(query, sessionUser);
            model.addAttribute("studyactivity", searchResults);
        } else {
            // Handle the case where the user is not logged in or session has expired
            return "redirect:/login";  // Redirect to login page if needed
        }

        return "feed";
    }
    // Controller Method to toggle coffee for a study activity
    @Autowired CoffeeService coffeeService;
    @RequestMapping(value = "/studyactivity/{id}/toggle-coffee", method = RequestMethod.POST)
    public String toggleCoffee(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        StudyActivity activity = studyActivityService.findById(id); // Fetch the StudyActivity by ID

        if (user != null && activity != null) {
            Coffee existingCoffee = coffeeService.findCoffeeByUserAndActivity(user, activity);
            if (existingCoffee != null) {
                // If coffee exists, remove it
                coffeeService.removeCoffee(user, activity);
            } else {
                // If coffee does not exist, add it
                coffeeService.giveCoffee(user, activity);
            }
        }
        return "redirect:/feed"; // Redirect to the feed page after toggling coffee
    }

    //End of feed page stuff
}
