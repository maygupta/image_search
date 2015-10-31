package com.groupon.maygupta.imagesearch.adapters;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.groupon.maygupta.imagesearch.R;
import com.groupon.maygupta.imagesearch.models.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by maygupta on 10/31/15.
 */
public class ImagesAdapter extends ArrayAdapter<Image>{
    public ImagesAdapter(Context context, List<Image> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Image image = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvImageTitle = (TextView) convertView.findViewById(R.id.tvImageTitle);

        tvImageTitle.setText(Html.fromHtml(image.title));
        ivImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(Uri.parse(image.thumbUrl)).into(ivImage);

        return convertView;
    }
}
