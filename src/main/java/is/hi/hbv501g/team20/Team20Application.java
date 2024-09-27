package is.hi.hbv501g.team20;

import is.hi.hbv501g.team20.Persistence.Entities.User;
import is.hi.hbv501g.team20.Persistence.Repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Team20Application {

    public static void main(String[] args) {
        SpringApplication.run(Team20Application.class, args);
    }

    /*
    @Bean
    public CommandLineRunner run(UserRepository repository){
        return args -> {
            insertJavaAdvocates(repository);
            System.out.println(repository.findAll());
        };
    }
    *
     */

    // this method was used to create the first entry into the database, we don't need to keep it.
    private void insertJavaAdvocates(UserRepository userRepository) {
        userRepository.save(new User("Catarina","cms5@hi.is","hello"));
    }
}
