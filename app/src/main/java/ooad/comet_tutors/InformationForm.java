package ooad.comet_tutors;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;


public class InformationForm extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_form);
        createExpandableList();
    }

    public void createExpandableList()
    {
        final List<String> mathCourses = new ArrayList<String>() {{
            add("Calculus");
            add("Geometry");
            add("Trigonometry");
            add("Algebra");
        }};
        final List<String> scienceCourses = new ArrayList<String>() {{
            add("Biology");
            add("Chemistry");
            add("Neurology");
            add("Geology");
        }};
        final List<String> computerCourses = new ArrayList<String>() {{
            add("Java Programming");
            add("C++ Programming");
            add("C# Programming");
            add("Functional Programming");
            add("Logical Programming");
            add("Algorithms");
        }};
        List<ExpandListGroup> courseList = new ArrayList<ExpandListGroup>() {{
            add(new ExpandListGroup("Math", mathCourses));
            add(new ExpandListGroup("Science", scienceCourses));
            add(new ExpandListGroup("Computer Science", computerCourses));
        }};

        final ExpandableListView list = (ExpandableListView) findViewById(R.id.expertise);
        final ExpandListAdapter adapter = new ExpandListAdapter(this, courseList);
        list.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.information_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
