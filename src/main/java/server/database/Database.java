package server.database;

import com.google.gson.JsonElement;

public class Database {
    public String get(JsonElement key) {
        return Commands.get(key);
    }

    public String set(JsonElement key, JsonElement value) {
        return Commands.set(key, value);
    }

    public String delete(JsonElement key) {
        return Commands.delete(key);
    }

    public String exit() {
        return Commands.exit();
    }
}
