package server.database.personmodel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class PersonModelSerializer implements JsonSerializer<PersonModel> {
    @Override
    public JsonElement serialize(PersonModel src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.addProperty("key", src.getKey());
        result.add("value", context.serialize(src.getValue()));

        return result;
    }
}
