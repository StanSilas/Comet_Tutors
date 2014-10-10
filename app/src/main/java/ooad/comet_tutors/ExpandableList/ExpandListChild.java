package ooad.comet_tutors.ExpandableList;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import ooad.comet_tutors.LoginActivity;
import ooad.comet_tutors.R;

/**
 * Created by Dillon on 10/3/2014.
 */
public class ExpandListChild {

    private List<String> courseNames;
    private View view;
    private LinearLayout ll;
    private List<CheckBox> cb = null;

    public ExpandListChild(List<String> courseNames)
    {
        cb = new ArrayList<CheckBox>();
        this.courseNames = courseNames;
        for (int i = 0; i < this.courseNames.size(); i++) {
            cb.add(new CheckBox(LoginActivity.getContext()));
            cb.get(i).setText(this.courseNames.get(i));
        }
    }

    public List<String> getCourseNames()
    {
        return courseNames;
    }

    public List<CheckBox> getList() { return cb; }

    public void setView(View view)
    {
        this.view = view;
        ll = (LinearLayout) view.findViewById(R.id.LinearLayout);
        ll.removeAllViews();
        for (int i = 0; i < courseNames.size(); i++)
        {
            ViewGroup parent = (ViewGroup) cb.get(i).getParent();
            if (parent != null) parent.removeView(cb.get(i));
        }
        for (int i = 0; i < courseNames.size(); i++)
        {
            ll.addView(cb.get(i));
        }
    }
}
