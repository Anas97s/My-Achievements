package com.example.money_split_f1.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.money_split_f1.User.UserData;
import com.example.money_split_f1.databinding.ActivityChangePasswordBinding;
import com.example.money_split_f1.databinding.ActivityChangeProfilePictureBinding;

import java.net.HttpURLConnection;


public class ChangeProfilePictureActivity extends AppCompatActivity {

    ActivityChangeProfilePictureBinding binding;
    ActivityResultLauncher<String> mGetImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeProfilePictureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mGetImage = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                binding.previewImage.setImageURI(result);
            }
        });
        binding.openGallery.setOnClickListener(v -> {
            mGetImage.launch("image/*");
        });

        binding.savePicture.setOnClickListener(v -> {
            BitmapDrawable bitmapDrawable = ((BitmapDrawable) binding.previewImage.getDrawable());
            Bitmap bitmap = bitmapDrawable .getBitmap();
            UserData.getInstance().setProfilePicture(bitmap);
            finish();
        });
    }

    public void changeProfilePictureRequest (){

    }

}
