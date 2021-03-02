package server.database.personmodel.car;

import com.google.gson.*;

public class Car {
    private String model;
    private String year;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getJsonString() {
        return new Gson().toJson(this);
    }
}

