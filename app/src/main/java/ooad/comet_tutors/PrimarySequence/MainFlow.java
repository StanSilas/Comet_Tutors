package ooad.comet_tutors.PrimarySequence;

import android.app.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ooad.comet_tutors.CommonForms.Database;
import ooad.comet_tutors.CommonForms.Has_Expertise;
import ooad.comet_tutors.CommonForms.Has_Query;
import ooad.comet_tutors.CommonForms.LoginActivity;
import ooad.comet_tutors.List.Matches;
import ooad.comet_tutors.ExpandableList.ExpandListAdapter;
import ooad.comet_tutors.ExpandableList.ExpandListGroup;
import ooad.comet_tutors.R;
import ooad.comet_tutors.StudentForm.Student;
import ooad.comet_tutors.TutorForm.Tutor;

public class MainFlow extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ProfileFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener, MatchesFragment.OnFragmentInteractionListener {

    public static Tutor tutor = null;
    public static Student student = null;
    public static String type = null;
    public static ProgressDialog pd;
    public static Context context;
    ExpandableListAdapter adp = null;




    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_flow);
        context = this;
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        if (LoginActivity.student != null)
        {
            type = "Student";
            student = new Student(LoginActivity.student);
        }
        else {
            type = "Tutor";
            tutor = new Tutor(LoginActivity.tutor);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        switch (position)
        {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ProfileFragment())
                        .commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new AccountFragment())
                        .commit();
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new MatchesFragment())
                        .commit();
        }
    }

    public ExpandableListView createExpandableListView(View view)
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
        ExpandableListView list = new ExpandableListView(view.getContext());
        ExpandListAdapter adapter = new ExpandListAdapter(this, courseList);
        adp = adapter;
        list.setAdapter(adapter);
        return list;
    }

    public void addToList(View view)
    {
        final String[] queryToAdd = new String[1];
        final String[] expertiseToAdd = new String[1];
        final View tempView = view;
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
        if (type.equals("Student")) {
            alertDialog.setTitle("Query");
            alertDialog.setMessage("Enter your query");
        }
        else
        {
            alertDialog.setTitle("Expertise");
            alertDialog.setMessage("Choose your expertise");
        }

        final ExpandableListView input = createExpandableListView(view);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.height = WindowManager.LayoutParams.FILL_PARENT;

        alertDialog.setView(input);
        final ExpandableListAdapter adapter = adp;
       // input.setLayoutParams(lp);
        if (type.equals("Student")) {
            alertDialog.setPositiveButton("Enter",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (int j = 0; j < adapter.getGroupCount(); j++) {
                                for (int k = 0; k < ((ExpandListGroup) adapter.getGroup(j)).getChild().getList().size(); k++) {
                                    if (((ExpandListGroup) adapter.getGroup(j)).getChild().getList().get(k).isChecked() && !ProfileFragment.queriesList.contains(((ExpandListGroup) adapter.getGroup(j)).getChild().getList().get(k).getText().toString())) {
                                        ProfileFragment.queriesList.add(((ExpandListGroup) adapter.getGroup(j))
                                                .getChild()
                                                .getList()
                                                .get(k)
                                                .getText()
                                                .toString());
                                    } else if (!((ExpandListGroup) adapter.getGroup(j)).getChild().getList().get(k).isChecked() && ProfileFragment.queriesList.contains(((ExpandListGroup) adapter.getGroup(j)).getChild().getList().get(k).getText().toString())) {
                                        ProfileFragment.queriesList.remove(((ExpandListGroup) adapter.getGroup(j))
                                                .getChild()
                                                .getList()
                                                .get(k)
                                                .getText()
                                                .toString());
                                    }
                                }
                            }
                            //queryToAdd[0] = input.getText().toString();
                            //ListView queries = (ListView) tempView.findViewById(R.id.tutorQueriesList);
                            ListView queries = ProfileFragment.getListView();
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(tempView.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, ProfileFragment.queriesList);
                            queries.setAdapter(adapter);
                            queries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    final int index = i;
                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                    builder.setMessage("Are you sure you want to delete this item?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    ProfileFragment.queriesList.remove(index);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // User cancelled the dialog
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            });
                        }
                    });
        }
        else
        {
            alertDialog.setPositiveButton("Enter",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (int j = 0; j < adapter.getGroupCount(); j++) {
                                for (int k = 0; k < ((ExpandListGroup) adapter.getGroup(j)).getChild().getList().size(); k++) {
                                    if (((ExpandListGroup) adapter.getGroup(j)).getChild().getList().get(k).isChecked() && !ProfileFragment.expertiseList.contains(((ExpandListGroup) adapter.getGroup(j)).getChild().getList().get(k).getText().toString())) {
                                        ProfileFragment.expertiseList.add(((ExpandListGroup) adapter.getGroup(j))
                                                .getChild()
                                                .getList()
                                                .get(k)
                                                .getText()
                                                .toString());
                                    } else if (!((ExpandListGroup) adapter.getGroup(j)).getChild().getList().get(k).isChecked() && ProfileFragment.expertiseList.contains(((ExpandListGroup) adapter.getGroup(j)).getChild().getList().get(k).getText().toString())) {
                                        ProfileFragment.expertiseList.remove(((ExpandListGroup) adapter.getGroup(j))
                                                .getChild()
                                                .getList()
                                                .get(k)
                                                .getText()
                                                .toString());
                                    }
                                }
                            }
                            ListView queries = ProfileFragment.getListView();
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(tempView.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, ProfileFragment.expertiseList);
                            queries.setAdapter(adapter);
                            queries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    final int index = i;
                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                    builder.setMessage("Are you sure you want to delete this item?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    ProfileFragment.expertiseList.remove(index);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // User cancelled the dialog
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            });
                        }
                    });
        }
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        Dialog d = alertDialog.create();
        d.show();
        d.getWindow().setAttributes(lp);
        if (type.equals("Student")) {
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                for (int j = 0; j < ((ExpandListGroup) adapter.getGroup(i)).getChild().getList().size(); j++) {
                    if (ProfileFragment.queriesList.contains(((ExpandListGroup) adapter.getGroup(i))
                            .getChild()
                            .getList()
                            .get(j)
                            .getText()
                            .toString())) {
                        ((ExpandListGroup) adapter.getGroup(i))
                                .getChild()
                                .getList()
                                .get(j)
                                .setChecked(true);
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                for (int j = 0; j < ((ExpandListGroup) adapter.getGroup(i)).getChild().getList().size(); j++) {
                    if (ProfileFragment.expertiseList.contains(((ExpandListGroup) adapter.getGroup(i))
                            .getChild()
                            .getList()
                            .get(j)
                            .getText()
                            .toString())) {
                        ((ExpandListGroup) adapter.getGroup(i))
                                .getChild()
                                .getList()
                                .get(j)
                                .setChecked(true);
                    }
                }
            }
        }

        //alertDialog.create().show();
        //ProfileFragment.addToList(queriesList);
    }

    public void save(View view)
    {
        pd = ProgressDialog.show(view.getContext(), "Saving", "Saving results...", true, false, null);
        Database db = new Database(this);
        if (type.equals("Student")) {
            db.removeAll(student);
        }
        else db.removeAll(tutor);
    }

    public static void savePartTwo()
    {
        Database db = new Database(context);
        if (type.equals("Student")) {
            for (String str : ProfileFragment.queriesList) {
                Student st = student;
                Has_Query query = new Has_Query(student.getId(), student.getEmail(), str);
                db.insert(query);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            db.match(student);
        }
        else
        {
            for (String str : ProfileFragment.expertiseList) {
                Tutor tu = tutor;
                Has_Expertise expert = new Has_Expertise(tutor.getId(), tutor.getEmail(), str);
                db.insert(expert);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void match(View view)
    {
        Database db = new Database(this);
        db.match(student);
    }

    public static void dismissPD()
    {
        pd.dismiss();
    }
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                if (type.equals("Student")) mTitle = getString(R.string.title_section3op2);
                else mTitle = getString(R.string.title_section3op1);
                break;
        }

    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_flow, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_flow, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainFlow) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
