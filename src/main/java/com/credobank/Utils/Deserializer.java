package com.credobank.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

public class Deserializer {

    private static final Gson gson = new Gson();

    public static <T> T deserialize(Response response, Class<T> clazz) {
        return gson.fromJson(response.getBody().asString(), clazz);
    }

    public static <T> List<T> deserializeList(Response response, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        JsonParser
                .parseString(response.getBody().asString())
                .getAsJsonArray()
                .forEach(el -> result.add(gson.fromJson(el,clazz)));
        return result;
    }
}
