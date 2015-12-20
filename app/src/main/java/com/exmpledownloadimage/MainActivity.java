package com.exmpledownloadimage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {
    ArrayList<Bitmap> images = new ArrayList();
    MyBaseAdapter myBaseAdapter;
    GridView listView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (GridView)findViewById(R.id.list_item);

        new MyDownloadImageUrl().execute("http://www.androidhive.info/wp-content/themes/androidhive_3.0/img/logo.png",
                "https://www.google.ps/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"
                , "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQUk9UScBjhTOPrpZr700ncPKB4wrbzhFcu9CT0msyz2gG5Tlz4",
                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTQxfnE7Ir5PppkRyWIiz4j34p_HPGDNpdjxWkThVyWVNTJ5X6I9g",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZrFMG3zlfCXC9usMgkLy0YjwXG6HSSbnTQr3GzuHNbg0jpfNzRQ"
        ,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSC-cJDn2t0X64fOxXPYoKOqVlhhasuBh3igZZ0WuceJ4DMdC1b",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTVgkQC1wuk3nYTdVqinFff3ZnxpOZlB_niQ1CvwFAbIpXSwpvYPw",
                "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSniRsytSOVRwY1QguNdDac3UEfRZX3P3SFh5BKmYHSzRVrdhOz"
        ,"https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRBY1PCUABJAswwxeygLWuQ6y3q9fs8QlXXvOdVf8VQM4SFdeAW");

        myBaseAdapter = new MyBaseAdapter(this,images);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bitmap image = images.get(position);
                Bundle extras = new Bundle();
                Intent intent = new Intent(MainActivity.this, Item_wallpaper.class);
                extras.putParcelable("imagebitmap", image);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }
    private Bitmap DownloadImage(String url)
    {
        Bitmap bitmap = null;
        InputStream inputStram = null;
        inputStram = Downloadimageinurl(url);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        int width =  options.outWidth;
        int height = options.outHeight;
        int counter = 0;
        while (150 < width|| 150 < height){
            width/=2;
            height/=2;
            counter++;
        }
        options.inSampleSize = counter;
        options.inJustDecodeBounds = false;

        bitmap = BitmapFactory.decodeStream(inputStram,new Rect(2,2,2,2),options);
        Log.e("getWidth",bitmap.getWidth()+"");
        return bitmap;
    }
    class MyDownloadImageUrl extends AsyncTask<String, Bitmap, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("loading");
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... urls) {

            Bitmap image ;
            for (int i = 0; i < urls.length; i++) {

                image = DownloadImage(urls[i]);

                publishProgress(image);

            }
            return urls.length;
        }
        @Override
        protected void onProgressUpdate(Bitmap... values) {
            images.add(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (progressDialog != null)
            {
                progressDialog.dismiss();
            }

            listView.setAdapter(myBaseAdapter);

            Toast.makeText(getApplicationContext(), "Number of download image is " + integer, Toast.LENGTH_SHORT).show();
        }
    }
        private InputStream Downloadimageinurl(String urls) {
            InputStream inputStream = null;
            URL url = null;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(urls);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(30000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == httpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }
    }

