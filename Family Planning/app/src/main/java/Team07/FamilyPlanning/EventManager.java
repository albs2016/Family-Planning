package Team07.FamilyPlanning;

import com.google.gson.Gson;

public class  EventManager {
    private Gson gson;

    public EventManager()
    {
        gson = new Gson();
    }

    public  String EventToJson(Event event)
    {
        return gson.toJson(event);
    }
}
