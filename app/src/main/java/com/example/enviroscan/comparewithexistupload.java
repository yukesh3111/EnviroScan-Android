package com.example.enviroscan;

//import static com.example.enviroscan.ImageUploadActivity.PICK_IMAGE_REQUEST;

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
import com.example.enviroscan.databinding.ActivityComparewithexistuploadBinding;
import com.example.enviroscan.databinding.ActivityImageUploadBinding;
import com.example.enviroscan.serverresponse.comparewithexisting;
import com.example.enviroscan.serverresponse.speciesidentification;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class comparewithexistupload extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    ActivityComparewithexistuploadBinding binding;
    TextView checkbox;
    private String fileName;
    private Uri imageUri;
    private ImageView imageView;
    private Button processImage;
    private Interface imageUploadService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding=ActivityComparewithexistuploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkbox=binding.loader;
        processImage = binding.btnProcessImage; // Make sure you have a TextView in your XML to display the result
        imageUploadService = RetroClient.getRetrofit().create(Interface.class);

        binding.imagepicker.setOnClickListener(v -> {
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
            binding.imagepicker.setImageURI(imageUri);
            checkbox.setVisibility(View.VISIBLE);
            fileName = getFileName(imageUri);
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
        Call<comparewithexisting> call = imageUploadService.compareuploadImage(body, latitude, longitude);
        call.enqueue(new Callback<comparewithexisting>() {
            @Override
            public void onResponse(Call<comparewithexisting> call, Response<comparewithexisting> response) {
                if(validate()){
                    if(response.isSuccessful() ){
                        if(response.body().isMessage()){
                            comparewithexisting comparewithexisting=response.body();

                            Intent intent=new Intent(comparewithexistupload.this,comparewithexistingresult.class);
                            intent.putExtra("presentcount",""+comparewithexisting.getPresentcount());
                            intent.putExtra("previouscount",""+comparewithexisting.getPreviouscount());
                            intent.putExtra("filename",""+comparewithexisting.getFilename());
                            intent.putExtra("newfilename",""+fileName);

                            startActivity(intent);
                            binding.loadingAnimation.setVisibility(View.GONE);
                            binding.btnProcessImage.setClickable(true);
                            binding.darkOverlay.setVisibility(View.GONE);
                            binding.latitude.setClickable(true);
                            binding.longitude.setClickable(true);
                            binding.imagepicker.setClickable(true);
                            binding.latitude.setFocusable(true);
                            binding.longitude.setFocusable(true);
                        }
                        else{
                            binding.loadingAnimation.setVisibility(View.GONE);
                            binding.btnProcessImage.setClickable(true);
                            binding.darkOverlay.setVisibility(View.GONE);
                            binding.latitude.setClickable(true);
                            binding.longitude.setClickable(true);
                            binding.imagepicker.setClickable(true);
                            binding.latitude.setFocusable(true);
                            binding.longitude.setFocusable(true);
                            Toast.makeText(comparewithexistupload.this, "This area is not added in Tree eumneration Database", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        binding.loadingAnimation.setVisibility(View.GONE);
                        binding.btnProcessImage.setClickable(true);
                        binding.darkOverlay.setVisibility(View.GONE);
                        binding.latitude.setClickable(true);
                        binding.longitude.setClickable(true);
                        binding.imagepicker.setClickable(true);
                        binding.latitude.setFocusable(true);
                        binding.longitude.setFocusable(true);
                        Toast.makeText(comparewithexistupload.this, "Upload Failure Please Check Internet Connetion", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onResponse: "+response.errorBody().toString());
                    }
                }

            }

            @Override
            public void onFailure(Call<comparewithexisting> call, Throwable throwable) {
                binding.loadingAnimation.setVisibility(View.GONE);
                binding.btnProcessImage.setClickable(true);
                binding.darkOverlay.setVisibility(View.GONE);
                binding.latitude.setClickable(true);
                binding.longitude.setClickable(true);
                binding.imagepicker.setClickable(true);
                binding.latitude.setFocusable(true);
                binding.longitude.setFocusable(true);
                Toast.makeText(comparewithexistupload.this,"Upload Failure Please Check Internet Connetion"+throwable.getMessage(),Toast.LENGTH_SHORT).show();
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
    private boolean validate(){
        if(binding.latitude.getText().toString().trim().isEmpty() || binding.longitude.getText().toString().trim().isEmpty()){
            binding.latitude.setError("Address is required");
            binding.latitude.requestFocus();
            return false;
        }
        return true;
    }
}