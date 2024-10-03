package is.hi.hbv501g.team20.Persistence.Entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Date;


@Entity
//@Table(name = "\"studyactivity\"")
public class StudyActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Date date;
    private LocalTime start; //eða Timer timer?
    private LocalTime end;
    private String description;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //not yet u horny bastard! <- THE WHHAT NOW?!
    //private Subject subject;
    //@OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Coffee> coffees;
    //private String ImageID; //or Image Image
    //private Location location;

    public StudyActivity(Date date, LocalTime start, LocalTime end, String description) {
       this.date = date;
       this.start = start;
       this.end = end; //implement differently
       this.description = description;
       this.user = user;
    }

    public StudyActivity() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
