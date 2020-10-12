package Team07.FamilyPlanning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Add_Event extends AppCompatActivity {

   //time picker stuff
    EditText chooseTime;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;


  //Normal Class Stuff
    public EditText titleId;
    public String title;
    public EditText descId;
    public String desc;
    public int day;
    public int year;
    public int month;
    public int hour;
    public int minute;

    public String account;

    private EditText dateId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Setting Minute to 100 is Actually Important don't remove
        minute = 100; // Don't remove event though it looks dumb

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__event);
        titleId = (EditText) findViewById(R.id.title);
        descId = (EditText) findViewById(R.id.Description);


        account = getIntent().getStringExtra("account");


        minute =  getIntent().getIntExtra("minute",minute);
        hour =  getIntent().getIntExtra("hour",hour);

        title =   getIntent().getStringExtra("title");
        titleId.setText(title);
        desc =  getIntent().getStringExtra("desc");
        descId.setText(desc);





        //Everything below is the clock stuff.
        chooseTime = findViewById(R.id.etChooseTime);


        chooseTime.setText(String.format("%02d:%02d", hour, minute));

        if (minute == 100) {
            calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            chooseTime.setText(String.format("%02d:%02d", hour, minute));
        }

        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
                     public void onClick(View view) {
                calendar = Calendar.getInstance();

                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

               timePickerDialog = new TimePickerDialog(Add_Event.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        hour = hourOfDay;
                        minute = minutes;
                        chooseTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);

                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });



        day = getIntent().getIntExtra("day", day);
        year = getIntent().getIntExtra("year", year);
        month = getIntent().getIntExtra("month", month);


        dateId = (EditText) findViewById(R.id.date);
        dateId.setText(autofillDate());


       // final Calendar myCalendar = Calendar.getInstance();

       final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int Year, int monthOfYear,
                                  int dayOfMonth) {

                year = Year;
                month =  1 + monthOfYear;
                day = dayOfMonth;
                dateId.setText(autofillDate());
            }

        };

        dateId.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               new DatePickerDialog (Add_Event.this,date, year, month - 1,
                       day).show();
            }
        });



    }

// fucntion to fill in the date with the correct layout
    public String autofillDate()
    {
        String date = month + "/" + day + "/" + year;
        return date;
    }


    //saves the event to firebase and then returns user to the main activity
    public void saveEvent(View view) {
        title = titleId.getText().toString();
        desc = descId.getText().toString();
        Event Save = new Event(month, day, year, title, desc, hour, minute, account);
        Toast.makeText(getApplicationContext(), Save.toString(), Toast.LENGTH_LONG).show();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Save.pathString());

        DatabaseReference deleteEvents = database.getReference(account + "/" + "Event" + "/" +"Year -"
                + year + "/" +"Month -"+getIntent().getIntExtra("month", month)
                +"/" +"Day -" + getIntent().getIntExtra("day", day) +
                "/" + "Hour -" + getIntent().getIntExtra("hour", hour) + ":" + getIntent().getIntExtra("minute", minute));
        if (getIntent().getIntExtra("minute", minute) < 99)
             deleteEvents.removeValue();

        myRef.setValue(Save)
          .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                BackToMain();
            }
        });

    }

    //this function is used to delete an event from firebase
    public void removeEvent (View view)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference deleteEvents = database.getReference(account + "/" + "Event" + "/" +"Year -"
                + year + "/" +"Month -"+getIntent().getIntExtra("month", month)
                +"/" +"Day -" + getIntent().getIntExtra("day", day) +
                "/" + "Hour -" + getIntent().getIntExtra("hour", hour) + ":" + getIntent().getIntExtra("minute", minute));
        if (getIntent().getIntExtra("minute", minute) < 99)
            deleteEvents.removeValue();
    }

    //brings user back to main.
    public void BackToMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("account",account);
        startActivity(intent);
    }
}
