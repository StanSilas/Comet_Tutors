package ooad.comet_tutors.List;

import ooad.comet_tutors.TutorForm.Tutor;

/**
 * Created by Dillon on 10/25/2014.
 */
public class Matches {
    String firstName;
    String lastName;
    String expertise;
    Tutor tutor;

    public Matches(String firstName, String lastName, String expertise) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.expertise = expertise;
    }

    public Matches(String firstName, String lastName, String expertise, Tutor tutor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.expertise = expertise;
        this.tutor = tutor;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}
