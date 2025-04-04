package com.android.bttuan10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.bttuan10.api.ServiceAPI;
import com.android.bttuan10.model.User;
import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity {
    private ImageView imgProfile;
    private TextView tvId, tvUsername, tvEmail, tvGender;
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

        ImageView imgProfile = findViewById(R.id.imgProfile);
        TextView tvId = findViewById(R.id.tvId);
        TextView tvUsername = findViewById(R.id.tvUsername);
        TextView tvFullName = findViewById(R.id.tvFullName);
        TextView tvEmail = findViewById(R.id.tvEmail);

        User user = getIntent().getParcelableExtra("user");
        if (user != null) {
            tvId.setText(user.getId());
            tvUsername.setText(user.getUsername());
            tvFullName.setText(user.getName());
            tvEmail.setText(user.getEmail());

            Glide.with(this)
                    .load(user.getAvatar())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imgProfile);
        }

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, UploadImgActivity.class);
                startActivity(intent);
            }
        });
    }

}