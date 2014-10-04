package ooad.comet_tutors;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

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
        this.courseNames = courseNames;
    }

    public List<String> getCourseNames()
    {
        return courseNames;
    }

    public void setView(View view)
    {
        this.view = view;
        ll = (LinearLayout) view.findViewById(R.id.LinearLayout);
        ll.removeAllViews();
        if (cb == null) {
            cb = new ArrayList<CheckBox>();
            for (int i = 0; i < courseNames.size(); i++) {
                cb.add(new CheckBox(view.getContext()));
                cb.get(i).setText(courseNames.get(i));
            }
        }
        else
        {
            for (int i = 0; i < courseNames.size(); i++)
            {
                ViewGroup parent = (ViewGroup) cb.get(i).getParent();
                if (parent != null) parent.removeView(cb.get(i));
            }
        }
        for (int i = 0; i < courseNames.size(); i++)
        {
            ll.addView(cb.get(i));
        }
    }
}
