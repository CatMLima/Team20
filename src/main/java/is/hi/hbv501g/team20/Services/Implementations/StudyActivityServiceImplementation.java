package is.hi.hbv501g.team20.Services.Implementations;

import is.hi.hbv501g.team20.Persistence.Entities.StudyActivity;
import is.hi.hbv501g.team20.Persistence.Entities.User;
import is.hi.hbv501g.team20.Persistence.Repository.StudyActivityRepository;
import is.hi.hbv501g.team20.Persistence.Repository.UserRepository;
import is.hi.hbv501g.team20.Services.StudyActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyActivityServiceImplementation implements StudyActivityService {

    //UserRepository userRepo;
    @Autowired
    StudyActivityRepository studyActRepo;

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
    public List<StudyActivity> searchByTitleOrDescription(String searchText) {
        List<StudyActivity> FoundByTitle = studyActRepo.findByTitle(searchText);
        List<StudyActivity> FoundByDesc = studyActRepo.findByDescription(searchText);
        if(FoundByTitle == null){ return FoundByDesc; }
        else{ return FoundByTitle; }
    }

    @Override
    public List<StudyActivity> findAllPublicAndUserActivities(User user) {
        // Fetch public activities and user's own activities
        List<StudyActivity> publicActivities = studyActRepo.findAllPublicActivities();
        List<StudyActivity> userActivities = studyActRepo.findByUser(user.getId());

        // Combine the two lists
        publicActivities.addAll(userActivities);

        return publicActivities;
    }
}
