package ooad.comet_tutors.TutorForm;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dillon on 10/8/2014.
 */
public class Tutor implements Parcelable{

    String id;
    String email;
    String password;
    String FirstName;
    String LastName;
    String PhoneNumber;
    transient List<String> expertise = new ArrayList<String>();
    transient List<String> schedule = new ArrayList<String>();

    public Tutor(){}
    public Tutor(Tutor tutor)
    {
        this.id = tutor.id;
        this.email = tutor.email;
        this.password = tutor.password;
        this.FirstName = tutor.FirstName;
        this.LastName = tutor.LastName;
        this.PhoneNumber = tutor.PhoneNumber;
        for (String str : tutor.expertise)
        {
            this.expertise.add(str);
        }
        for (String str : tutor.schedule)
        {
            this.schedule.add(str);
        }
    }

    public Tutor(String email, String password, String fName, String lName, String pNumber, List<String> expertise, List<String> schedule) {
        this.email = email;
        this.password = password;
        this.FirstName = fName;
        this.LastName = lName;
        this.PhoneNumber = pNumber;
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

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {this.email, this.password, this.FirstName, this.LastName, this.PhoneNumber});
    }
}
