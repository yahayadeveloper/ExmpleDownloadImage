package com.exmpledownloadimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Essa on 10/19/15.
 */
public class Item_wallpaper extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_wallpaper);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
        imageView.setImageBitmap(bmp);
    }
}
