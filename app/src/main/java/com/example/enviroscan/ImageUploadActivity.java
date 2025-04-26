package com.example.enviroscan;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.enviroscan.api.Interface;
import com.example.enviroscan.api.RetroClient;
import com.example.enviroscan.databinding.ActivityImageUploadBinding;
import com.example.enviroscan.serverresponse.TreeCounting;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploadActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private ActivityImageUploadBinding binding;
    private Button processImage;
    private TextView resultTextView;
    private Uri imageUri;
    private Interface imageUploadService;
    TextView checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityImageUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageView = binding.imagepicker;
        processImage = binding.btnProcessImage; // Make sure you have a TextView in your XML to display the result
        checkbox=binding.loader;
        // Initialize Retrofit service
        imageUploadService = RetroClient.getRetrofit().create(Interface.class);

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });
        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        processImage.setOnClickListener(v -> uploadImage());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            checkbox.setVisibility(View.VISIBLE);
            String fileName = getFileName(imageUri);
            binding.loader.setText("  "+fileName);
        }
    }

    private void uploadImage() {
        binding.loadingAnimation.setVisibility(View.VISIBLE);
        binding.btnProcessImage.setClickable(false);
        binding.darkOverlay.setVisibility(View.VISIBLE);
        binding.latitude.setClickable(false);
        binding.latitude.setFocusable(false);
        binding.longitude.setFocusable(false);
        binding.longitude.setClickable(false);
        binding.imagepicker.setClickable(false);
        if (imageUri == null) {
            binding.loadingAnimation.setVisibility(View.GONE);
            binding.btnProcessImage.setClickable(true);
            binding.darkOverlay.setVisibility(View.GONE);
            binding.latitude.setClickable(true);
            binding.longitude.setClickable(true);
            binding.imagepicker.setClickable(true);
            binding.latitude.setFocusable(true);
            binding.longitude.setFocusable(true);
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(FileUtils.getPath(this, imageUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody latitude=RequestBody.create(MediaType.parse("text/plain"), ""+binding.latitude.getText().toString().trim());
        RequestBody longitude=RequestBody.create(MediaType.parse("text/plain"), ""+binding.longitude.getText().toString().trim());
        Call<TreeCounting> call = imageUploadService.uploadImage(body,latitude,longitude);
        call.enqueue(new Callback<TreeCounting>() {
            @Override
            public void onResponse(Call<TreeCounting> call, Response<TreeCounting> response) {
                if (response.isSuccessful()) {
                    TreeCounting treeCounting = response.body();
//                    String resultText = "Tree Count: " + treeCounting.getCount() + "\nFilename: " + treeCounting.getFilename();
//                    resultTextView.setText(resultText);
//                    Toast.makeText(ImageUploadActivity.this, resultText, Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(ImageUploadActivity.this, treecountingresult.class));

                    Intent intent = new Intent(ImageUploadActivity.this, treecountingresult.class);
                    intent.putExtra("message_key",""+treeCounting.getCount());
                    intent.putExtra("filename",""+treeCounting.getFilename());
                    startActivity(intent);
                    binding.loadingAnimation.setVisibility(View.GONE);
                    binding.btnProcessImage.setClickable(true);
                    binding.darkOverlay.setVisibility(View.GONE);
                    binding.latitude.setClickable(true);
                    binding.longitude.setClickable(true);
                    binding.imagepicker.setClickable(true);
                    binding.latitude.setFocusable(true);
                    binding.longitude.setFocusable(true);

//                    Log.d("ImageUploadActivity", "Upload successful: " + resultText);
                } else {
                    Log.e("ImageUploadActivity", "Upload failed: " + response.message());
                    Toast.makeText(ImageUploadActivity.this, "Failed to get response", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TreeCounting> call, Throwable t) {
                binding.loadingAnimation.setVisibility(View.GONE);
                binding.btnProcessImage.setClickable(true);
                binding.darkOverlay.setVisibility(View.GONE);
                binding.latitude.setClickable(true);
                binding.longitude.setClickable(true);
                binding.imagepicker.setClickable(true);
                binding.latitude.setFocusable(true);
                binding.longitude.setFocusable(true);
                Log.e("ImageUploadActivity", "Upload error: " + t.getMessage());
                Toast.makeText(ImageUploadActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

}
