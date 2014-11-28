package ooad.comet_tutors.TechnicalServices;

import ooad.comet_tutors.Models.Student;
import ooad.comet_tutors.Models.Tutor;

/**
 * Created by Dillon on 11/28/2014.
 */
public class ProfileFactory {

    public static ProfileFactory factory = null;
    private Student student;
    private Tutor tutor;
    public String type;

    public static synchronized ProfileFactory getInstance() {
        if (factory == null) factory = new ProfileFactory();
        return factory;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
        this.type = "Student";
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
        this.type = "Tutor";
    }

    public String getType() {
        return type;
    }
}
