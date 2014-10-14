package ooad.comet_tutors.CommonForms;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.microsoft.windowsazure.mobileservices.*;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import ooad.comet_tutors.StudentForm.Student;
import ooad.comet_tutors.TutorForm.Tutor;

/**
 * Created by Dillon on 10/11/2014.
 */
public class Database extends AsyncTask {

    private MobileServiceClient mClient;


    public Database(Context context) {
        try {
            mClient = new MobileServiceClient("https://comettutors.azure-mobile.net/", "IwckhnZstWukKYYPLIOWdyjtnadGqP75", context);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (objects[0].getClass() == Student.class) {
            mClient.getTable(Student.class).insert((Student) objects[0], new TableOperationCallback<Student>() {
                @Override
                public void onCompleted(Student entity, Exception exception, ServiceFilterResponse response) {
                }
            });
        }
        else if (objects[0].getClass() == Tutor.class)
        {
            Tutor tutor = (Tutor) objects[0];
            mClient.getTable(Tutor.class).insert((Tutor) objects[0], new TableOperationCallback<Tutor>() {
                @Override
                public void onCompleted(Tutor entity, Exception exception, ServiceFilterResponse response) {
                    if (exception == null)
                    {

                    }
                }
            });
        }
        return null;
    }
}
