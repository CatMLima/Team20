package is.hi.hbv501g.team20;

import is.hi.hbv501g.team20.Entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    User save(User user);

    void delete(User user);

    List<User> findAll();

    Optional<User> findById(long id);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
