package ooad.comet_tutors.TutorForm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ooad.comet_tutors.CommonForms.Database;
import ooad.comet_tutors.CommonForms.InformationForm;
import ooad.comet_tutors.CommonForms.ProfileForm;
import ooad.comet_tutors.R;


public class ScheduleForm extends Activity {

    private List<String> schedule = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);
    }

    public void createAccount(View view)
    {
        List<String> expertise = new ArrayList<String>();
        List<String> availability = new ArrayList<String>();
        ExpandableListView expertiseList = (ExpandableListView) findViewById(R.id.expertise);

        for (String str : schedule)
        {
            availability.add(str);
        }

        List<CheckBox> lst = InformationForm.getExpandableListChildren();

        for (CheckBox box : lst)
        {
            if (box.isChecked()) {
                expertise.add((String)box.getText());
            }
        }
        Tutor tutor = ProfileForm.tutor;
        tutor.setExpertise(expertise);
        tutor.setSchedule(availability);
        Database db = new Database(this);
        db.execute(tutor);
    }

    public void addToSchedule(View view)
    {

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        ListView lv = (ListView) findViewById(R.id.listView);
        String selectedDate = DateFormat.getDateInstance().format(datePicker.getCalendarView().getDate());
        Format formatter;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

        formatter = new SimpleDateFormat("HH:mm");
        selectedDate += " " + formatter.format(calendar.getTime());
        schedule.add(selectedDate);
        Log.w("Date", selectedDate);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, schedule );
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i;
                Log.w("Test", "HERE");
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                schedule.remove(index);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schedule_form, menu);
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
