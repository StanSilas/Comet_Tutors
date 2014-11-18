package ooad.comet_tutors.Controllers.PrimarySequence;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import ooad.comet_tutors.R;
import ooad.comet_tutors.TechnicalServices.Database;

public class SelectedTutor extends Activity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_tutor);
        TextView firstName = (TextView) findViewById(R.id.tutorFirstName);
        TextView lastName = (TextView) findViewById(R.id.tutorLastName);
        TextView phoneNumber = (TextView) findViewById(R.id.tutorPhoneNumber);
        TextView email = (TextView) findViewById(R.id.tutorEmail);
        TextView expertise = (TextView) findViewById(R.id.tutorExpertise);
        ListView queryList = (ListView) findViewById(R.id.queryList);
        final String selectedExpertise = this.getIntent().getStringExtra("Expertise");
        final String[] room = {""};
        final EditText input = new EditText(context);
        if (MatchesFragment.matchedTutor != null) {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, MatchesFragment.matchedTutor.getSchedule());
            queryList.setAdapter(adapter);
            queryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                    final int index = i;
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                            .setTitle("Schedule")
                            .setMessage("Do you want to schedule an appointment at this time?")
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            final AlertDialog.Builder roomPicker = new AlertDialog.Builder(context)
                                                    .setTitle("Meeting Location")
                                                    .setMessage("Please pick a meeting location")
                                                    .setView(input)
                                                    .setPositiveButton("Ok",
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    room[0] = input.getText().toString();
                                                                    Database db = new Database(context);
                                                                    db.schedule(MainFlow.student, MatchesFragment.matchedTutor, room[0], MatchesFragment.matchedTutor.getSchedule().get(index), selectedExpertise);
                                                                    MatchesFragment.matchedTutor.getSchedule().remove(index);
                                                                    adapter.notifyDataSetChanged();
                                                                }
                                                            })
                                                    .setNegativeButton("Cancel",
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    //User pressed cancel
                                                                    ((ViewGroup) input.getParent()).removeView(input);
                                                                }
                                                            });
                                            roomPicker.show();
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //User pressed cancel
                                        }
                                    });
                   alertDialog.show();
                }
            });
            firstName.setText(MatchesFragment.matchedTutor.getFirstName());
            lastName.setText(MatchesFragment.matchedTutor.getLastName());
            phoneNumber.setText(MatchesFragment.matchedTutor.getPhoneNumber());
            email.setText(MatchesFragment.matchedTutor.getEmail());
            expertise.setText(selectedExpertise);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.selected_tutor, menu);
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
