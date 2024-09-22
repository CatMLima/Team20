package is.hi.hbv501g.team20;

import is.hi.hbv501g.team20.Entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByName(String name);

}
