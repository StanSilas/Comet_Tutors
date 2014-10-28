package ooad.comet_tutors.StudentForm;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dillon on 10/10/2014.
 */
public class Student implements Parcelable{
    String id;
    String email;
    String password;
    String FirstName;
    String LastName;
    String PhoneNumber;

    public Student(){}
    public Student (Parcel parcel)
    {
        String[] data = new String[5];
        parcel.readStringArray(data);
        this.email = data[0];
        this.password = data[1];
        this.FirstName = data[2];
        this.LastName = data[3];
        this.PhoneNumber = data[4];
    }
    public Student(Student student)
    {
        this.id = student.id;
        this.email = student.email;
        this.password = student.password;
        this.FirstName = student.FirstName;
        this.LastName = student.LastName;
        this.PhoneNumber = student.PhoneNumber;
    }

    public Student(String email, String password, String fName, String lName, String pNumber) {
        this.email = email;
        this.password = password;
        this.FirstName = fName;
        this.LastName = lName;
        this.PhoneNumber = pNumber;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {this.email, this.password, this.FirstName, this.LastName, this.PhoneNumber});
    }
}
