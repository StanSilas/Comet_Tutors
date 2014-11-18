package ooad.comet_tutors.Controllers.PrimarySequence;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ooad.comet_tutors.Models.Has_Expertise;
import ooad.comet_tutors.Models.Has_Query;
import ooad.comet_tutors.Controllers.Login.LoginActivity;
import ooad.comet_tutors.Models.Has_Schedule;
import ooad.comet_tutors.R;
import ooad.comet_tutors.Models.Student;
import ooad.comet_tutors.Models.Tutor;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static List<String> queriesList = new ArrayList<String>();
    public static List<String> expertiseList = new ArrayList<String>();
    public static List<String> scheduleList = new ArrayList<String>();
    private Student student = null;
    private Tutor tutor = null;
    private static View view;
    private static int init = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        if (MainFlow.type.equals("Student"))
        {
            student = new Student(LoginActivity.student);

        }
        else tutor = new Tutor(LoginActivity.tutor);
        TextView firstName = (TextView) view.findViewById(R.id.firstname);
        TextView lastName = (TextView) view.findViewById(R.id.lastname);
        TextView phoneNumber = (TextView) view.findViewById(R.id.tutorPhoneNumber);
        TextView email = (TextView) view.findViewById(R.id.email);

        if (MainFlow.type.equals("Student")) {
            ListView queries = (ListView) view.findViewById(R.id.queryList);
            List<Has_Query> lst = LoginActivity.hasQueryList;
            if (init == 0) {
                init++;
                for (Has_Query query : lst) {
                    queriesList.add(query.getQuery());
                }
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, queriesList);
            queries.setAdapter(adapter);
            queries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final int index = i;
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Are you sure you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    queriesList.remove(index);
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
            RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.RelativeLayout);
            ListView schedule = (ListView) view.findViewById(R.id.tutorScheduleList);
            Button scheduleButton = (Button) view.findViewById(R.id.addSchedule);
            TextView scheduleTitle = (TextView) view.findViewById(R.id.scheduleTitle);
            rl.removeView(schedule);
            rl.removeView(scheduleButton);
            rl.removeView(scheduleTitle);

            firstName.setText(student.getFirstName());
            lastName.setText(student.getLastName());
            phoneNumber.setText(student.getPhoneNumber());
            email.setText(student.getEmail());
        }
        else
        {
            ListView expertise = (ListView) view.findViewById(R.id.queryList);
            ListView schedule = (ListView) view.findViewById(R.id.tutorScheduleList);
            List<Has_Expertise> lst = LoginActivity.hasExpertiseList;
            List<Has_Schedule> lst2 = LoginActivity.hasScheduleList;
            if (init == 0) {
                init++;
                for (Has_Expertise expert : lst) {
                    expertiseList.add(expert.getExpertise());
                }
                for (Has_Schedule sched : lst2) {
                    scheduleList.add(sched.getSchedule());
                }
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, expertiseList);
            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, scheduleList);
            expertise.setAdapter(adapter);
            schedule.setAdapter(adapter2);
            expertise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final int index = i;
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Are you sure you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    expertiseList.remove(index);
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
            schedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final int index = i;
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Are you sure you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    scheduleList.remove(index);
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

            ((TextView) view.findViewById(R.id.listTitle)).setText("Expertise List");
            ((Button) view.findViewById(R.id.addQuery)).setText("New Expertise");
            firstName.setText(tutor.getFirstName());
            lastName.setText(tutor.getLastName());
            phoneNumber.setText(tutor.getPhoneNumber());
            email.setText(tutor.getEmail());
        }
        return view;
    }

    public static ListView getExpertiseListView()
    {
        return (ListView) view.findViewById(R.id.queryList);
    }

    public static ListView getScheduleListView()
    {
        return (ListView) view.findViewById(R.id.tutorScheduleList);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static void addToList(List<String> queriesList)
    {
        ListView queries = (ListView) view.findViewById(R.id.queryList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, queriesList);
        queries.setAdapter(adapter);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
