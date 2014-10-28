package ooad.comet_tutors.CommonForms;

/**
 * Created by Dillon on 10/24/2014.
 */
public class Has_Schedule {
    String id;
    String email;
    String schedule;

    public Has_Schedule(String id, String email, String schedule) {
        this.id = id;
        this.email = email;
        this.schedule = schedule;
    }

    public Has_Schedule(String email, String schedule) {
        this.email = email;
        this.schedule = schedule;
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

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
