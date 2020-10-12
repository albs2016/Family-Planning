package Team07.FamilyPlanning;

public class Event {
   private  int month;
   private  int day;
   private  int year;
   private int hour;
   private int minute;
   private  String title;
   private String description;
   private String account;


   //Event constructor
    public Event() {
        month = 0;
        day = 0;
        year = 0;
        hour = 0;
        minute = 0;
        title = "";
        description = "";
    }


    //Constructor
    public Event(int month, int day, int year, String title, String description, int hour, int minute, String account) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.title = title;
        this.description = description;
        this.hour = hour;
        this.minute = minute;
        this.account = account;
    }


    //A to string function to display the event
    public String toString()
    {
        String TheEvent =  hour + ":" + minute + " " + title  + " - " + description;
        return TheEvent;
    }


    //function to autofill the date with the correct format.
    public String autofillDate()
    {
        String date = month + "/" + day + "/" + year;
        return date;
    }

    //a function to make a path for firebase
    public String pathString()
    {
        return  account + "/" + "Event" + "/" +"Year -" + year + "/" +"Month -"+month +"/" +"Day -" + day +
                "/" + "Hour -" + hour + ":" + minute ;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getAccount() { return account; }

    public void setAccount(String account) { this.account = account; }
}
