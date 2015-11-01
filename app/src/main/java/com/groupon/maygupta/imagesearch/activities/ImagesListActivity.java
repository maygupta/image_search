package com.groupon.maygupta.imagesearch.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.groupon.maygupta.imagesearch.R;
import com.groupon.maygupta.imagesearch.adapters.ImagesAdapter;
import com.groupon.maygupta.imagesearch.clients.GoogleApiClient;
import com.groupon.maygupta.imagesearch.fragments.SearchFilterFragment;
import com.groupon.maygupta.imagesearch.models.FilterParams;
import com.groupon.maygupta.imagesearch.models.Image;
import com.groupon.maygupta.imagesearch.utils.EndlessScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ImagesListActivity extends ActionBarActivity {

    private ArrayList<Image> images;
    private GoogleApiClient client;
    private ImagesAdapter adapter;
//    private GridView gvImages;
    private StaggeredGridView gvImages;
    private FilterParams filterParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_list);

        // Create client object for Google API
        client = new GoogleApiClient();

        // Setup views and listener events on the views
        setupViews();

        images = new ArrayList<>();

        // Create adapter for images and bind it with view
        adapter = new ImagesAdapter(this, images);
        gvImages.setAdapter(adapter);

        // Initiate Filter params
        filterParams = new FilterParams();
    }

    public void setupViews() {
//        gvImages = (GridView) findViewById(R.id.gvImages);
        gvImages = (StaggeredGridView) findViewById(R.id.grid_view);

        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch image detail activity
                Intent intent = new Intent(ImagesListActivity.this, ImageDetailActivity.class);
                Image image = images.get(position);
                intent.putExtra("image", image);
                startActivity(intent);
            }
        });

        gvImages.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
                return true;
            }
        });
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        client.page = offset;
        client.getImages(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray resultsJson = null;
                try {
                    resultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    adapter.addAll(Image.fromJSONArray(resultsJson));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_images_list, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                fetchImages(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void fetchImages(String query) {

        if (isNetworkAvailable() == false) {
            Toast.makeText(this, "Network not available!!", Toast.LENGTH_LONG);
            return;
        }

        client.query = query;
        client.filterParams = filterParams;
        client.getImages(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray resultsJson = null;
                try {
                    resultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    adapter.clear(); // clear existing images from the array
                    adapter.addAll(Image.fromJSONArray(resultsJson));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if ( id == R.id.filter_settings) {
            FragmentManager manager = getFragmentManager();
            SearchFilterFragment fragment = new SearchFilterFragment();
            Bundle args = new Bundle();
            args.putString("color", filterParams.color);
            args.putString("size", filterParams.size);
            args.putString("type", filterParams.type);
            args.putString("domain", filterParams.domain);
            fragment.setArguments(args);
            fragment.setDialogResultHandler(new SearchFilterFragment.OnDialogResultHandler() {
                @Override
                public void finish(FilterParams filterParamsFromDialog) {
                    filterParams = filterParamsFromDialog;
                }
            });
            fragment.show(manager, "FilterDialog");
        }

        return super.onOptionsItemSelected(item);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
