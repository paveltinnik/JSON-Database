package server.database.personmodel.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ValueSerializer implements JsonSerializer<Value> {
    @Override
    public JsonElement serialize(Value src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject value = new JsonObject();

        value.addProperty("name", src.getName());
        value.add("car", context.serialize(src.getCar()));
        value.add("rocket", context.serialize(src.getRocket()));

        return value;
    }
}
