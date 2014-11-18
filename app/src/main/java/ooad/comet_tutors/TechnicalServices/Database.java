package ooad.comet_tutors.TechnicalServices;

import android.content.Context;
import android.os.AsyncTask;

import com.microsoft.windowsazure.mobileservices.*;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import ooad.comet_tutors.Controllers.Login.LoginActivity;
import ooad.comet_tutors.Controllers.PrimarySequence.AppointmentsFragment;
import ooad.comet_tutors.Controllers.PrimarySequence.RequestsFragment;
import ooad.comet_tutors.Models.Appointment;
import ooad.comet_tutors.Models.Has_Expertise;
import ooad.comet_tutors.Models.Has_Query;
import ooad.comet_tutors.Models.Has_Schedule;
import ooad.comet_tutors.TechnicalServices.List.Matches;
import ooad.comet_tutors.Models.Student;
import ooad.comet_tutors.Models.Tutor;
import ooad.comet_tutors.Controllers.PrimarySequence.MainFlow;
import ooad.comet_tutors.Controllers.PrimarySequence.MatchesFragment;
import ooad.comet_tutors.Controllers.Login.ScheduleForm;

/**
 * Created by Dillon on 10/11/2014.
 */
public class Database {

    private MobileServiceClient mClient;
    public Context context;
    public int statCounter = 0;

    public Database(Context context) {
        this.context = context;
        try {
            mClient = new MobileServiceClient("https://comettutors.azure-mobile.net/", "IwckhnZstWukKYYPLIOWdyjtnadGqP75", context);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Object objects)
    {
        Insert tempInsert = new Insert();
        tempInsert.execute(objects);
    }

    public void removeAll(Object objects) {
        Delete tempDelete = new Delete();
        tempDelete.execute(objects);
    }

    public void match(Object objects) {
        Match tempMatch = new Match();
        tempMatch.execute(objects);
    }

    public void setAppointments(Object objects) {
        SetAppointment tempAppointment = new SetAppointment();
        tempAppointment.execute(objects);
    }

    public void delete(Appointment appointment)
    {
        MobileServiceTable<Appointment> appointmentTable = mClient.getTable(Appointment.class);
        appointmentTable.delete(appointment, new TableDeleteCallback() {
            @Override
            public void onCompleted(Exception exception, ServiceFilterResponse response) {
                //Entry deleted
            }
        });
    }

    public void refresh()
    {
        if (LoginActivity.student != null)
        {
            Match match = new Match();
            match.execute(LoginActivity.student);

            SetAppointment appointment = new SetAppointment();
            appointment.execute(LoginActivity.student);
        }
        else {
            SetAppointment appointment = new SetAppointment();
            appointment.execute(LoginActivity.tutor);
        }
    }

    public void update()
    {
        statCounter--;
        if (statCounter == 0) MainFlow.savePartTwo();
    }

    public void createAccountPart2()
    {
        ScheduleForm.createAccountPart2();
    }

    public void schedule(Student student, Tutor matchedTutor, String room, String date, String expertise) {
        SetAppointment tempAppointment = new SetAppointment();
        Object[] objArray = {student, matchedTutor, room, date, expertise};
        tempAppointment.execute(objArray);
    }

    public void schedule(Appointment app) {
        SetAppointment tempAppointment = new SetAppointment();
        tempAppointment.execute(app);
    }

    public class SetAppointment extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            if (objects.length > 1) {
                AppointmentsFragment.appointmentsList.clear();
                final Student student = (Student) objects[0];
                final Tutor tutor = (Tutor) objects[1];
                final String room = (String) objects[2];
                final String date = (String) objects[3];
                final String expertise = (String) objects[4];
                final MobileServiceTable<Appointment> appointmentTable = mClient.getTable(Appointment.class);
                appointmentTable.where()
                        .field("studentEmail").eq(student.getEmail())
                        .and()
                        .field("tutorEmail").eq(tutor.getEmail())
                        .and()
                        .field("date").eq(date)
                        .execute(new TableQueryCallback<Appointment>() {
                            @Override
                            public void onCompleted(List<Appointment> result, int count, Exception exception, ServiceFilterResponse response) {
                                if (result.size() == 0) {
                                    Appointment app = new Appointment(student.getEmail(), tutor.getEmail(), student.getId(), tutor.getId(), date, room, expertise, tutor.getFirstName() + " " + tutor.getLastName(), student.getFirstName() + " " + student.getLastName(), false, false);
                                    appointmentTable.insert(app, new TableOperationCallback<Appointment>() {
                                        @Override
                                        public void onCompleted(Appointment entity, Exception exception, ServiceFilterResponse response) {
                                            //Inserted
                                            AppointmentsFragment.appointmentsList.add(entity);
                                        }
                                    });
                                }
                            }
                        });
            }
            else if (objects[0].getClass() == Appointment.class)
            {
                AppointmentsFragment.appointmentsList.clear();
                Appointment app = (Appointment) objects[0];
                MobileServiceTable<Appointment> appointmentTable = mClient.getTable(Appointment.class);
                appointmentTable.update(app, new TableOperationCallback<Appointment>() {
                    @Override
                    public void onCompleted(Appointment entity, Exception exception, ServiceFilterResponse response) {
                        //Complete
                    }
                });

            }
            else
            {
                if (LoginActivity.student != null) {
                    AppointmentsFragment.appointmentsList.clear();
                    final Student student = (Student) objects[0];
                    MobileServiceTable<Appointment> appointmentTable = mClient.getTable(Appointment.class);
                    appointmentTable.where()
                            .field("studentEmail")
                            .eq(student.getEmail())
                            .execute(new TableQueryCallback<Appointment>() {
                                @Override
                                public void onCompleted(List<Appointment> result, int count, Exception exception, ServiceFilterResponse response) {
                                    AppointmentsFragment.appointmentsList = result;
                                }
                            });
                }
                else
                {
                    final Tutor tutor = (Tutor) objects[0];
                    MobileServiceTable<Appointment> appointmentTable = mClient.getTable(Appointment.class);
                    appointmentTable.where()
                            .field("tutorEmail")
                            .eq(tutor.getEmail())
                            .execute(new TableQueryCallback<Appointment>() {
                                @Override
                                public void onCompleted(List<Appointment> result, int count, Exception exception, ServiceFilterResponse response) {
                                    for (Appointment app : result)
                                    {
                                        if (app.isAccepted() == false && app.isRejected() == false) RequestsFragment.requestsList.add(app);
                                        else if (app.isAccepted()) AppointmentsFragment.appointmentsList.add(app);
                                    }
                                }
                            });
                }
            }
            return null;
        }
    }


    public class Match extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            MatchesFragment.matchesList.clear();
            final Student tempStudent = (Student) objects[0];
            MobileServiceTable<Has_Query> queryTable = mClient.getTable(Has_Query.class);
            queryTable.where()
                    .field("email").eq(tempStudent.getEmail())
                    .execute(new TableQueryCallback<Has_Query>() {
                        @Override
                        public void onCompleted(List<Has_Query> result, int count, Exception exception, ServiceFilterResponse response) {
                            MobileServiceTable<Has_Expertise> expertiseTable = mClient.getTable(Has_Expertise.class);
                            for (Has_Query query : result)
                            {
                                expertiseTable.where()
                                        .field("expertise").eq(query.getQuery())
                                        .execute(new TableQueryCallback<Has_Expertise>() {
                                            @Override
                                            public void onCompleted(List<Has_Expertise> result, int count, Exception exception, ServiceFilterResponse response) {
                                                MobileServiceTable<Tutor> tutorTable = mClient.getTable(Tutor.class);
                                                for (final Has_Expertise expertise : result)
                                                {
                                                    tutorTable.where()
                                                            .field("email").eq(expertise.getEmail())
                                                            .execute(new TableQueryCallback<Tutor>() {
                                                                @Override
                                                                public void onCompleted(List<Tutor> result, int count, Exception exception, ServiceFilterResponse response) {
                                                                    for (final Tutor tutor : result)
                                                                    {
                                                                        MobileServiceTable<Has_Schedule> scheduleTable = mClient.getTable(Has_Schedule.class);
                                                                        scheduleTable.where()
                                                                                .field("email")
                                                                                .eq(tutor.getEmail())
                                                                                .execute(new TableQueryCallback<Has_Schedule>() {
                                                                                    @Override
                                                                                    public void onCompleted(List<Has_Schedule> result, int count, Exception exception, ServiceFilterResponse response) {
                                                                                       final List<String> schedule = new ArrayList<String>();
                                                                                       for (final Has_Schedule sched : result)
                                                                                       {
                                                                                           MobileServiceTable<Appointment> appointmentTable = mClient.getTable(Appointment.class);
                                                                                           appointmentTable.where()
                                                                                                   .field("date").eq(sched.getSchedule())
                                                                                                   .and()
                                                                                                   .field("tutorEmail").eq(tutor.getEmail())
                                                                                                   .and()
                                                                                                   .field("accepted").eq(true)
                                                                                                   .or()
                                                                                                   .field("date").eq(sched.getSchedule())
                                                                                                   .and()
                                                                                                   .field("tutorEmail").eq(tutor.getEmail())
                                                                                                   .and()
                                                                                                   .field("studentEmail").eq(tempStudent.getEmail())
                                                                                                   .execute(new TableQueryCallback<Appointment>() {
                                                                                                       @Override
                                                                                                       public void onCompleted(List<Appointment> result, int count, Exception exception, ServiceFilterResponse response) {
                                                                                                           if (result.size() == 0) {
                                                                                                               schedule.add(sched.getSchedule());
                                                                                                           }
                                                                                                       }
                                                                                                   });
                                                                                       }
                                                                                       tutor.setSchedule(schedule);
                                                                                       MatchesFragment.matchesList.add(new Matches(tutor.getFirstName(), tutor.getLastName(), expertise.getExpertise(), tutor));
                                                                                    }
                                                                                });
                                                                    }
                                                                }
                                                            });
                                                }
                                            }
                                        });
                            }
                        }
                    });
            return null;
        }
    }

    public class Delete extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            if (objects.length > 0)
            {
                if (objects[0].getClass() == Student.class)
                {
                    Student tempStudent = (Student) objects[0];
                    final MobileServiceTable<Has_Query> queryTable = mClient.getTable(Has_Query.class);
                    queryTable.where()
                            .field("email").eq(tempStudent.getEmail())
                            .execute(new TableQueryCallback<Has_Query>() {
                                @Override
                                public void onCompleted(List<Has_Query> result, int count, Exception exception, ServiceFilterResponse response) {
                                    int internalCounter = result.size();
                                    statCounter = result.size();
                                    if (internalCounter == 0) MainFlow.savePartTwo();
                                    for (Has_Query query : result) {
                                        queryTable.delete(query, new TableDeleteCallback() {
                                            @Override
                                            public void onCompleted(Exception exception, ServiceFilterResponse response) {
                                                update();
                                            }
                                        });
                                    }
                                }
                            });
                }
                else if (objects[0].getClass() == Tutor.class)
                {
                    Tutor tempTutor = (Tutor) objects[0];
                    final MobileServiceTable<Has_Expertise> expertiseTable = mClient.getTable(Has_Expertise.class);
                    final MobileServiceTable<Has_Schedule> scheduleTable = mClient.getTable(Has_Schedule.class);
                    scheduleTable.where()
                            .field("email").eq(tempTutor.getEmail())
                            .execute(new TableQueryCallback<Has_Schedule>() {
                                @Override
                                public void onCompleted(List<Has_Schedule> result, int count, Exception exception, ServiceFilterResponse response) {
                                    for (Has_Schedule sched : result)
                                    {
                                        scheduleTable.delete(sched, new TableDeleteCallback() {
                                            @Override
                                            public void onCompleted(Exception exception, ServiceFilterResponse response) {

                                            }
                                        });
                                    }
                                }
                            });
                    expertiseTable.where()
                            .field("email").eq(tempTutor.getEmail())
                            .execute(new TableQueryCallback<Has_Expertise>() {
                                @Override
                                public void onCompleted(List<Has_Expertise> result, int count, Exception exception, ServiceFilterResponse response) {
                                    int internalCounter = result.size();
                                    statCounter = result.size();
                                    if (internalCounter == 0) MainFlow.savePartTwo();
                                    for (Has_Expertise expert : result) {
                                        expertiseTable.delete(expert, new TableDeleteCallback() {
                                            @Override
                                            public void onCompleted(Exception exception, ServiceFilterResponse response) {
                                                update();
                                            }
                                        });
                                    }
                                }
                            });
                }
            }
            return null;
        }
    }
    public class Insert extends AsyncTask {

        @Override
        protected Object doInBackground(final Object[] objects) {
            if (objects.length > 0) {
                if (objects[0].getClass() == Student.class) {
                    mClient.getTable(Student.class).insert((Student) objects[0], new TableOperationCallback<Student>() {
                        @Override
                        public void onCompleted(Student entity, Exception exception, ServiceFilterResponse response) {
                        }
                    });
                } else if (objects[0].getClass() == Tutor.class) {
                    Tutor tutor = (Tutor) objects[0];
                    mClient.getTable(Tutor.class).insert((Tutor) objects[0], new TableOperationCallback<Tutor>() {
                        @Override
                        public void onCompleted(Tutor entity, Exception exception, ServiceFilterResponse response) {
                            createAccountPart2();
                        }
                    });
                } else if (objects[0].getClass() == Has_Query.class) {
                    Has_Query query = (Has_Query) objects[0];
                    mClient.getTable(Has_Query.class).insert((Has_Query) objects[0], new TableOperationCallback<Has_Query>() {
                        @Override
                        public void onCompleted(Has_Query entity, Exception exception, ServiceFilterResponse response) {
                            if (context == MainFlow.context) MainFlow.dismissPD();
                        }
                    });
                } else if (objects[0].getClass() == Has_Schedule.class) {
                    final Has_Schedule schedule = (Has_Schedule) objects[0];
                    MobileServiceTable<Tutor> tutorTable = mClient.getTable(Tutor.class);
                    tutorTable.where()
                            .field("email").eq(schedule.getEmail())
                            .execute(new TableQueryCallback<Tutor>() {
                                @Override
                                public void onCompleted(List<Tutor> result, int count, Exception exception, ServiceFilterResponse response) {
                                    Tutor tutor = result.get(0);
                                    schedule.setId(tutor.getId());
                                    mClient.getTable(Has_Schedule.class).insert(schedule, new TableOperationCallback<Has_Schedule>() {
                                        @Override
                                        public void onCompleted(Has_Schedule entity, Exception exception, ServiceFilterResponse response) {
                                            if (context == MainFlow.context) MainFlow.dismissPD();
                                        }
                                    });
                                }
                            });
                } else if (objects[0].getClass() == Has_Expertise.class) {
                    final Has_Expertise expertise = (Has_Expertise) objects[0];
                    MobileServiceTable<Tutor> tutorTable = mClient.getTable(Tutor.class);
                    tutorTable.where()
                            .field("email").eq(expertise.getEmail())
                            .execute(new TableQueryCallback<Tutor>() {
                                @Override
                                public void onCompleted(List<Tutor> result, int count, Exception exception, ServiceFilterResponse response) {
                                    Tutor tutor = result.get(0);
                                    expertise.setId(tutor.getId());
                                    mClient.getTable(Has_Expertise.class).insert(expertise, new TableOperationCallback<Has_Expertise>() {
                                        @Override
                                        public void onCompleted(Has_Expertise entity, Exception exception, ServiceFilterResponse response) {
                                            if (context == MainFlow.context) MainFlow.dismissPD();
                                        }
                                    });
                                }
                            });
                }
            }
            return null;
        }
    }
}
