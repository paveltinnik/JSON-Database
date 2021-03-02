package server.database.personmodel.value;

import com.google.gson.*;
import server.database.personmodel.car.Car;
import server.database.personmodel.car.CarSerializer;
import server.database.personmodel.rocket.Rocket;
import server.database.personmodel.rocket.RocketSerializer;

public class Value {
    private String name;
    private Car car;
    private Rocket rocket;

    Value() {}

    Value(String name, Car car, Rocket rocket) {
        this.name = name;
        this.car = car;
        this.rocket = rocket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public String getJsonString() {
        return new GsonBuilder()
                .registerTypeAdapter(Value.class, new ValueSerializer())
                .registerTypeAdapter(Car.class, new CarSerializer())
                .registerTypeAdapter(Rocket.class, new RocketSerializer())
                .create().toJson(this);
    }

    public JsonElement getAsJsonElement() {
        return new GsonBuilder()
                .registerTypeAdapter(Value.class, new ValueSerializer())
                .registerTypeAdapter(Car.class, new CarSerializer())
                .registerTypeAdapter(Rocket.class, new RocketSerializer())
                .create().toJsonTree(this);
    }
}

