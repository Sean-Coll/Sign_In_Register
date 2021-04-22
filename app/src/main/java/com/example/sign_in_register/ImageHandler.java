/* This class provides all the methods to handle a image the user inserts and allows them to change
 * it. The constructor takes the imageView where the image will be displayed and a name for the
 * image to be saved under.
 * Author: Seán Coll
 * Date Created: 16/4/21
 * Last Edited: 16/4/21
 */

package com.example.sign_in_register;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ImageHandler {

    ImageView view;
    String fileName;

    public ImageHandler(ImageView view, String fileName) {
        this.view = view;
        this.fileName = fileName;
    }

    public boolean saveImage() {
        // Convert image to drawable
        BitmapDrawable drawable = (BitmapDrawable)view.getDrawable();
        // Convert drawable to bitmap
        Bitmap bitmap = drawable.getBitmap();
        // Get directory for SD card
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        // Create file to store image
        File image = new File(sdCardDirectory, fileName);
        // Boolean to react to result
        boolean success = false;
        // Encode image as JPG
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public void loadImage() {
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File image = new File(sdCardDirectory, fileName);
        ImageLoader loader = new ImageLoader(view.getLayoutParams().width,
                view.getLayoutParams().height);
        Bitmap bitmap = null;
        try {
            bitmap = loader.execute(image).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        view.setImageBitmap(bitmap);
    }
}
