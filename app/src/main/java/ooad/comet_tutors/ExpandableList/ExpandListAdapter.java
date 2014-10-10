package ooad.comet_tutors.ExpandableList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import ooad.comet_tutors.R;

/**
 * Created by Dillon on 10/3/2014.
 */
public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<ExpandListGroup> courses;

    public ExpandListAdapter(Context context, List<ExpandListGroup> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public int getGroupCount() {
        return courses.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return courses.get(i);
    }

    @Override
    public Object getChild(int i, int i2) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_item, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.textView);
        ExpandListGroup entry = (ExpandListGroup) getGroup(i);
        tv.setText(entry.getRootCourse());

        return view;
    }

    @Override
    public View getChildView(int i, int i2, boolean b, View view, ViewGroup viewGroup) {
        //Get saved states
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.child_item, null);
        }
        ExpandListGroup g = (ExpandListGroup) getGroup(i);
        ExpandListChild c = g.getChild();
        c.setView(view);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}
