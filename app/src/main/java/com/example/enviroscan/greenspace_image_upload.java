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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.enviroscan.api.Interface;
import com.example.enviroscan.api.RetroClient;
import com.example.enviroscan.databinding.ActivityGreenspaceImageUploadBinding;
import com.example.enviroscan.serverresponse.GreenSpace;
import com.example.enviroscan.serverresponse.comparewithexisting;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class greenspace_image_upload extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private ActivityGreenspaceImageUploadBinding binding;
    private Button processImage;
    private TextView resultTextView;
    private Uri imageUri;
    private Interface greenimageUploadService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ActivityGreenspaceImageUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imageView = binding.imagepicker;
        processImage = binding.btnProcessImage; // Make sure you have a TextView in your XML to display the result

        // Initialize Retrofit service
        greenimageUploadService = RetroClient.getRetrofit().create(Interface.class);

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
            imageView.setImageURI(imageUri);
            binding.loader.setVisibility(View.VISIBLE);
            String fileName = getFileName(imageUri);
            binding.loader.setText("  "+fileName);
        }
    }
    private void uploadImage() {
        binding.loadingAnimation.setVisibility(View.VISIBLE);
        binding.btnProcessImage.setClickable(false);
        binding.darkOverlay.setVisibility(View.VISIBLE);
        binding.imagepicker.setClickable(false);
        if (imageUri == null) {
            binding.loadingAnimation.setVisibility(View.GONE);
            binding.btnProcessImage.setClickable(true);
            binding.darkOverlay.setVisibility(View.GONE);
            binding.imagepicker.setClickable(true);
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(FileUtils.getPath(this, imageUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Call<GreenSpace> call = greenimageUploadService.greenuploadImage(body);
        call.enqueue(new Callback<GreenSpace>() {
            @Override
            public void onResponse(Call<GreenSpace> call, Response<GreenSpace> response) {
                if(response.isSuccessful()){
                    GreenSpace GreenSpace=response.body();

                    Intent intent=new Intent(greenspace_image_upload.this,greenspaceresult.class);
                    intent.putExtra("greenpercentage",GreenSpace.getGreenpercentage());
                    intent.putExtra("landspace",GreenSpace.getLandpercentage());
                    intent.putExtra("bot",GreenSpace.getBot());
                    intent.putExtra("filename",GreenSpace.getFilename());
                    intent.putExtra("recommentation",GreenSpace.getRecommentation());
                    startActivity(intent);
                    binding.loadingAnimation.setVisibility(View.GONE);
                    binding.btnProcessImage.setClickable(true);
                    binding.darkOverlay.setVisibility(View.GONE);
                    binding.imagepicker.setClickable(true);
                }

            }

            @Override
            public void onFailure(Call<GreenSpace> call, Throwable throwable) {
                binding.loadingAnimation.setVisibility(View.GONE);
                binding.btnProcessImage.setClickable(true);
                binding.darkOverlay.setVisibility(View.GONE);
                binding.imagepicker.setClickable(true);
                Toast.makeText(greenspace_image_upload.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
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