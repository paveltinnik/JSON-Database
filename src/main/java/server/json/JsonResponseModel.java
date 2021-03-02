package server.json;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonResponseModel {
    private String response;
    private String reason;
    private JsonElement value;

    public JsonResponseModel(String response, String reason, JsonElement value) {
        this.value = value;
        this.reason = reason;
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public JsonElement getValue() {
        return value;
    }

    public void setValue(JsonElement value) {
        this.value = value;
    }

    public String getJsonString(){
        return new GsonBuilder()
            .registerTypeAdapter(JsonResponseModel.class, new JsonResponseModelSerializer())
            .create().toJson(this);
    }
}

class JsonResponseModelSerializer implements JsonSerializer<JsonResponseModel> {
    @Override
    public JsonElement serialize(JsonResponseModel src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        if (src.getReason().equals("") && src.getValue() == null) {
            result.addProperty("response", src.getResponse());

        } else if (src.getValue() == null) {
            result.addProperty("response", src.getResponse());
            result.addProperty("reason", src.getReason());

        } else if (src.getReason().equals("")) {
            result.addProperty("response", src.getResponse());

            if (src.getValue().isJsonPrimitive()) {
                result.addProperty("value", src.getValue().getAsString());
            } else {
                result.add("value", src.getValue());
            }
        }
        return result;
    }
}