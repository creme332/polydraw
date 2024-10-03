package com.github.creme332.utils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Point2DListAdapter implements JsonSerializer<List<Point2D>>, JsonDeserializer<List<Point2D>> {

    @Override
    public JsonElement serialize(List<Point2D> points, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();

        // Serialize each Point2D object in the list
        for (Point2D point : points) {
            JsonObject jsonPoint = new JsonObject();
            jsonPoint.addProperty("x", point.getX());
            jsonPoint.addProperty("y", point.getY());
            jsonArray.add(jsonPoint);
        }

        return jsonArray;
    }

    @Override
    public List<Point2D> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        List<Point2D> points = new ArrayList<>();

        // Deserialize each JSON object into a Point2D object
        for (JsonElement element : jsonArray) {
            JsonObject jsonPoint = element.getAsJsonObject();
            double x = jsonPoint.get("x").getAsDouble();
            double y = jsonPoint.get("y").getAsDouble();
            points.add(new Point2D.Double(x, y)); // Assuming you use Point2D.Double
        }

        return points;
    }
}
