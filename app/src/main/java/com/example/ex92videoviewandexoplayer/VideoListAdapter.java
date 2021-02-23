package com.example.ex92videoviewandexoplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VH> {
    Context context;
    ArrayList<VideoItem> items;

    public VideoListAdapter(Context context, ArrayList<VideoItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        VideoItem item = items.get(position);
        holder.tvTitle.setText(item.title);
        holder.tvSubTitle.setText(item.subtitle);
        holder.tvDesc.setText(item.desc);

        //플레이어가 실행할 미디어 아이템 객체 생성
        MediaItem mediaItem = MediaItem.fromUri(item.sources);
        //플레이어에 미디어 설정[플레이어뷰가 아니야!!!]
        holder.player.setMediaItem(mediaItem);
        holder.player.prepare();
//        holder.player.play(); 바로 실행은 안시킴 여러동영상 동시에 실행되면 정신없어
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvSubTitle;
        PlayerView pv;
        TextView tvDesc;

        //플레이어뷰에 동영상을 실제로 플레이시키는 객체 참조변수
        SimpleExoPlayer player;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSubTitle = itemView.findViewById(R.id.tv_subtitle);
            pv = itemView.findViewById(R.id.pv);
            tvDesc = itemView.findViewById(R.id.tv_desc);

            player = new SimpleExoPlayer.Builder(context).build();
            pv.setPlayer(player);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoItem videoItem = items.get(getLayoutPosition());

                    Intent intent= new Intent(context, DetailActivity.class);
//                    intent.putExtra("item", videoItem) //객체를 통으로 전달 불가ㅠㅠ
                    //방법 1 . VideoItem 클래스를 아예 implements시킴 serializeable...근데 이건 지저분해짐
                    //고로, 객체를 json문자열로 변환
                    //Gson 라이브러리가 이런 변환을 아주 쉽게 해줌
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(videoItem); //객체--> json문자열로 변환
                    intent.putExtra("item",jsonStr);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VH holder) {
        super.onViewDetachedFromWindow(holder);
        holder.player.stop();
    }
}
