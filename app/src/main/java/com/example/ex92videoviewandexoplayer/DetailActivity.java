package com.example.ex92videoviewandexoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv = findViewById(R.id.tv);

        String jsonStr = getIntent().getStringExtra("item");
        //받은 게 json이니까 다시 VideoItem 객체로 변환!
        VideoItem videoItem = new Gson().fromJson(jsonStr,VideoItem.class);
        tv.setText(videoItem.title+"\n"+videoItem.subtitle+"\n"+videoItem.sources);
    }
}