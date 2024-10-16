package is.hi.hbv501g.team20.Persistence.Repository;

import is.hi.hbv501g.team20.Persistence.Entities.Location;
import is.hi.hbv501g.team20.Persistence.Entities.StudyActivity;
import is.hi.hbv501g.team20.Persistence.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StudyActivityRepository extends JpaRepository<StudyActivity, Long> {

        boolean existsById(long Id);
        //boolean existsbyLocation(Location location);
        StudyActivity save(StudyActivity studyActivity);
        void delete(StudyActivity studyActivity);
        StudyActivity findById(long id);
        List<StudyActivity> findAll();
        //List<StudyActivity> findByLocation(Location location);
        //List<StudyActivity> findByDate(Date date);

        //things to implement later: findBySubject, findByDuration,edit(studyActivity)

        // Query to find all public study activities
        @Query("SELECT sa FROM StudyActivity sa WHERE sa.user.privacy = 0")
        List<StudyActivity> findAllPublicActivities();

        // Query to find activities for a specific user
        @Query("SELECT sa FROM StudyActivity sa WHERE sa.user = :userId")
        List<StudyActivity> findByUser(@Param("userId") Long userId);

        @Query("SELECT '%sa%' FROM StudyActivity sa WHERE sa.description = :descText")
        List<StudyActivity> findByDescription(@Param("descText") String descText);

        @Query("SELECT '%sa%' FROM StudyActivity sa WHERE sa.title = :descText")
        List<StudyActivity> findByTitle(@Param("descText") String descText);

}
