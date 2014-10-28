package ooad.comet_tutors.Controllers.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ooad.comet_tutors.Models.Has_Expertise;
import ooad.comet_tutors.Models.Has_Query;
import ooad.comet_tutors.Controllers.PrimarySequence.MainFlow;
import ooad.comet_tutors.R;
import ooad.comet_tutors.Models.Student;
import ooad.comet_tutors.TechnicalServices.Database;
import ooad.comet_tutors.Models.Tutor;


/**
 * A login screen that offers login via email/password.

 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor>{

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private static Context context;
    public static Student student = null;
    public static Tutor tutor = null;
    public static List<Has_Query> hasQueryList = new ArrayList<Has_Query>();
    public static List<Has_Expertise> hasExpertiseList = new ArrayList<Has_Expertise>();
    private int successCounter = 0;
    private ProgressDialog pd;

    public static Context getContext()
    {
        return context;
    }

    public void registerForm(View view)
    {
        final View superView = view;
        String accounts[] = new String[] {"Student", "Tutor"};
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("What type of account?")
                .setItems(accounts, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:
                                Intent StudentIntent = new Intent(superView.getContext(), ProfileForm.class);
                                StudentIntent.putExtra("Type", "Student");
                                superView.getContext().startActivity(StudentIntent);
                                break;
                            case 1:
                                Intent TutorIntent = new Intent(superView.getContext(), ProfileForm.class);
                                TutorIntent.putExtra("Type", "Tutor");
                                superView.getContext().startActivity(TutorIntent);
                                break;
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();
        context = mEmailView.getContext();
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            UserLoginTask ul = new UserLoginTask(mEmailView.getText().toString(), mPasswordView.getText().toString());
            ul.execute();
            pd = ProgressDialog.show(this, "Login", "Attempting to login...", true, false, null);
            try {
                ul.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void login(Boolean success)
    {
        if (!success) successCounter++;
        if (successCounter == 2)
        {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            successCounter = 0;
            pd.dismiss();
        }
        if (success)
        {
            pd.dismiss();
            Intent mainFlow = new Intent(this, MainFlow.class);
            if (student != null) mainFlow.putExtra("Profile", (android.os.Parcelable) student);
            else mainFlow.putExtra("Profile", (android.os.Parcelable) tutor);
            getContext().startActivity(mainFlow);
        }
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                                                                     .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                final MobileServiceClient mClient = new MobileServiceClient("https://comettutors.azure-mobile.net/", "IwckhnZstWukKYYPLIOWdyjtnadGqP75", context);
                MobileServiceTable<Student> studentTable = mClient.getTable(Student.class);
                studentTable.where()
                        .field("email").eq(mEmail)
                        .and()
                        .field("password").eq(mPassword)
                        .execute(new TableQueryCallback<Student>() {
                            @Override
                            public void onCompleted(List<Student> result, int count, Exception exception, ServiceFilterResponse response) {
                                if (result.size() > 0) {
                                    LoginActivity.student = new Student(result.get(0));
                                    Database db = new Database(context);
                                    db.match(LoginActivity.student);
                                    MobileServiceTable<Has_Query> queryTable = mClient.getTable(Has_Query.class);
                                    queryTable.where()
                                            .field("email").eq(student.getEmail())
                                            .execute(new TableQueryCallback<Has_Query>() {
                                                @Override
                                                public void onCompleted(List<Has_Query> result, int count, Exception exception, ServiceFilterResponse response) {
                                                    hasQueryList = result;
                                                    login(true);
                                                }
                                            });
                                    //login(true);
                                } else login(false);
                            }
                        });
                MobileServiceTable<Tutor> tutorTable = mClient.getTable(Tutor.class);
                tutorTable.where()
                        .field("email").eq(mEmail)
                        .and()
                        .field("password").eq(mPassword)
                        .execute(new TableQueryCallback<Tutor>() {
                            @Override
                            public void onCompleted(List<Tutor> result, int count, Exception exception, ServiceFilterResponse response) {
                                if (result.size() > 0) {
                                    LoginActivity.tutor = new Tutor(result.get(0));
                                    MobileServiceTable<Has_Expertise> expertiseTable = mClient.getTable(Has_Expertise.class);
                                    expertiseTable.where()
                                            .field("email").eq(tutor.getEmail())
                                            .execute(new TableQueryCallback<Has_Expertise>() {
                                                @Override
                                                public void onCompleted(List<Has_Expertise> result, int count, Exception exception, ServiceFilterResponse response) {
                                                    hasExpertiseList = result;
                                                    login(true);
                                                }
                                            });
                                } else login(false);
                            }
                        });

            } catch (MalformedURLException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                //finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}



