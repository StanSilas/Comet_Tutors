package ooad.comet_tutors.TechnicalServices.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ooad.comet_tutors.Controllers.PrimarySequence.MainFlow;
import ooad.comet_tutors.Models.Appointment;
import ooad.comet_tutors.R;

/**
 * Created by Dillon on 11/17/2014.
 */
public class AppointmentListAdapter extends ArrayAdapter<Appointment> {
    Context context;
    int resource;
    List<Appointment> objects;

    public AppointmentListAdapter(Context context, int resource, List<Appointment> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.appointments_row_item, null);

        }

        Appointment m = getItem(position);

        if (m != null) {

            TextView tt = (TextView) v.findViewById(R.id.appointmentName);
            TextView tt1 = (TextView) v.findViewById(R.id.appointmentSubject);
            TextView tt2 = (TextView) v.findViewById(R.id.appointmentDate);
            TextView tt3 = (TextView) v.findViewById(R.id.appointmentLocation);
            TextView tt4 = (TextView) v.findViewById(R.id.appointmentStatus);
            TextView tt5 = (TextView) v.findViewById(R.id.appointmentEmail);

            if (tt != null) {
                if (MainFlow.type.equals("Student")) tt.setText(m.getTutorName());
                else tt.setText(m.getStudentName());
            }
            if (tt1 != null) {

                tt1.setText(m.getSubject());
            }
            if (tt2 != null) {
                tt2.setText(m.getDate());
            }
            if (tt3 != null) {

                tt3.setText(m.getLocation());
            }
            if (tt4 != null) {
                if (m.isAccepted()) tt4.setText("Accepted");
                else if (m.isRejected()) tt4.setText("Rejected");
                else tt4.setText("Pending");
            }
            if (tt5 != null) {
                if (MainFlow.type.equals("Student")) tt5.setText(m.getTutorEmail());
                else tt5.setText(m.getStudentEmail());
            }
        }

        return v;
    }
}
