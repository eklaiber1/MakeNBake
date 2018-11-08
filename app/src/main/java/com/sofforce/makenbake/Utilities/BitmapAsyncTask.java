package com.sofforce.makenbake.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.widget.ImageView;

import com.sofforce.makenbake.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class BitmapAsyncTask extends AsyncTask<File, File, File> {



    private String videoPath;
    private final ThreadLocal<ImageView> imageView = new ThreadLocal<>();

    public synchronized static void retrieveVideoFrameFromVideo(String videoPath, ImageView imageView) {
        BitmapAsyncTask asyncTask = new BitmapAsyncTask();
        asyncTask.videoPath = videoPath;
        asyncTask.imageView.set(imageView);
        asyncTask.execute();
    }


    @Override
    protected File doInBackground(File... files) {
        MediaMetadataRetriever mediaMetadataRetriever = null;
        Bitmap bitmap;
        File file = null;

        try {
            // for ImageCache Key on basis of url
            if (!videoPath.equals(ConstantsForApp.NOT_AVAILABLE)) {
                byte[] byteArray = videoPath.getBytes("UTF-8");
                String base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                String directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "MakeNBake";
                File directory = new File(directoryPath);
                if (!directory.isDirectory())
                    directory.mkdirs();

                file = new File(directoryPath + File.separator + base64 + ".jpeg");
                if (!file.exists()) {
                    file.createNewFile();

                    mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
                    bitmap = mediaMetadataRetriever.getFrameAtTime();
                    scaleDown(bitmap, file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return file;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (file != null) {
            Picasso.get().load(file).placeholder(R.drawable.videocamera01).into(imageView.get());
        }
    }


    public static void scaleDown(Bitmap realImage, File file) {
        int maxSize = 400;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        int width = realImage.getWidth();
        int height = realImage.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        realImage = Bitmap.createScaledBitmap(realImage, width, height, true);

        try {
            FileOutputStream fOut = new FileOutputStream(file);
            realImage.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!realImage.isRecycled())
            realImage.recycle();
    }
}
