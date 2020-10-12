package Team07.FamilyPlanning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    public EditText userId;
    public String username;
    public EditText passwordId;
    public String password;
    public EditText confirmId;
    public String confirm;

    public String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userId = findViewById(R.id.username);
        passwordId = findViewById(R.id.password);
        confirmId = findViewById(R.id.confirm_password);

    }


    //saves a user account checks if info is valid
    public void SaveBtn(View view) {
        username = userId.getText().toString();
        password = passwordId.getText().toString();
        confirm = confirmId.getText().toString();

        if (password.equals(confirm)&& !username.equals("") && !password.equals(""))
        {
            account = username+password;

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(account);
            myRef.setValue("Account");


            Toast.makeText(getApplicationContext(), account +" Account Saved Successfully", Toast.LENGTH_LONG).show();
            startMain();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please Enter Matching Passwords", Toast.LENGTH_LONG).show();
        }
    }

    //starts the main activity.
    public void startMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("account",account);
        startActivity(intent);
    }
}

