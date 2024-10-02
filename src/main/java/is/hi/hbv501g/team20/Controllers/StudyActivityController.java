package is.hi.hbv501g.team20.Controllers;

import is.hi.hbv501g.team20.Persistence.Entities.StudyActivity;
import is.hi.hbv501g.team20.Services.StudyActivityService;
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
}
