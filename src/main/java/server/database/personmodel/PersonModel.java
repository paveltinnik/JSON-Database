package server.database.personmodel;

import com.google.gson.*;
import server.database.personmodel.car.Car;
import server.database.personmodel.car.CarSerializer;
import server.database.personmodel.rocket.Rocket;
import server.database.personmodel.rocket.RocketSerializer;
import server.database.personmodel.value.Value;
import server.database.personmodel.value.ValueSerializer;

public class PersonModel {
    private String key;
    private Value value;

    PersonModel() {}

    PersonModel(String key, Value value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getJsonString(){
        return new GsonBuilder()
                .registerTypeAdapter(PersonModel.class, new PersonModelSerializer())
                .registerTypeAdapter(Value.class, new ValueSerializer())
                .registerTypeAdapter(Car.class, new CarSerializer())
                .registerTypeAdapter(Rocket.class, new RocketSerializer())
                .create().toJson(this);
    }

    public void complexSet(JsonArray keyArray, JsonElement value) {
        String personField = keyArray.get(1).getAsString();
        String valueString = value.getAsString();

        switch (personField) {
            case "name":
                this.setKey(valueString);
                break;
            case "car":
                if (keyArray.size() == 2) {
                    Car carValue = new Gson().fromJson(value, Car.class);
                    this.getValue().setCar(carValue);
                } else {
                    String carField = keyArray.get(2).getAsString();
                    if (carField.equals("model")) {
                        Car car = this.getValue().getCar();
                        car.setModel(valueString);
                    } else if (carField.equals("year")) {
                        Car car = this.getValue().getCar();
                        car.setYear(valueString);
                    }
                }
                break;
            case "rocket":
                if (keyArray.size() == 2) {
                    Rocket rocketValue = new Gson().fromJson(value, Rocket.class);
                    this.getValue().setRocket(rocketValue);
                } else {
                    String rocketField = keyArray.get(2).getAsString();
                    if (rocketField.equals("name")) {
                        Rocket rocket = this.getValue().getRocket();
                        rocket.setName(valueString);
                    } else if (rocketField.equals("launches")) {
                        Rocket rocket = this.getValue().getRocket();
                        rocket.setLaunches(valueString);
                    }
                }
                break;
        }
    }

    public JsonElement complexGet(JsonArray keyArray) {
        if (keyArray.size() == 1) {
            return this.getValue().getAsJsonElement();
        } else {
            String personField = keyArray.get(1).getAsString();

            switch (personField) {
                case "name":
                    return new Gson().toJsonTree(this.getValue().getName());
                case "car":
                    if (keyArray.size() == 2) {
                        return new Gson().toJsonTree(this.getValue().getCar());
                    }
                    String carField = keyArray.get(2).getAsString();

                    if (carField.equals("model")) {
                        return new Gson().toJsonTree(this.getValue().getCar().getModel());
                    } else if (carField.equals("year")) {
                        return new Gson().toJsonTree(this.getValue().getCar().getYear());
                    }
                    break;
                case "rocket":
                    if (keyArray.size() == 2) {
                        return new Gson().toJsonTree(this.getValue().getRocket());
                    }

                    String rocketField = keyArray.get(2).getAsString();

                    if (rocketField.equals("name")) {
                        return new Gson().toJsonTree(this.getValue().getRocket().getName());
                    } else if (rocketField.equals("launches")) {
                        return new Gson().toJsonTree(this.getValue().getRocket().getLaunches());
                    }
                    break;
            }
        }
        return null;
    }

    public void complexDelete(JsonArray keyArray) {
        String personField = keyArray.get(1).getAsString();

        switch (personField) {
            case "name":
                this.getValue().setName(null);
                break;
            case "car":
                if (keyArray.size() == 2) {
                    this.getValue().setCar(null);
                }

                String carField = keyArray.get(2).getAsString();

                if (carField.equals("model")) {
                    this.getValue().getCar().setModel(null);
                } else if (carField.equals("year")) {
                    this.getValue().getCar().setYear(null);
                }
                break;
            case "rocket":
                if (keyArray.size() == 2) {
                    this.getValue().setRocket(null);
                }

                String rocketField = keyArray.get(2).getAsString();

                if (rocketField.equals("name")) {
                    this.getValue().getRocket().setName(null);
                } else if (rocketField.equals("launches")) {
                    this.getValue().getRocket().setLaunches(null);
                }
                break;
        }
    }
}