package is.hi.hbv501g.team20.Services.Implementations;

import is.hi.hbv501g.team20.Persistence.Entities.StudyActivity;
import is.hi.hbv501g.team20.Persistence.Entities.User;
import is.hi.hbv501g.team20.Persistence.Repository.StudyActivityRepository;
import is.hi.hbv501g.team20.Persistence.Repository.UserRepository;
import is.hi.hbv501g.team20.Services.StudyActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudyActivityServiceImplementation implements StudyActivityService {

    //UserRepository userRepo;
    @Autowired
    StudyActivityRepository studyActRepo;
    @Autowired
    UserRepository userRepo;

    @Override
    public StudyActivity save(StudyActivity studyActivity) {
        return studyActRepo.save(studyActivity);
    }

    @Override
    public void delete(StudyActivity studyActivity) {
        studyActRepo.delete(studyActivity);
    }

    @Override
    public void deleteAll() {
        studyActRepo.deleteAll();
    }

    @Override
    public boolean existsById(Long id) {
        return studyActRepo.existsById(id);
    }

    @Override
    public StudyActivity findById(long id) {
        return studyActRepo.findById(id);
    }

    @Override
    public List<StudyActivity> findAll() {
        return studyActRepo.findAll();
    }

    @Override
    public List<StudyActivity> findByUser(User user) { return studyActRepo.findByUser(user); }

    @Override
    public List<StudyActivity> searchByTitleOrDescription(String search) {
        List<StudyActivity> foundByTitle = studyActRepo.findByTitle(search);
        List<StudyActivity> foundByDescription = studyActRepo.findByDescription(search);
        foundByTitle.addAll(foundByDescription);
        return foundByTitle;
    }

    @Override
    public List<StudyActivity> findAllPublicAndUserActivities(User user) {
        List<StudyActivity> activitiesPublic = studyActRepo.findAllPublicActivities();
        List<StudyActivity> activitiesUser = studyActRepo.findByUser(user);
        List<StudyActivity> activities = new ArrayList<>();
        activities.addAll(activitiesPublic);
        activities.addAll(activitiesUser);
        return activities;
    }

    @Override
    public List<StudyActivity> findAllPublicActivities() {
        return List.of();
    }


}
