package Team07.FamilyPlanning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    public EditText userId;
    public String username;
    public EditText passwordId;
    public String password;
    public String account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userId = findViewById(R.id.username);
        passwordId = findViewById(R.id.password);
    }

    //uses presses to go to the Main Activity under a specific account
    public void loginBtn(View view) {

        username = userId.getText().toString();
        password = passwordId.getText().toString();
        account = username+password;




        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(account);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null && !account.equals("")) {
                        startMain();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Enter a Valid Username" +
                            " and Password", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    //User presses to go to the create account activity
    public void SignUpBtn(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }


    //goes to activity main
    public void startMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("account",account);
        startActivity(intent);
    }

}
