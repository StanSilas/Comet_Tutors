package ooad.comet_tutors.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ooad.comet_tutors.R;

/**
 * Created by Dillon on 10/25/2014.
 */
public class ListViewAdapter extends ArrayAdapter<Matches> {
    Context context;
    int resource;
    List<Matches> objects;

    public ListViewAdapter(Context context, int resource, List<Matches> objects) {
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
            v = vi.inflate(R.layout.listview_row_item, null);

        }

        Matches m = getItem(position);

        if (m != null) {

            TextView tt = (TextView) v.findViewById(R.id.listView_FirstName);
            TextView tt1 = (TextView) v.findViewById(R.id.listView_LastName);
            TextView tt3 = (TextView) v.findViewById(R.id.listView_Expertise);

            if (tt != null) {
                tt.setText(m.getFirstName());
            }
            if (tt1 != null) {

                tt1.setText(m.getLastName());
            }
            if (tt3 != null) {

                tt3.setText(m.getExpertise());
            }
        }

        return v;    }
}
