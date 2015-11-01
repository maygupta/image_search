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

    private static class ViewHolder {
        public ImageView ivImage;
        public TextView tvTitle;
    }

    public ImagesAdapter(Context context, List<Image> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Image image = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_image, parent, false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvImageTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(Html.fromHtml(image.title));
        viewHolder.ivImage.setImageResource(0);
        Picasso.with(getContext()).load(Uri.parse(image.thumbUrl)).into(viewHolder.ivImage);

        return convertView;
    }
}
