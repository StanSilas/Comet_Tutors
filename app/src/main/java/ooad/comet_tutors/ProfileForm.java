package ooad.comet_tutors;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TabHost;

import ooad.comet_tutors.BackendClasses.Tutor;
import ooad.comet_tutors.TutorForm.AccountForm;
import ooad.comet_tutors.TutorForm.InformationForm;
import ooad.comet_tutors.TutorForm.ScheduleForm;


public class ProfileForm extends TabActivity {

    public static Tutor tutor = new Tutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_form);
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        Log.w("Test", getIntent().getStringExtra("Type"));
        TabHost.TabSpec tab1 = tabHost.newTabSpec("Account");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Information");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Schedule");

        tab1.setIndicator("Account");
        tab1.setContent(new Intent(this,AccountForm.class));

        tab2.setIndicator("Information");
        if (getIntent().getStringExtra("Type").equals("Student")) {
            Intent information = new Intent(this, InformationForm.class);
            LinearLayout ll = (LinearLayout) findViewById(R.id.LinearLayout);
            ll.removeView(findViewById(R.id.expertise));
            tab2.setContent(information);
        }
        else tab2.setContent(new Intent(this, InformationForm.class));

        tab3.setIndicator("Schedule");
        tab3.setContent(new Intent(this,ScheduleForm.class));


        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        if (getIntent().getStringExtra("Type").equals("Tutor"))tabHost.addTab(tab3);
    }

    public void switchTab(int tab)
    {
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setCurrentTab(tab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_form, menu);
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
