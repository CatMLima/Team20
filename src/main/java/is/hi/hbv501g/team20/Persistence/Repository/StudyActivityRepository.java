package is.hi.hbv501g.team20.Persistence.Repository;

import is.hi.hbv501g.team20.Persistence.Entities.StudyActivity;
import is.hi.hbv501g.team20.Persistence.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyActivityRepository extends JpaRepository<StudyActivity, Long> {

        boolean existsById(long Id);
        //boolean existsbyLocation(Location location);
        StudyActivity save(StudyActivity studyActivity);
        void delete(StudyActivity studyActivity);
        StudyActivity findById(long id);
        List<StudyActivity> findAll();
        //List<StudyActivity> findByUser(User user);
        //List<StudyActivity> findByLocation(Location location);
        //List<StudyActivity> findByDate(Date date);
        //List<StudyActivity> findByTitleContainingOrDescriptionContaining(String titleQuery, String descriptionQuery);
        //things to implement later: findBySubject, findByDuration,edit(studyActivity)

        // Query to search on users own activities and public ones
        @Query("SELECT sa FROM StudyActivity sa WHERE ( sa.title LIKE %:query% OR sa.description LIKE %:query% OR sa.subjectID LIKE %:query% OR sa.subjectName LIKE %:query% ) AND sa.user.id = :userId")
        List<StudyActivity> searchStudyActivityPrivateUser(@Param("query") String query, @Param("userId") Long userId);

        // Query to search public activities
        @Query("SELECT sa FROM StudyActivity sa WHERE ( sa.title LIKE %:query% OR sa.description LIKE %:query% OR sa.subjectID LIKE %:query% OR sa.subjectName LIKE %:query% ) AND sa.privacy = 0")
        List<StudyActivity> searchStudyActivityPublicUser(@Param("query") String query);

        // Query to find public study activities (privacy = 0)
        @Query("SELECT sa FROM StudyActivity sa WHERE sa.privacy = 0")
        List<StudyActivity> findAllPublicActivities();

        // Query to find activities for a specific user by user entity
        @Query("SELECT sa FROM StudyActivity sa WHERE sa.user = :user")
        List<StudyActivity> findByUser(@Param("user") User user);

        // Query to get ongoing Activities
        @Query("SELECT sa FROM StudyActivity sa WHERE sa.isOngoing = 0 AND sa.user = :user")
        List<StudyActivity> getOngoingActivities(@Param("user") User user);
}
