package server.database.personmodel.rocket;

import com.google.gson.*;

import java.lang.reflect.Type;

public class RocketDeserializer implements JsonDeserializer<Rocket> {
    @Override
    public Rocket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Rocket rocket = new Rocket();

        if (jsonObject.get("name") != null) {
            rocket.setName(jsonObject.get("name").getAsString());
        }

        if (jsonObject.get("launches") != null) {
            rocket.setLaunches(jsonObject.get("launches").getAsString());
        }

        return rocket;
    }
}
