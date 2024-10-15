package is.hi.hbv501g.team20.Services;

import is.hi.hbv501g.team20.Persistence.Entities.User;

import java.util.List;
import java.util.Optional;

public interface LoginService {
    User save(User user);

    User updatePrivacy(long id, boolean privacy);

    void delete(User user);
    List<User> findAll();
    User findByEmail(String username);
    User login(User user);
    User findById(long id);

}
