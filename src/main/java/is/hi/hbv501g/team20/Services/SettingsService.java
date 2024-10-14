package is.hi.hbv501g.team20.Services;

import is.hi.hbv501g.team20.Persistence.Entities.User;

import java.util.List;

public interface SettingsService {
    void delete(User user);
    List<User> findAll();
    User findByEmail(String email);
    User findById(long id);
    User save(User user);
    User updatePrivacy(long id, boolean privacy);
}
