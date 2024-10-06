package is.hi.hbv501g.team20.Persistence.Entities;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name= "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }


    private String name;
    private String email;
    private String password;

    @Lob
    private byte[] profilePicture;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyActivity> studyActivities;

    private Boolean privacySetting;

    public User() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Boolean getPrivacySetting() { return privacySetting; }

    public void setPrivacySetting(Boolean privacySetting) { this.privacySetting = privacySetting; }


}
