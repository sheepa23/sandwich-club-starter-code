package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    /**
     * Create a private constructor because no one should ever create a {@link JsonUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name JsonUtils (and an object instance of JsonUtils is not needed).
     */
    private JsonUtils() {
    }

    public static Sandwich parseSandwichJson(String json) {
        try {

            // get JSONObject from JSON file
            JSONObject baseJson = new JSONObject(json);
            // fetch JSONObject named "name"
            JSONObject name = baseJson.getJSONObject("name");
            // get sandwich mainName
            String mainName = name.getString("mainName");
            // fetch JSONArray named alsoKnownAs within name object
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            // looping through All alsoKnownAs

            List<String> alsoKnownAsStrings = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAsStrings.add(alsoKnownAsArray.getString(i));
            }
            // get sandwich place of Origin
            String placeOfOrigin = baseJson.getString("placeOfOrigin");
            // get sandwich description
            String description = baseJson.getString("description");
            // get sandwich image
            String image = baseJson.getString("image");
            // fetch JSONArray named ingredients within name object
            JSONArray ingredientsArray = baseJson.getJSONArray("ingredients");
            // looping through All ingredients
            List<String> ingredientsStrings = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredientsStrings.add(ingredientsArray.getString(i));
            }

            //return new sandwich from json data. This is done before catch b/c after catch you can't reference variables.
            return new Sandwich(mainName, alsoKnownAsStrings, placeOfOrigin, description, image, ingredientsStrings);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Return null if sandwich could not be parsed.
        return null;
    }
}