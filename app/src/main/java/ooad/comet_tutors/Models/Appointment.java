package ooad.comet_tutors.Models;

/**
 * Created by Dillon on 11/16/2014.
 */
public class Appointment {
    String id;
    String studentEmail;
    String tutorEmail;
    String studentID;
    String tutorID;
    String date;
    String location;
    String subject;
    String tutorName;
    String studentName;
    boolean accepted;
    boolean rejected;

    public Appointment(String studentEmail, String tutorEmail, String date, boolean accepted) {
        this.studentEmail = studentEmail;
        this.tutorEmail = tutorEmail;
        this.date = date;
        this.accepted = accepted;
    }

    public Appointment(String studentEmail, String tutorEmail, String studentID, String tutorID, String date, String location, String subject, String tutorName, String studentName, boolean accepted, boolean rejected) {
        this.studentEmail = studentEmail;
        this.tutorEmail = tutorEmail;
        this.studentID = studentID;
        this.tutorID = tutorID;
        this.date = date;
        this.location = location;
        this.subject = subject;
        this.tutorName = tutorName;
        this.studentName = studentName;
        this.accepted = accepted;
        this.rejected = rejected;
    }

    public Appointment(String id, String studentEmail, String tutorEmail, String studentID, String tutorID, String date, String location, String subject, String tutorName, String studentName, boolean accepted, boolean rejected) {
        this.id = id;
        this.studentEmail = studentEmail;
        this.tutorEmail = tutorEmail;
        this.studentID = studentID;
        this.tutorID = tutorID;
        this.date = date;
        this.location = location;
        this.subject = subject;
        this.tutorName = tutorName;
        this.studentName = studentName;
        this.accepted = accepted;
        this.rejected = rejected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getTutorEmail() {
        return tutorEmail;
    }

    public void setTutorEmail(String tutorEmail) {
        this.tutorEmail = tutorEmail;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getTutorID() {
        return tutorID;
    }

    public void setTutorID(String tutorID) {
        this.tutorID = tutorID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }
}
