package com.example.sign_in_register;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageLoader extends AsyncTask<File, Void, Bitmap> {

    int imageW;
    int imageH;

    public ImageLoader(int imageW, int imageH) {
        this.imageW = imageW;
        this.imageH = imageH;
    }


    @Override
    protected Bitmap doInBackground(File... files) {
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File image = new File(sdCardDirectory, "emergencyImage.png");
        if(this.imageW > 0 && this.imageH > 0) {
            try {
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(new FileInputStream(image),null,o);

                // The new size we want to scale to
                final int REQUIRED_WIDTH=this.imageW;
                final int REQUIRED_HEIGHT=this.imageH;
                // Find the correct scale value. It should be a power of 2.
                int scale = 1;
                while(o.outWidth/scale/2 >= REQUIRED_WIDTH && o.outHeight/scale/2 >= REQUIRED_HEIGHT)
                    scale *= 2;
                //Decode with inSampleSize
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                return BitmapFactory.decodeStream(new FileInputStream(image), null, o2);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
