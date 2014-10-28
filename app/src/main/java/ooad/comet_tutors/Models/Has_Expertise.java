package ooad.comet_tutors.Models;

/**
 * Created by Dillon on 10/24/2014.
 */
public class Has_Expertise {
    String id;
    String email;
    String expertise;

    public Has_Expertise(String id, String email, String expertise) {
        this.id = id;
        this.email = email;
        this.expertise = expertise;
    }

    public Has_Expertise(String email, String expertise) {
        this.email = email;
        this.expertise = expertise;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
}
