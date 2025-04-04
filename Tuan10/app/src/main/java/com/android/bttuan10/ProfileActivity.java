package com.android.bttuan10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.bttuan10.api.ServiceAPI;
import com.android.bttuan10.model.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ProfileActivity extends AppCompatActivity {
    private ImageView imgProfile;
    private TextView tvId, tvUsername, tvEmail, tvFullName, tvGender;
    private ActivityResultLauncher<Intent> mActivityResultLauncher;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgProfile = findViewById(R.id.imgProfile);
        tvId = findViewById(R.id.tvId);
        tvUsername = findViewById(R.id.tvUsername);
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        tvGender = findViewById(R.id.tvGender);

        tvId.setText("12345");
        tvUsername.setText("trung1");
        tvFullName.setText("Nguyễn Hữu Trung");
        tvEmail.setText("trung2@gmail.com");
        tvGender.setText("Male");
        String profileImg = null;

        hanlder();


        if (profileImg != null && !profileImg.isEmpty()) {
            Glide.with(this)
                    .load(profileImg)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(imgProfile);
        } else {
            imgProfile.setImageResource(R.drawable.ic_launcher_background);
        }

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, UploadImgActivity.class);
                String currentUserId = tvId.getText().toString();
                if (currentUserId.isEmpty() || currentUserId.equals("USER")) {
                    Toast.makeText(ProfileActivity.this, "Cannot edit profile: User ID not loaded correctly.", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra(UploadImgActivity.USER_ID, currentUserId);

                mActivityResultLauncher.launch(intent);
            }
        });
    }

    private void hanlder(){
        mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {@Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.hasExtra(UploadImgActivity.IMAGE_URL)) {
                            String newImg = data.getStringExtra(UploadImgActivity.IMAGE_URL);

                            if (newImg != null && !newImg.isEmpty()) {
                                Glide.with(ProfileActivity.this)
                                        .load(newImg)
                                        .placeholder(R.drawable.ic_launcher_background)
                                        .error(R.drawable.ic_launcher_background)
                                        .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .into(imgProfile);
                            }
                        } else {
                            Log.d("KHOA", "Không có ảnh mới");
                        }
                    } else {
                        Log.d("KHOA", "Mã kq: " + result.getResultCode());
                    }
                }
                });
    }

}