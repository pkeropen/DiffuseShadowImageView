package com.vita.shadow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.blankj.utilcode.util.ImageUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DiffuseShadowImageView diffuseShadowImageView = findViewById(R.id.iv_image);
        DiffuseShadowImageView diffuseShadowImageView2 = findViewById(R.id.iv_image2);
        diffuseShadowImageView.loadImage(ImageUtils.getBitmap(R.drawable.img));
        diffuseShadowImageView2.loadImage(ImageUtils.getBitmap(R.drawable.img2));
    }
}
