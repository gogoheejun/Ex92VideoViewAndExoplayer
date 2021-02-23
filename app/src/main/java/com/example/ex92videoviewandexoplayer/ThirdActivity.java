package com.example.ex92videoviewandexoplayer;

import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ThirdActivity extends AppCompatActivity {

    ArrayList<VideoItem> items = new ArrayList<>();
    RecyclerView recyclerView;
    VideoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        recyclerView = findViewById(R.id.recycler);
        adapter = new VideoListAdapter(this, items);
        recyclerView.setAdapter(adapter);

        //서버에 있는 json문서를 읽어와서 파싱(분석)하여 리스트로 보여주기
        loadData();
    }

    private void loadData() {
        //Retrofit을 이용하여 서버에 있는 json문서 읽어와서 파싱(우선 json을 그냥 글씨로 읽어와서 직접 파싱해보기)
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://alexang.dothome.co.kr/"); //서버 기본url
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();
        //원래 위 4줄 귀찮아서 RetrofitHelper만들었었어 근데 걍 함 여기선
        RetrofitService retrofitService = retrofit.create(RetrofitService.class); //RetrofitService.class인터페이스의 기능을 다 구현하는 마법~
        Call<String> call = retrofitService.getVideoDatas();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String jsonStr = response.body();
                Log.i("tag", jsonStr);

                //이제 이 jsonStr을 VideoItme으로 분석하여 리스트뷰에 추가하기
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);//json을 자바객체로 만들어줌
                    JSONArray categories = jsonObject.getJSONArray("categories");  //가져오려는 value가 string이면 겟스트링이겠지. 여기선 []가 벨류안에 있으니까 겟제이슨어레이
                    JSONObject object = categories.getJSONObject(0);
                    JSONArray videoarray = object.getJSONArray("videos");
                    for(int i=0; i<videoarray.length(); i++){
                        JSONObject video = videoarray.getJSONObject(i);
                        String title = video.getString("title");
                        String subtitle = video.getString("subtitle");
                        String thumb= video.getString("thumb");
                        String desc= video.getString("description");
                        String sources= video.getString("sources");


                        Log.i("tag", sources);
                        sources = sources.replace("\\/","/");
                        sources = sources.replace("[\"","");
                        sources = sources.replace("\"]","");
                        Log.i("tag", sources);

                        VideoItem videoItem = new VideoItem(title,subtitle,thumb,sources,desc);
                        //리사이클러뷰가 보여줄 아이템리스트에 추가
                        items.add(videoItem);
                        adapter.notifyItemInserted(items.size()-1); //아예 changed하는건 리스트뷰에서 하는거임
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ThirdActivity.this, "error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //
    }
}