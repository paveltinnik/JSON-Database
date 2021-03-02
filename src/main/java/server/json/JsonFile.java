package server.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class JsonFile {
    private JsonElement key;
    private JsonElement value;

    public JsonFile(JsonElement key, JsonElement value) {
        this.key = key;
        this.value = value;
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
