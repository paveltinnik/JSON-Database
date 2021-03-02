package server.database.personmodel;

import com.google.gson.*;
import server.database.personmodel.value.Value;

import java.lang.reflect.Type;

public class PersonModelDeserializer implements JsonDeserializer<PersonModel> {
    @Override
    public PersonModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        PersonModel personModel = new PersonModel();

        if (jsonObject.get("key") != null) {
            personModel.setKey(jsonObject.get("key").getAsString());
        }

        if (jsonObject.get("value") != null) {
            personModel.setValue(context.deserialize(jsonObject.get("value"), Value.class));
        }

        return personModel;
    }
}
