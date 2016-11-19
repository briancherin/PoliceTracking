package joseph.com.policetracking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import static joseph.com.policetracking.MainActivity.aFlag;
import static joseph.com.policetracking.MainActivity.dFlag;
import static joseph.com.policetracking.MainActivity.fFlag;
import static joseph.com.policetracking.MainActivity.mAuth;
import static joseph.com.policetracking.MainActivity.pFlag;

public class Dispatch extends AppCompatActivity implements View.OnClickListener {

    private String email, password;
    private EditText usernameInput, passwordInput;
    private Button login;
    private TextView create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);

        usernameInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        create = (TextView) findViewById(R.id.create);

        FirebaseUser user = mAuth.getCurrentUser();
        if ((user != null) && (dFlag == 1 && pFlag == 0 && aFlag == 0 && fFlag == 0)) {
            Intent intent = new Intent(Dispatch.this, DispatchW.class);
            startActivity(intent);
        }



        login.setOnClickListener(this);
        create.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.login:
                login();
                break;

            case R.id.create:
                Intent intent = new Intent(Dispatch.this, Create.class);
                startActivity(intent);
                break;

            default:
                break;

        }
    }

    private void login() {
        email = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        usernameInput.setText("");
                        passwordInput.setText("");
                        Intent intent;
                        if(MainActivity.dFlag == 1) {
                            intent = new Intent(Dispatch.this, DispatchW.class);
                            startActivity(intent);
                        }

                        else if(MainActivity.pFlag == 1) {
                            intent = new Intent(Dispatch.this, Police.class);
                            startActivity(intent);
                        }

                        else if(MainActivity.aFlag == 1) {
                            intent = new Intent(Dispatch.this, Ambulance.class);
                            startActivity(intent);
                        }

                        else if(MainActivity.fFlag == 1) {
                            intent = new Intent(Dispatch.this, Firemen.class);
                            startActivity(intent);
                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            System.out.println("Login Successfull");
                        }
                    }
                });
    }

}

