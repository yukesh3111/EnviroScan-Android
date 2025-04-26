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
import com.example.enviroscan.databinding.ActivitySpeciesImageUploadBinding;
import com.example.enviroscan.serverresponse.GreenSpace;
import com.example.enviroscan.serverresponse.speciesidentification;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class species_image_upload extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private ActivitySpeciesImageUploadBinding binding;
    private Button processImage;
    private TextView resultTextView;
    private Uri imageUri;
    private Interface greenimageUploadService;
    TextView checkbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_species_image_upload);
        binding=ActivitySpeciesImageUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkbox=binding.loader;
        imageView = binding.imagepicker;
        processImage = binding.btnProcessImage; // Make sure you have a TextView in your XML to display the result
        greenimageUploadService = RetroClient.getRetrofit().create(Interface.class);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        processImage.setOnClickListener(v -> uploadImage());
        binding.backbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        Call<speciesidentification> call = greenimageUploadService.speciesuploadImage(body);
        call.enqueue(new Callback<speciesidentification>() {
            @Override
            public void onResponse(Call<speciesidentification> call, Response<speciesidentification> response) {
                if(response.isSuccessful()){
                    speciesidentification speciesidentification=response.body();

                    if(speciesidentification.getResponse().equals("1")){
                        Intent intent=new Intent(species_image_upload.this,speciesidentificationresult.class);
                        intent.putExtra("message_key1",""+speciesidentification.getSpecies());
                        intent.putExtra("message_key2",""+speciesidentification.getDescription());
                        intent.putExtra("ecological",""+speciesidentification.getEcological());
                        intent.putExtra("fact",""+speciesidentification.getFact());
                        intent.putExtra("physical",""+speciesidentification.getPhysical());
                        intent.putExtra("filename",speciesidentification.getFilename());
                        startActivity(intent);
                        binding.loadingAnimation.setVisibility(View.GONE);
                        binding.btnProcessImage.setClickable(true);
                        binding.darkOverlay.setVisibility(View.GONE);
                        binding.imagepicker.setClickable(true);

                    } else if (speciesidentification.getResponse().equals("2")) {

                        Intent intent=new Intent(species_image_upload.this, speciesclaritychallenge.class);
                        intent.putExtra("species1",speciesidentification.getSpecies1());
                        intent.putExtra("species2",speciesidentification.getSpecies2());
                        intent.putExtra("species3",speciesidentification.getSpecies3());
                        intent.putExtra("confidence1",speciesidentification.getConfidence1());
                        intent.putExtra("confidence2",speciesidentification.getConfidence2());
                        intent.putExtra("confidence3",speciesidentification.getConfidence3());
                        intent.putExtra("filename",speciesidentification.getFilename());
                        startActivity(intent );
                        binding.loadingAnimation.setVisibility(View.GONE);
                        binding.btnProcessImage.setClickable(true);
                        binding.darkOverlay.setVisibility(View.GONE);
                        binding.imagepicker.setClickable(true);
                    }

                }else{
                    Log.e("ImageUploadActivity", "Upload failed: " + response.message());
                    Toast.makeText(species_image_upload.this, "Failed to get response", Toast.LENGTH_SHORT).show();
                    binding.loadingAnimation.setVisibility(View.GONE);
                    binding.btnProcessImage.setClickable(true);
                    binding.darkOverlay.setVisibility(View.GONE);
                    binding.imagepicker.setClickable(true);
                }
            }

            @Override
            public void onFailure(Call<speciesidentification> call, Throwable throwable) {
                binding.loadingAnimation.setVisibility(View.GONE);
                binding.btnProcessImage.setClickable(true);
                binding.darkOverlay.setVisibility(View.GONE);
                binding.imagepicker.setClickable(true);
                Toast.makeText(species_image_upload.this,"Upload Failure Please Check Internet Connetion",Toast.LENGTH_SHORT).show();
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