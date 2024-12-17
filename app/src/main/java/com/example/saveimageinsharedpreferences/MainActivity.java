package com.example.saveimageinsharedpreferences;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView);
        ImageView saveImage = findViewById(R.id.saveImage);
        Button saveBtn = findViewById(R.id.saveBtn);

        preferences = getSharedPreferences("shaon", MODE_PRIVATE);
        editor = preferences.edit();

        String encodedSaveImage = preferences.getString("image", "");
        byte[] decodedString = Base64.decode(encodedSaveImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        if (decodedByte != null) {
            saveImage.setImageBitmap(decodedByte);
        }


        saveBtn.setOnClickListener(v -> {

            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] byteArray = outputStream.toByteArray();
            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

            editor.putString("image", encodedImage);
            editor.apply();

            Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
        });

    }
}