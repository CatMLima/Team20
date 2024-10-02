package is.hi.hbv501g.team20.Persistence.Repository;

import is.hi.hbv501g.team20.Persistence.Entities.StudyActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyActivityRepository extends JpaRepository<StudyActivity, Long> {

        boolean existsById(long Id);
        StudyActivity save(StudyActivity studyActivity);
        void delete(StudyActivity studyActivity);
        StudyActivity findById(long id);
        List<StudyActivity> findAll();

        //how to implement StudyActivity edit(...) => update descr, location, subject...
}
