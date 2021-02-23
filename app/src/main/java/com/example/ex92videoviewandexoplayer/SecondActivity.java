package com.example.ex92videoviewandexoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;

public class SecondActivity extends AppCompatActivity {

    //ExoPlayer Library 추가 [project structure메뉴에서 검색 안됨]

    PlayerView pv; //비디오를 보여주는 뷰
    SimpleExoPlayer player; //실제 비디오를 플레이하는 객체참조변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        pv = findViewById(R.id.pv);

    }
    //액티비티가 화면에 보여질 때 자동으로 호출되는 메소드

    @Override
    protected void onStart() {
        super.onStart();
        //실제 비디오를 실행하는 객체 생성
        player = new SimpleExoPlayer.Builder(this).build();
        //플레이어뷰에 플레이어 객체 설정
        pv.setPlayer(player);

        //**비디오 1개를 로딩하는 작업 수행
//        Uri uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
//
//        //Media Item객체로 만들기
//        MediaItem mediaItem = MediaItem.fromUri(uri);
//        player.setMediaItem(mediaItem);
//        player.prepare();
//        player.play();

        //**비디오 여러개를 차례로 재생

        Uri firstUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
        Uri secondUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4");

        MediaItem item1 = MediaItem.fromUri(firstUri);
        MediaItem item2 = MediaItem.fromUri(secondUri);
        player.addMediaItem(item1);
        player.addMediaItem(item2);
        player.prepare();
        player.play();

        //플레이어컨트롤뷰에 플레이어 연동하기
//        PlayerControlView pcv = findViewById(R.id.pcv);
//        pcv.setPlayer(player);
    }
}