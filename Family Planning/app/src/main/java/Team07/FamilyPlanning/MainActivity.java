package Team07.FamilyPlanning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {


    private int month;
    private int day;
    private int year;

    public ListView eventId;
    public TextView headerId;

    public String account;

    public List<Event> events = new ArrayList<>();
    public List<String> noEvents = new ArrayList<>();



    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        account = getIntent().getStringExtra("account");


        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        day = localCalendar.get(Calendar.DATE);
        month = localCalendar.get(Calendar.MONTH) + 1;
        year = localCalendar.get(Calendar.YEAR);
        getEventData();
        //This Chunk of code is what makes the Date for the Selected Date come up as a toast.
        CalendarView simpleCalendarView = findViewById(R.id.calendarView);
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth){
                setDay(dayOfMonth);
                setMonth(month + 1);
                setYear(year);
                getEventData();
            }
        });

        displayEvent();

        eventId = findViewById(R.id.event);

            eventId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override

                public void onItemClick(AdapterView<?> a, View v, int position,
                                       long id) {
                    if (!events.isEmpty()) {
                        Event check = new Event();
                        check = events.get(position);
             //           Toast.makeText(getApplicationContext(), check.toString(), Toast.LENGTH_LONG).show();
                        EditEvent(check);
                    }
               }
            });


    }



//This function gets the event data of a specific date from firebase and will call the display event fucntion.
    public void getEventData()
        {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference(account + "/"+ "Event" + "/" +"Year -" + year + "/" +"Month -"+month +"/" +"Day -" + day);


            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    events.clear();
                    noEvents.clear();
                    if(dataSnapshot.getValue(Event.class)!= null)
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        events.add((postSnapshot.getValue(Event.class)));//).eventString());
                    }
                    displayEvent();
                }

                @Override
               public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
        //starts an intent to go to the add event activity
    public void AddEventButton(View view)
    {
        Intent intent = new Intent(this, Add_Event.class);
        intent.putExtra("day", getDay());
        intent.putExtra("month", getMonth());
        intent.putExtra("year", getYear());
        intent.putExtra("account", account);
        startActivity(intent);
    }

    //When the uses presses an event goes to teh add event activity with the activity being auto populated
    public void EditEvent(Event check)
    {
        Toast.makeText(getApplicationContext(), check.getTitle(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, Add_Event.class);

        intent.putExtra("day",check.getDay());
        intent.putExtra("month",check.getMonth());
        intent.putExtra("year",check.getYear());
        intent.putExtra("minute",check.getMinute());
        intent.putExtra("hour",check.getHour());
        intent.putExtra("title",check.getTitle());
        intent.putExtra("desc",check.getDescription());
        intent.putExtra("account",check.getAccount());
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        displayEvent();
    }


//Diplays the days events under the calendar
    public void displayEvent()
    {
        displayHeader();
        eventId = findViewById(R.id.event);

        if (!events.isEmpty()) {
            ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, events);

            eventId.setAdapter(adapter);

        }
        else {
            noEvents.add("No Events on This Date.");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, noEvents);
            eventId.setAdapter(adapter);

        }
    }

    //Fills the space with the current date under the calendar
    public void displayHeader()
    {
        headerId = findViewById(R.id.header);
        String MonthString = "NO MONTH";
        if (month == 1)
            MonthString = "January";
        if (month == 2)
            MonthString = "February";
        if (month == 3)
            MonthString = "March";
        if (month == 4)
            MonthString = "April";
        if (month == 5)
            MonthString = "May";
        if (month == 6)
            MonthString = "June";
        if (month == 7)
            MonthString = "July";
        if (month == 8)
            MonthString = "August";
        if (month == 9)
            MonthString = "September";
        if (month == 10)
            MonthString = "October";
        if (month == 11)
            MonthString = "November";
        if (month == 12)
            MonthString = "December";


        headerId.setText("Events on " + MonthString + " - " + day);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
