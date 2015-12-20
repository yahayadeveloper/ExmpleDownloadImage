package com.exmpledownloadimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class MyBaseAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ImageView imageView;
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    Context context;
    public MyBaseAdapter(Context context, ArrayList<Bitmap> images) {
        this.images = images;
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = inflater.inflate(R.layout.item, null);
        imageView = (ImageView) v.findViewById(R.id.imageView2);
            imageView.setImageBitmap(images.get(position));
        return v;
    }
}