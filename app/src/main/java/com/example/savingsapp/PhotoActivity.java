package com.example.savingsapp; // Package declaration

import android.annotation.SuppressLint; // Import for SuppressLint annotation
import android.content.Intent; // Import for Intent class
import android.content.pm.PackageManager; // Import for PackageManager class
import android.graphics.Bitmap; // Import for Bitmap class
import android.net.Uri; // Import for Uri class
import android.os.Bundle; // Import for Bundle class
import android.os.Environment; // Import for Environment class
import android.provider.MediaStore; // Import for MediaStore class
import android.view.View; // Import for View class
import android.widget.Button; // Import for Button widget

import androidx.core.content.FileProvider; // Import for FileProvider class

import java.io.File; // Import for File class
import java.io.FileOutputStream; // Import for FileOutputStream class
import java.io.IOException; // Import for IOException class
import java.nio.file.Files; // Import for Files class
import java.text.SimpleDateFormat; // Import for SimpleDateFormat class
import java.util.Date; // Import for Date class
import java.util.UUID; // Import for UUID class

import de.hdodenhof.circleimageview.CircleImageView; // Import for CircleImageView class

public class PhotoActivity extends BaseActivity implements View.OnClickListener { // Class declaration

    String currentPhotoPath; // Variable to store the current photo path
    String dbPhotoPath; // Variable to store the database photo path
    int REQUEST_IMAGE_CAPTURE = 1; // Request code for image capture

    CircleImageView mImageView; // ImageView to display the photo
    Button captureButton; // Button to capture the photo
    Button saveButton; // Button to save the photo

    @Override
    protected void onCreate(Bundle savedInstanceState) { // onCreate method, called when the activity is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo); // Set the layout for the activity

        initViews(); // Initialize views
        initAppDb(); // Initialize the database
    }

    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException { // Method to create an image file
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); // Generate a timestamp
        String imageFileName = "JPEG_" + timeStamp + "_"; // Create a unique file name
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES); // Get the directory for storing pictures
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath(); // Store the current photo path
        return image; // Return the created image file
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void dispatchTakePictureIntent() { // Method to dispatch the take picture intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // Create an intent to capture an image
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(); // Create an image file
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile); // Get the URI for the file
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE); // Start the activity to capture the image
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // Method to handle the result of an activity
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) { // Check if the result is from image capture and is successful
            Bundle extras = data.getExtras(); // Get the extras from the intent
            Bitmap imageBitmap = (Bitmap) extras.get("data"); // Get the captured image
            imageBitmap = cropAndScale(imageBitmap, 300); // Crop and scale the image
            mImageView.setImageBitmap(imageBitmap); // Set the image to the ImageView
            savePhoto(imageBitmap); // Save the photo
        }
    }

    void savePhoto(Bitmap bitmap) { // Method to save the photo
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "ginafi_photos"); // Get the directory for storing photos
        if (!dir.exists()) {
            dir.mkdir(); // Create the directory if it doesn't exist
        }

        dbPhotoPath = dir.getAbsolutePath() + "/profile_photo.png"; // Set the database photo path
        File file = new File(dir, "profile_photo-" + UUID.randomUUID() + ".png"); // Create a new file for the photo

        try {
            getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    MediaStore.Images.Media.DATA + "=?", new String[]{file.getAbsolutePath()}); // Delete any existing file with the same path
            FileOutputStream out = new FileOutputStream(file); // Create a FileOutputStream for the file
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress the bitmap to the file
            out.flush(); // Flush the output stream
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName()); // Insert the image to the MediaStore
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if an error occurs
        }
    }

    private void initViews() { // Method to initialize views
        mImageView = findViewById(R.id.photo); // Initialize the ImageView

        captureButton = findViewById(R.id.capture_btn); // Initialize the capture button
        captureButton.setOnClickListener(this); // Set click listener for the capture button

        saveButton = findViewById(R.id.save_btn); // Initialize the save button
        saveButton.setOnClickListener(this); // Set click listener for the save button
    }

    @Override
    public void onClick(View v) { // Method to handle click events
        if (v.getId() == R.id.capture_btn) { // Check if the capture button is clicked
            if (!isPermissionGranted("android.permission.CAMERA")) { // Check if camera permission is granted
                requestPermissions(new String[]{
                        "android.permission.CAMERA",
                        "android.permission.WRITE_EXTERNAL_STORAGE",
                        "android.permission.READ_EXTERNAL_STORAGE"
                }, 1); // Request necessary permissions if not granted
            } else {
                dispatchTakePictureIntent(); // Dispatch the take picture intent if permissions are granted
            }
        } else if (v.getId() == R.id.save_btn) { // Check if the save button is clicked
            if (dbPhotoPath == null) { // Check if a photo is taken
                showToast("Please take a photo first"); // Show a toast message if no photo is taken
                return; // Return early
            }
            // Save the image to the database
            Intent intent = getIntent(); // Get the intent that started this activity
            long userId = intent.getLongExtra("userId", 0); // Get the user ID from the intent
            runInBackground(() -> {
                appDatabase.userDao().updateUser((int) userId, dbPhotoPath); // Update the user with the photo path in the database
                runOnUiThread(() -> {
                    showToast("Photo saved successfully"); // Show a toast message on the UI thread
                    gotoActivity(this, MainActivity.class); // Go to the MainActivity
                });
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) { // Method to handle the result of permission requests
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) { // Check if the request code matches
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // Check if permissions are granted
                dispatchTakePictureIntent(); // Dispatch the take picture intent if permissions are granted
            }
        }
    }

    public static Bitmap cropAndScale(Bitmap source, int scale) { // Method to crop and scale a bitmap
        int factor = source.getHeight() <= source.getWidth() ? source.getHeight() : source.getWidth(); // Determine the smaller dimension
        int longer = source.getHeight() >= source.getWidth() ? source.getHeight() : source.getWidth(); // Determine the larger dimension
        int x = source.getHeight() >= source.getWidth() ? 0 : (longer - factor) / 2; // Calculate the x coordinate for cropping
        int y = source.getHeight() <= source.getWidth() ? 0 : (longer - factor) / 2; // Calculate the y coordinate for cropping
        source = Bitmap.createBitmap(source, x, y, factor, factor); // Crop the bitmap
        source = Bitmap.createScaledBitmap(source, scale, scale, false); // Scale the bitmap
        return source; // Return the cropped and scaled bitmap
    }
}
