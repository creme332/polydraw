package com.github.creme332.utils;

import java.awt.Color;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {

    @Override
    public JsonElement serialize(Color color, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonColor = new JsonObject();
        jsonColor.addProperty("r", color.getRed());
        jsonColor.addProperty("g", color.getGreen());
        jsonColor.addProperty("b", color.getBlue());
        jsonColor.addProperty("a", color.getAlpha()); // include alpha if needed
        return jsonColor;
    }

    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
        int r = jsonObject.get("r").getAsInt();
        int g = jsonObject.get("g").getAsInt();
        int b = jsonObject.get("b").getAsInt();
        int a = jsonObject.has("a") ? jsonObject.get("a").getAsInt() : 255; // default to fully opaque if alpha not
                                                                            // specified
        return new Color(r, g, b, a);
    }
}
