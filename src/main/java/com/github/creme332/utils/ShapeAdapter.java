package com.github.creme332.utils;

import java.awt.Polygon;
import java.awt.Shape;
import java.lang.reflect.Type;

import com.github.creme332.controller.canvas.drawing.DrawLine;
import com.github.creme332.model.ShapeWrapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Class for GSON to help in serializing/deserializing a Shape from ShapeWrapper.
 * Such a shape can only be a Polygon or a Path2D.
 */
public class ShapeAdapter implements JsonSerializer<Shape>, JsonDeserializer<Shape> {

    @Override
    public JsonElement serialize(Shape shape, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonShape = new JsonObject();
        Gson gson = new Gson();

        // create a list of x and y coordinates
        double[][] coords = ShapeWrapper.getCoordinates(shape);
        JsonArray xcoordinatesArray = new JsonArray();
        JsonArray ycoordinatesArray = new JsonArray();

        // Iterate through the 2D array and convert the coordinates to integers
        for (int i = 0; i < coords.length; i++) {
            xcoordinatesArray.add((int) coords[i][0]); // Convert and store the x-coordinate
            ycoordinatesArray.add((int) coords[i][1]); // Convert and store the y-coordinate
        }

        jsonShape.add("xCoordinates", xcoordinatesArray);
        jsonShape.add("yCoordinates", ycoordinatesArray);
        jsonShape.addProperty("isLine", gson.toJson(ShapeWrapper.isLine(shape)));

        return jsonShape;
    }

    @Override
    public Shape deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
        final boolean isLine = jsonObject.get("isLine").getAsBoolean();

        // get array of x-coordinates
        final JsonArray xCoordinatesObj = jsonObject.get("xCoordinates").getAsJsonArray();
        int[] xCoordinates = new int[xCoordinatesObj.size()];
        for (int i = 0; i < xCoordinatesObj.size(); i++) {
            xCoordinates[i] = xCoordinatesObj.get(i).getAsInt();
        }

        // get array of y-coordinates
        final JsonArray yCoordinatesObj = jsonObject.get("yCoordinates").getAsJsonArray();
        int[] yCoordinates = new int[yCoordinatesObj.size()];
        for (int i = 0; i < yCoordinatesObj.size(); i++) {
            yCoordinates[i] = yCoordinatesObj.get(i).getAsInt();
        }

        // reconstruct shape
        Shape shape;
        if (isLine) {
            shape = DrawLine.createPolyline(xCoordinates, yCoordinates, xCoordinates.length);
        } else {
            shape = new Polygon(xCoordinates, yCoordinates, xCoordinates.length);
        }

        return shape;
    }
}
