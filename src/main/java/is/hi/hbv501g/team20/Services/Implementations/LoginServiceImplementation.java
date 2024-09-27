package is.hi.hbv501g.team20.Services.Implementations;

import is.hi.hbv501g.team20.Persistence.Entities.User;
import is.hi.hbv501g.team20.Persistence.Repository.UserRepository;
import is.hi.hbv501g.team20.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImplementation  implements LoginService {

    @Autowired
    UserRepository userRepo;

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public void delete(User user) {
        userRepo.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public User login(User user) {
        User doesExist = findByEmail(user.getEmail());
        if(doesExist != null){
            if(doesExist.getPassword().equals(user.getPassword())){
                return doesExist;
            }
        }
        return null;
    }
}
