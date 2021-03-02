package server.database.personmodel.value;

import com.google.gson.*;
import server.database.personmodel.car.Car;
import server.database.personmodel.rocket.Rocket;

import java.lang.reflect.Type;

public class ValueDeserializer implements JsonDeserializer<Value> {
    @Override
    public Value deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Value value = new Value();

        if (jsonObject.get("name") != null) {
            value.setName(jsonObject.get("name").getAsString());
        }

        if (jsonObject.get("car") != null) {
            value.setCar(context.deserialize(jsonObject.get("car"), Car.class));
        }

        if (jsonObject.get("rocket") != null) {
            value.setRocket(context.deserialize(jsonObject.get("rocket"), Rocket.class));
        }

        return value;
    }
}
