package server.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class JsonRequest {
    private String type;
    private JsonElement key;
    private JsonElement value;

    public JsonRequest(String type, JsonElement key, JsonElement value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JsonElement getKey() {
        return key;
    }

    public void setKey(JsonElement key) {
        this.key = key;
    }

    public JsonElement getValue() {
        return value;
    }

    public void setValue(JsonElement value) {
        this.value = value;
    }

    public String getJsonString(){
        return new Gson().toJson(this);
    }
}
