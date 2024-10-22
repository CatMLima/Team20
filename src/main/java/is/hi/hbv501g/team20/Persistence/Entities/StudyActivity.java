package is.hi.hbv501g.team20.Persistence.Entities;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


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
    private LocalDateTime start; //eða Timer timer?

    //@Temporal(TemporalType.TIME)
    private LocalDateTime end;

    private Duration duration;

    private String title;
    private String description;
    private String subjectID;
    private String subjectName;

    //not yet u horny bastard! <- THE WHHAT NOW?!

    //@OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Coffee> coffees;
    @Lob
    private byte[] activityPicture;

    private Integer privacy;

    private Integer isOngoing;

    public StudyActivity(Date date,
                         LocalTime start,
                         LocalTime end,
                         String title,
                         String description,
                         String subjectID,
                         String subjectName) {
        this.date = date;
        this.start = LocalDateTime.from(start);
        this.end = LocalDateTime.from(end);
        this.duration = Duration.between(start, end);
        this.title = title;
        this.description = description;
        this.subjectID = subjectID;
        this.subjectName = subjectName;
    }

    public StudyActivity() {
        this.privacy = 0;
        this.isOngoing = 0; // starts as 0 (True)
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

    public void setId(long Id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public long getDurationInMinutes() {
        if (start != null && end != null) {
            return java.time.Duration.between(start, end).toMinutes();
        }
        return 0;
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Integer getIsOngoing() {
        return isOngoing;
    }

    public void setIsOngoing(Integer isOngoing) {
        this.isOngoing = isOngoing;
    }
}
