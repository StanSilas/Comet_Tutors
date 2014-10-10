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
}
