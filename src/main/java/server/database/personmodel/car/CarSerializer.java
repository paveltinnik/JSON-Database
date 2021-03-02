package server.database.personmodel.car;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class CarSerializer implements JsonSerializer<Car> {
    @Override
    public JsonElement serialize(Car src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject car = new JsonObject();

        car.addProperty("model", src.getModel());
        car.addProperty("year", src.getYear());

        return car;
    }
}
