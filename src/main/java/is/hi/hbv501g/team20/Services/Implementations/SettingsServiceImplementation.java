package is.hi.hbv501g.team20.Services.Implementations;

import is.hi.hbv501g.team20.Persistence.Entities.User;
import is.hi.hbv501g.team20.Persistence.Repository.UserRepository;
import is.hi.hbv501g.team20.Services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsServiceImplementation implements SettingsService {

    @Autowired
    UserRepository userRepo;

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public void delete(User user){ userRepo.delete(user); }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public User findById(long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public User updatePrivacy(long id, Boolean privateAccount) {
        User user = findById(id);
        if (user != null) {
            user.setPrivacySetting(privateAccount);
            return save(user);
        }
        return null;
    }


}
