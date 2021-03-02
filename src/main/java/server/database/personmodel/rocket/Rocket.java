package server.database.personmodel.rocket;

import com.google.gson.*;

public class Rocket {
    private String name;
    private String launches;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLaunches() {
        return launches;
    }

    public void setLaunches(String launches) {
        this.launches = launches;
    }

    public String getJsonString() {
        return new Gson().toJson(this);
    }
}

