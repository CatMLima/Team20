package is.hi.hbv501g.team20.Services;

import is.hi.hbv501g.team20.Entities.User;

import java.util.List;
import java.util.Optional;

public interface LoginService {
    User save(User user);
    void delete(User user);
    List<User> findAll();
    User findByEmail(String username);
    User login(User user);
}