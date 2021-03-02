package server.database.personmodel.rocket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class RocketSerializer implements JsonSerializer<Rocket> {
    @Override
    public JsonElement serialize(Rocket src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject rocket = new JsonObject();

        rocket.addProperty("name", src.getName());
        rocket.addProperty("launches", src.getLaunches());

        return rocket;
    }
}
