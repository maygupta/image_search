package com.groupon.maygupta.imagesearch.clients;

import com.groupon.maygupta.imagesearch.models.FilterParams;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by maygupta on 10/31/15.
 */
public class GoogleApiClient {

    private AsyncHttpClient client;
    public int page;
    public int IMAGES_PER_PAGE = 8;
    public String query;
    public FilterParams filterParams;
    private static final String GOOGLE_IMAGEs_GET_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";

    public GoogleApiClient() {
        client = new AsyncHttpClient();
        page = 1;
    }

    public void getImages(JsonHttpResponseHandler handler) {
        try {
            String url = GOOGLE_IMAGEs_GET_URL + URLEncoder.encode(query, "utf-8");
            url += "&rsz=" + String.valueOf(IMAGES_PER_PAGE);
            url += "&start=" + String.valueOf((page-1) * IMAGES_PER_PAGE);

            if ( filterParams.size != null) {
                url += "&imgsz=" + filterParams.size;
            }

            if ( filterParams.color != null) {
                url += "&imgcolor=" + filterParams.color;
            }

            if ( filterParams.type != null) {
                url += "&as_filetype=" + filterParams.type;
            }

            if ( filterParams.domain != null) {
                url += "&as_sitesearch=" + filterParams.domain;
            }

            client.get(url, handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
