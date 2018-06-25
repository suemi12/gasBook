package app.ejemplos.com.gasstation.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.regex.Pattern;

import app.ejemplos.com.gasstation.R;

public class ChekinActivity extends AppCompatActivity implements View.OnClickListener,
        FirebaseAuth.AuthStateListener, TextWatcher {


    private static final String TAG = "ChekinActivity";
    private FirebaseAuth mAuth;

    private Button chekin;
    private TextInputLayout email;
    private TextInputLayout password;
    private TextInputLayout user;
    private TextInputLayout passwordAgain;
    private FrameLayout progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin);

        chekin = findViewById(R.id.email_sign_in);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        user = findViewById(R.id.user);
        passwordAgain = findViewById(R.id.password_again);
        progressView = findViewById(R.id.progress_view);
        chekin.setOnClickListener(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        user.getEditText().addTextChangedListener(this);
        password.getEditText().addTextChangedListener(this);
        passwordAgain.getEditText().addTextChangedListener(this);
        email.getEditText().addTextChangedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    private void showProgress(Boolean show) {
        if (show) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressView.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressView.setVisibility(View.GONE);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_sign_in:
                if (isValidData()) {
                    showProgress(true);
                    System.out.println("EMAIL: " + email);
                    System.out.println("PASSWORD: " + password);
                    mAuth.createUserWithEmailAndPassword
                            (email.getEditText().getText().toString(), password.getEditText().getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    showProgress(false);
                                    Log.d(TAG, "Usuario registrado" + task.isSuccessful());

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(ChekinActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ChekinActivity.this, R.string.complete, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(this);

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            System.out.println("EL USUARIO FUE REGISTRADO");
            // User is signed in
            Log.d(TAG, "El registro fue realizado con exito " + user.getUid());
        } else {
            System.out.println("USUARIO NO REGISTRADO");
            // User is signed out
            Log.d(TAG, "El registro no fue exitoso, intentalo de nuevo");
        }

    }

    private Boolean isValidData() {
        if (user.getEditText().getText().toString().isEmpty()) {
            user.setError(getString(R.string.user_name_error));
            return false;
        }
        if (password.getEditText().getText().toString().isEmpty()) {
            password.setError(getString(R.string.user_password_error));
            return false;
        }
        if (passwordAgain.getEditText().getText().toString().isEmpty()) {
            passwordAgain.setError(getString(R.string.user_pass_again_error));
            return false;
        }

        if (!Objects.equals(password.getEditText().getText().toString(), passwordAgain.getEditText().getText().toString())) {
            passwordAgain.setError(getString(R.string.password_again_error));
            return false;

        }

        if (email.getEditText().getText().toString().isEmpty()) {
            email.setError(getString(R.string.user_email_error));
            return false;
        }

       if(!Patterns.EMAIL_ADDRESS.matcher(email.getEditText().getText().toString()).matches()){
           email.setError(getString(R.string.password_invalid));
           return false;
       }

        return false;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        clearError();
    }

    private void clearError() {
        user.setError(null);
        password.setError(null);
        passwordAgain.setError(null);
        email.setError(null);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
