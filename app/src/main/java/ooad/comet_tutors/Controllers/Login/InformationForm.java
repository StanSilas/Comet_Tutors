package ooad.comet_tutors.Controllers.Login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ooad.comet_tutors.TechnicalServices.ExpandableList.ExpandListAdapter;
import ooad.comet_tutors.TechnicalServices.ExpandableList.ExpandListGroup;
import ooad.comet_tutors.R;
import ooad.comet_tutors.Models.Student;
import ooad.comet_tutors.TechnicalServices.Database;


public class InformationForm extends Activity {

    static List<CheckBox> lst = new ArrayList<CheckBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_form);
        createExpandableList();
        EditText fName = (EditText) findViewById(R.id.firstNameTextBox);
        fName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (getIntent().getStringExtra("Type").equals("Tutor")) ProfileForm.tutor.setFirstName(charSequence.toString());
                else ProfileForm.student.setFirstName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        EditText lName = (EditText) findViewById(R.id.lastNameTextBox);
        lName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (getIntent().getStringExtra("Type").equals("Tutor")) ProfileForm.tutor.setLastName(charSequence.toString());
                else ProfileForm.student.setLastName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        EditText pNumber = (EditText) findViewById(R.id.phoneNumberTextBox);
        pNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (getIntent().getStringExtra("Type").equals("Tutor")) ProfileForm.tutor.setPhoneNumber(charSequence.toString());
                else ProfileForm.student.setPhoneNumber(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static List<CheckBox> getExpandableListChildren()
    {
        return lst;
    }

    public void createExpandableList()
    {
        LinearLayout ll = (LinearLayout) findViewById(R.id.LinearLayout);

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
        if (getIntent().getStringExtra("Type").equals("Student")) {
            ll.removeView(findViewById(R.id.expertise));
            ll.removeView(findViewById(R.id.tutorExpertiseTextBox));
        }
        else
        {
            ll.removeView(findViewById(R.id.button));
            final ExpandableListView list = (ExpandableListView) findViewById(R.id.expertise);
            final ExpandListAdapter adapter = new ExpandListAdapter(this, courseList);
            list.setAdapter(adapter);
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                for (int j = 0; j < ((ExpandListGroup) adapter.getGroup(i)).getChild().getList().size(); j++) {
                    lst.add(((ExpandListGroup) adapter.getGroup(i)).getChild().getList().get(j));
                }
            }
        }
    }

    public void createAccount(View view) {
        Student student = ProfileForm.student;
        Database db = new Database(this);
        db.insert(student);
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
