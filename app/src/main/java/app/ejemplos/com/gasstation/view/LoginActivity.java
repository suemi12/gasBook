package app.ejemplos.com.gasstation.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import app.ejemplos.com.gasstation.R;

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private static final String DUMMY_USER_ID = "0000000000";
    private static final String DUMMY_PASSWORD = "dummy_password";
    private UserLoginTask mAuthTask = null;


    private ImageView mLogoView;
    private EditText mUserIdView;
    private EditText mPasswordView;
    private TextInputLayout mFloatLabelUserId;
    private TextInputLayout mFloatLabelPassword;
    private View mProgressView;
    private View mLoginFormView;
    private Button mSignInButton;
    private TextView registrarse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLogoView = findViewById(R.id.image_logo);
        mUserIdView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mFloatLabelUserId = findViewById(R.id.float_label_user_id);
        mFloatLabelPassword = findViewById(R.id.float_label_password);
        mSignInButton = findViewById(R.id.email_sign_in);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        registrarse = findViewById(R.id.registrarse);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }

        });


        registrarse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ChekinActivity.class));
            }
        });

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mFloatLabelUserId.setError(null);
        mFloatLabelPassword.setError(null);

        // Store values at the time of the login attempt.
        String userId = mUserIdView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            // mFloatLabelPassword.setError(getString(R.string.error_field_require));
            focusView = mFloatLabelPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            // mFloatLabelPassword.setError(getString(R.string.error_invalid_password));
            focusView = mFloatLabelPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userId)) {
            //mFloatLabelUserId.setError(getString(R.string.error_field_required));
            focusView = mFloatLabelUserId;
            cancel = true;
        } else if (!isUserIdValid(userId)) {
            //mFloatLabelUserId.setError(getString(R.string.error_invalid_user_id));
            focusView = mFloatLabelUserId;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(userId, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUserIdValid(String userId) {
        return userId.length() == 10;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        int visibility = show ? View.GONE : View.VISIBLE;
        mLogoView.setVisibility(visibility);
        mLoginFormView.setVisibility(visibility);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mUserId;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mUserId = email;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return 4;
            }

            if (!mUserId.equals(DUMMY_USER_ID)) {
                return 2;
            }

            if (!mPassword.equals(DUMMY_PASSWORD)) {
                return 3;
            }

            return 1;

        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            showProgress(false);

            switch (success) {
                case 1:
                    showAppointmentsScreen();
                    break;
                case 2:

                case 3:
                    showLoginError("Número de identificación o contraseña inválidos");
                    break;
                case 4:
                    // showLoginError(getString(R.string.error_server));
                    break;
            }
        }

        private void showAppointmentsScreen() {

        }

        private void showLoginError(String error) {

        }

        private boolean isOnline() {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }

    }
}



