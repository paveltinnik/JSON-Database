package server.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import server.database.personmodel.*;
import server.database.personmodel.car.*;
import server.database.personmodel.rocket.*;
import server.database.personmodel.value.*;
import server.json.JsonFile;
import server.json.JsonResponseModel;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Commands {
    static String path = "src\\main\\java\\server\\data\\db.json";
    private static final String OK = "OK";
    private static final String ERROR = "ERROR";

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Lock readLock = lock.readLock();
    private static final Lock writeLock = lock.writeLock();

    // Serialize
    final static Gson toGson = new GsonBuilder()
            .registerTypeAdapter(PersonModel.class, new PersonModelSerializer())
            .registerTypeAdapter(Value.class, new ValueSerializer())
            .registerTypeAdapter(Car.class, new CarSerializer())
            .registerTypeAdapter(Rocket.class, new RocketSerializer())
            .create();

    // Deserialize
    final static Gson fromGson = new GsonBuilder()
            .registerTypeAdapter(PersonModel.class, new PersonModelDeserializer())
            .registerTypeAdapter(Value.class, new ValueDeserializer())
            .registerTypeAdapter(Car.class, new CarDeserializer())
            .registerTypeAdapter(Rocket.class, new RocketDeserializer())
            .create();

    public static String get(JsonElement key) {
        String response = "";

        readLock.lock();
        try {
            if (key.isJsonPrimitive()) {
                JsonFile jsonFile = new Gson().fromJson(ReaderWriter.read(path), JsonFile.class);

                if (jsonFile.getKey() == null || !jsonFile.getKey().equals(key)) {
                    response = new JsonResponseModel(ERROR, "No such key", null).getJsonString();
                } else if (jsonFile.getKey().equals(key)) {
                    response = new JsonResponseModel(OK, "", jsonFile.getValue()).getJsonString();
                }
            } else if (key.isJsonArray()) {
                PersonModel personModel = fromGson.fromJson(ReaderWriter.read(path), PersonModel.class);

                response = new JsonResponseModel(OK, "", personModel.complexGet(key.getAsJsonArray())).getJsonString();
            }
        } catch (Exception e) {
            response = new JsonResponseModel(ERROR, "No such key", null).getJsonString();
        }
        readLock.unlock();

        return response;
    }

    public static String set(JsonElement key, JsonElement value) {
        String response = "";

        writeLock.lock();
        try {
            if (!key.isJsonArray()) {
                JsonFile jsonWriteToFile = new JsonFile(key, value);
                String json = jsonWriteToFile.getJsonString();
                ReaderWriter.write(path, json);

            } else if (key.isJsonArray()) {
                JsonArray keyArray = key.getAsJsonArray();

                PersonModel personModel = fromGson.fromJson(ReaderWriter.read(path), PersonModel.class);

                personModel.complexSet(keyArray, value);

                String json = toGson.toJson(personModel);
                ReaderWriter.write(path, json);
            }
            response = new JsonResponseModel(OK, "", null).getJsonString();

        } catch (Exception e) {
            response =  new JsonResponseModel(ERROR, "No such key", null).getJsonString();
        }
        writeLock.unlock();

        return response;
    }

    public static String delete(JsonElement key) {
        String response = "";

        // Read data from db.json
        writeLock.lock();
        JsonFile jsonFile = new Gson().fromJson(ReaderWriter.read(path), JsonFile.class);

        if (jsonFile.getKey() == null || !jsonFile.getKey().equals(key.getAsJsonArray().get(0))) {
            response = new JsonResponseModel(ERROR, "No such key", null).getJsonString();
        } else {
            if (key.isJsonPrimitive()) {
                // Write empty json to db.json
                String json = "{}";
                ReaderWriter.write(path, json);

                response = new JsonResponseModel(OK, "", null).getJsonString();
            } else {
                PersonModel personModel = fromGson.fromJson(ReaderWriter.read(path), PersonModel.class);

                // Do delete
                personModel.complexDelete(key.getAsJsonArray());

                String json = toGson.toJson(personModel);
                ReaderWriter.write(path, json);

                response = new JsonResponseModel(OK, "", null).getJsonString();
            }
        }
        writeLock.unlock();

        return response;
    }

    public static String exit() {
        return new JsonResponseModel(OK, "", null).getJsonString();
    }
}
