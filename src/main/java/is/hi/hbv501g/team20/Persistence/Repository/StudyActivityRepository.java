package is.hi.hbv501g.team20.Persistence.Repository;

import is.hi.hbv501g.team20.Persistence.Entities.Location;
import is.hi.hbv501g.team20.Persistence.Entities.StudyActivity;
import is.hi.hbv501g.team20.Persistence.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface StudyActivityRepository extends JpaRepository<StudyActivity, Long> {

        boolean existsById(long Id);
        //boolean existsbyLocation(Location location);
        StudyActivity save(StudyActivity studyActivity);
        void delete(StudyActivity studyActivity);
        StudyActivity findById(long id);
        List<StudyActivity> findAll();
        List<StudyActivity> findByUser(User user);
        //List<StudyActivity> findByLocation(Location location);
        //List<StudyActivity> findByDate(Date date);
        List<StudyActivity> findByTitleContainingOrDescriptionContaining(String titleQuery, String descriptionQuery);
        //things to implement later: findBySubject, findByDuration,edit(studyActivity)
}
