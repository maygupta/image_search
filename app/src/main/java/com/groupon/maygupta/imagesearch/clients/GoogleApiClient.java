package com.groupon.maygupta.imagesearch.clients;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by maygupta on 10/31/15.
 */
public class GoogleApiClient {

    private AsyncHttpClient client;
    private static final String GOOGLE_IMAGEs_GET_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=";

    public GoogleApiClient() {
        client = new AsyncHttpClient();
    }

    public void getImages(final String query, JsonHttpResponseHandler handler) {
        try {
            client.get(GOOGLE_IMAGEs_GET_URL + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
