package com.francis.gitme;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

public class DeveloperAdapter extends ArrayAdapter<Developer>{

    public DeveloperAdapter(Context context, ArrayList<Developer> developers) {
        super(context, 0, developers);
    }
    // TODO: 09/09/2017 Add more functionality to your app

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.developer_container,
                    parent, false);
        }

        Developer current_developer = getItem(position);

        TextView username_txt = (TextView) convertView.findViewById(R.id.developer_username);
        username_txt.setText(current_developer.getUsername());

        final ImageView developer_image_view = (ImageView) convertView.findViewById(R.id.developer_image);
        Glide.with(getContext())
                .load(current_developer.getImage_url())
                .asBitmap()
                .animate(android.R.anim.fade_in)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(developer_image_view){ // make image circular
                    @Override
                    protected void setResource(Bitmap resource){
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(
                                getContext().getResources(), Bitmap.createScaledBitmap(resource, 200,
                                        200, false));
                        drawable.setCircular(true);
                        developer_image_view.setImageDrawable(drawable);
                    }
                });

        return convertView;
    }
}
