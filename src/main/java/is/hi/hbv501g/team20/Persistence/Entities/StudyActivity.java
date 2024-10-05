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

    private Date date;
    private LocalTime start; //eða Timer timer?
    private LocalTime end;
    private String description;

    //not yet u horny bastard! <- THE WHHAT NOW?!

    private String subjectID;
    private String subjectName;

    //@OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Coffee> coffees;
    //private String ImageID; //or Image Image
    //private Location location;

    public StudyActivity(Date date, String subjectID, String subjectName, LocalTime start, LocalTime end, String description) {
       this.date = date;
       this.subjectID = subjectID;
       this.subjectName = subjectName;
       this.start = start;
       this.end = end; //implement differently
       this.description = description;
    }

    public StudyActivity() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
