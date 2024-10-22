package is.hi.hbv501g.team20.Services;

import is.hi.hbv501g.team20.Persistence.Entities.StudyActivity;
import is.hi.hbv501g.team20.Persistence.Entities.User;

import java.util.List;

public interface StudyActivityService {
    StudyActivity save (StudyActivity studyActivity);
    void delete (StudyActivity studyActivity);
    void deleteAll();
    boolean existsById(Long id);
    StudyActivity findById(long id);
    List<StudyActivity> findAll();
    List<StudyActivity> findByUser(User user);
    List<StudyActivity> searchByTitleOrDescription(String query, User user);

    List<StudyActivity> findAllPublicAndUserActivities(User user);

    boolean hasOngoingStudyActivity(User user);
    List<StudyActivity> findOngoingActivities(User user);
}
