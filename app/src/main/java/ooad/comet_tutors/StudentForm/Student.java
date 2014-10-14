package ooad.comet_tutors.StudentForm;

/**
 * Created by Dillon on 10/10/2014.
 */
public class Student {
    String id;
    String email;
    String password;
    String FirstName;
    String LastName;
    String PhoneNumber;

    public Student(){}

    public Student(String email, String password, String fName, String lName, String pNumber) {
        this.email = email;
        this.password = password;
        this.FirstName = fName;
        this.LastName = lName;
        this.PhoneNumber = pNumber;
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
}
