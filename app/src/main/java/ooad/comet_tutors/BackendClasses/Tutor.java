package ooad.comet_tutors.BackendClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dillon on 10/8/2014.
 */
public class Tutor {

    String email;
    String password;
    String fName;
    String lName;
    String pNumber;
    List<String> expertise = new ArrayList<String>();
    List<String> schedule = new ArrayList<String>();

    public Tutor() {
    }

    public Tutor(String email, String password, String fName, String lName, String pNumber, List<String> expertise, List<String> schedule) {
        this.email = email;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.pNumber = pNumber;
        for (String exp : expertise)
        {
            this.expertise.add(exp);
        }
        for (String sch : schedule)
        {
            this.schedule.add(sch);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<String> expertise) {
        this.expertise = expertise;
    }

    public List<String> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<String> schedule) {
        this.schedule = schedule;
    }
}