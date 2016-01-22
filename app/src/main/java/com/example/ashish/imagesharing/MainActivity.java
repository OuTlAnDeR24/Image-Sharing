package com.example.ashish.imagesharing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity {

    private static final int CAMERA_PIC_REQUEST = 1;
    private Button mButton, mButtonCheck;
    public ImageView imageView;
    public Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(ImageObject.class);
        Parse.initialize(this, "ZAgFQIcanJ2ZFropucEp9jgEpglDw7zDJuzKqKLD", "nDl6yGlc5xPqrMUllaSXiH87paeLPuwcYOhY2VMF");

        mButton = (Button) findViewById(R.id.myButton);
        mButtonCheck = (Button) findViewById(R.id.myButtonCheck);
        imageView = (ImageView) findViewById(R.id.iv);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ParseObject testObject = new ParseObject("TestObject");
//                testObject.put("foo", "bar");
//                testObject.saveInBackground();


                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.


                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri();
                intent.putExtra( MediaStore.EXTRA_OUTPUT, fileUri );
                startActivityForResult(intent, CAMERA_PIC_REQUEST);


//                // Locate the image in res > drawable-hdpi
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                        R.mipmap.ic_launcher);
//                // Convert it to byte
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                // Compress image to lower quality scale 1 - 100
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] image = stream.toByteArray();
//
//                // Create the ParseFile
//                final ParseFile file = new ParseFile("launcher.png", image);
//                // Upload the image into Parse Cloud
//                file.saveInBackground(new SaveCallback() {
//                    @Override
//                    public void done(ParseException e) {
//                        ImageObject imageObject = new ImageObject();
//                        imageObject.setAuthor("Ashish");
//                        List<ParseFile> parseFiles = new ArrayList<>();
//                        parseFiles.add(file);
//                        imageObject.setPhotoFiles(parseFiles);
//                        imageObject.saveInBackground();
//                    }
//                });

            }
        });

        mButtonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("ImageObject");
                query.getFirstInBackground(new GetCallback<ParseObject>() {

                    public void done(ParseObject object, com.parse.ParseException e) {
                        if (e == null) {
                            ImageObject imageObject = (ImageObject) object;
                            List<ParseFile> parseFiles = imageObject.getPhotoFiles();
                            ParseFile parseFile = parseFiles.get(0);
                            parseFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] bytes, ParseException e) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    imageView.setImageBitmap(bitmap);
                                }
                            });
                        } else {
                            // something went wrong
                        }
                    }
                });
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {

            try {
                Bitmap mealImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
                Bitmap mealImageScaled = Bitmap.createScaledBitmap(mealImage, 200, 200
                        * mealImage.getHeight() / mealImage.getWidth(), false);

                // Override Android default landscape orientation and save portrait
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap rotatedScaledMealImage = Bitmap.createBitmap(mealImageScaled, 0,
                        0, mealImageScaled.getWidth(), mealImageScaled.getHeight(),
                        matrix, true);

//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            rotatedScaledMealImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);

//            byte[] scaledData = bos.toByteArray();
//                imageView.setImageBitmap(mealImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                mealImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();
//
                // Create the ParseFile
                final ParseFile file = new ParseFile("myself.png", image);
                // Upload the image into Parse Cloud
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        ImageObject imageObject = new ImageObject();
                        imageObject.setAuthor("Ashish");
                        List<ParseFile> parseFiles = new ArrayList<>();
                        parseFiles.add(file);
                        imageObject.setPhotoFiles(parseFiles);
                        imageObject.saveInBackground();
                    }
                });
            } catch (FileNotFoundException fe) {
                fe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            //                // Locate the image in res > drawable-hdpi
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                        R.mipmap.ic_launcher);
//                // Convert it to byte


        }
    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(){
        return Uri.fromFile(getOutputMediaFile());
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Image-Sharing");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("Image-Sharing", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }

}
