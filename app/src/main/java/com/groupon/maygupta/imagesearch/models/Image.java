package com.groupon.maygupta.imagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by maygupta on 10/31/15.
 */
public class Image implements Serializable {
    public String fullUrl;
    public String thumbUrl;
    public String title;

    public Image(JSONObject json) {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Image> fromJSONArray(JSONArray array) {
        ArrayList<Image> results = new ArrayList<Image>();

        for(int i = 0 ; i < array.length(); i++) {
            try {
                results.add(new Image(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
