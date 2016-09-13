package com.youtube.sample.app.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youtube.sample.app.App;
import com.youtube.sample.app.R;
import com.youtube.sample.app.event.OnVideoClicked;
import com.youtube.sample.app.net.gson.VideoItem;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoHolder> {
    @Inject
    EventBus bus;

    private final Context context;
    private List<VideoItem> videos;

    private SimpleDateFormat sourceSdf;
    private SimpleDateFormat destinationSdf;

    public VideosAdapter(Context context) {
        ((App) context).getComponent().inject(this);
        this.context = context;
        this.videos = new ArrayList<>();
        this.sourceSdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        this.destinationSdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
    }

    public void setVideos(List<VideoItem> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoHolder(LayoutInflater.from(context).inflate(R.layout.video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        VideoItem video = videos.get(position);
        Glide.with(context).load(video.snippet.thumbnails.medium.url).into(holder.thumbnailImage);
        holder.titleText.setText(video.snippet.title);
        try {
            Date date = sourceSdf.parse(video.snippet.publishedAt);
            holder.publishedAtText.setText(context.getString(R.string.published_text, destinationSdf.format(date)));
            holder.publishedAtText.setVisibility(View.VISIBLE);
        } catch (ParseException e) {
            Timber.e(e, e.getMessage());
            holder.publishedAtText.setVisibility(View.INVISIBLE);
        }
        holder.descriptionText.setText(video.snippet.description);
        holder.videoContainer.setOnClickListener(view -> bus.post(new OnVideoClicked(video.id.videoId)));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.video_container)
        ViewGroup videoContainer;
        @Bind(R.id.thumbnail)
        ImageView thumbnailImage;
        @Bind(R.id.title)
        TextView titleText;
        @Bind(R.id.published_at)
        TextView publishedAtText;
        @Bind(R.id.description)
        TextView descriptionText;

        VideoHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
