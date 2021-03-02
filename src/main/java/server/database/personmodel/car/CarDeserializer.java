package server.database.personmodel.car;

import com.google.gson.*;

import java.lang.reflect.Type;

public class CarDeserializer implements JsonDeserializer<Car> {
    @Override
    public Car deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Car car = new Car();

        if (jsonObject.get("model") != null) {
            car.setModel(jsonObject.get("model").getAsString());
        }

        if (jsonObject.get("year") != null) {
            car.setYear(jsonObject.get("year").getAsString());
        }

        return car;
    }
}
