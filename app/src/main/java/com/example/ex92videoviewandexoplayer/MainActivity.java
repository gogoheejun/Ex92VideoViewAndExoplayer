package com.example.ex92videoviewandexoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    VideoView vv;

    //Video Uri [용량묹로 별도의 서버에 위치하는 경우가 대부분] - 인터넷퍼미션
    //즉, 비디오파일을 서버에 놓고 이를 읽어들여 실행하도록 코딩......구글에 sample video uri로 검색
    Uri videoUri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"); //url을 uri로

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vv = findViewById(R.id.vv);

        //비디오뷰의 재생,일시정지 등을 할수있는 컨트롤바 붙이기
       vv.setMediaController(new MediaController(this));

        //비디오뷰에 동영상의 uri설정
        vv.setVideoURI(videoUri);

        //동영상 로딩하는데 시간걸리므로 곧바로 실행 못하고 비디오의 로딩준비(prepare)가 완료되었을때 실행하도록
       vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
           @Override
           public void onPrepared(MediaPlayer mp) {
               vv.start();
           }
       });
    }

    //액티비티가 화면에서 보이지 않을 때 비디오를 일시정지 할 필요 있음 why? 비디오는 별도thread로 동영상을 재생하기때문
    //물론 지금은 오레오버전부터 백그라운드 동작 막아주긴 함
    @Override
    protected void onPause() { //액티비티가 화면에 보이지 않을때
        super.onPause();
        if(vv!=null && vv.isPlaying()){
            vv.pause();
        }
    }

    //videoView는 성능이 좋지않고 무거움...그래서 화면에 비디오여러개면 막 느려짐, 컨트롤러위치 지정도 불편함
    //실무에서는 안드로이드 공식 videoview library인 ExoPlayer많이 씀
    public void clickBtn(View view) {
        startActivity(new Intent(this, SecondActivity.class));
        finish();
    }

    public void clickBtn2(View view) {
       startActivity(new Intent(this, ThirdActivity.class));
       finish();
    }

}























