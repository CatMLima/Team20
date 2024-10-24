package is.hi.hbv501g.team20.Persistence.Entities;

import is.hi.hbv501g.team20.Persistence.Enums.Building;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "\"studyactivity\"")
public class StudyActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.DATE)
    private Date date;

    //@Temporal(TemporalType.TIME)
    private LocalTime start; //eða Timer timer?

    //@Temporal(TemporalType.TIME)
    private LocalTime end;

    private String title;
    private String description;
    private String subjectID;
    private String subjectName;


    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coffee> coffees = new ArrayList<>();
    @Lob
    private byte[] activityPicture;

    private Integer privacy;


    @Enumerated(EnumType.STRING)
    private Building building;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private Integer isActive;


    public StudyActivity(Date date,
                         LocalTime start,
                         LocalTime end,
                         String title,
                         String description,
                         String subjectID,
                         String subjectName) {
        this.date = date;
        this.start = start;
        this.title = title;
        this.description = description;
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.privacy = 0;
        this.location = null;
    }

    public StudyActivity() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer userPrivacy() {
        return user.getPrivacy();
    }

    public long getId() {
        return id;
    }

    public void setId(long ID) {
        this.id = id;
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

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

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

    public void setActivityPicture(byte[] activityPicture) {
        this.activityPicture = activityPicture;
    }

    public byte[] getActivityPicture() {
        return activityPicture;
    }

    private Integer getPrivacy() {
        return privacy;
    }

    public void setPrivacy(User user) {
        this.privacy = userPrivacy();
    }
//used if we want to get the list somehow
    public List<Coffee> getCoffees() {
        return coffees;
    }

    public void setCoffees(List<Coffee> coffees) {
        this.coffees = coffees;
    }


    public Building getBuilding() {
        return building;
    }
    public void setBuilding(Building building) {
        this.building = building;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;

    }
}
