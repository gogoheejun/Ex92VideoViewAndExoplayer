package com.example.ex92videoviewandexoplayer;

import com.google.gson.annotations.SerializedName;

public class VideoItem {
    String title;
    String subtitle;
    String thumb; //이거 필요없긴한데 걍 넣었음
    String sources;

    //만약 자동파싱되는 json의 키값과 다른 멤버변수 명을 쓰고 싶다면
    @SerializedName("description") //description이라는 애를 만나면 desc로 하겠다!
    String desc;

    public VideoItem(String title, String subtitle, String thumb, String sources, String desc) {
        this.title = title;
        this.subtitle = subtitle;
        this.thumb = thumb;
        this.sources = sources;
        this.desc = desc;
    }
}
