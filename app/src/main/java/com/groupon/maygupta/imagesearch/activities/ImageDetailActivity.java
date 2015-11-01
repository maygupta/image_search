package com.groupon.maygupta.imagesearch.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.groupon.maygupta.imagesearch.R;
import com.groupon.maygupta.imagesearch.models.Image;
import com.squareup.picasso.Picasso;

public class ImageDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        getSupportActionBar().hide();
        // get serializable image from Intent
        Image image = (Image) getIntent().getSerializableExtra("image");

        ImageView ivFullImage = (ImageView) findViewById(R.id.ivFullImage);
        Picasso.with(this).load(Uri.parse(image.fullUrl)).into(ivFullImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
