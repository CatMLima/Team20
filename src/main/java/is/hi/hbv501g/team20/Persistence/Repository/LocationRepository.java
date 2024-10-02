package is.hi.hbv501g.team20.Persistence.Repository;

import is.hi.hbv501g.team20.Persistence.Entities.Location;
import is.hi.hbv501g.team20.Persistence.Enums.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
    Location save(Location location);
    Location findByBuilding(Building building);
    Location findByUserCount(int usersCount);
    void delete(Location location);
}
