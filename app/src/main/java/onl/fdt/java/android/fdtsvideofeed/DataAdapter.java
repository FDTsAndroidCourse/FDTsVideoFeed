package onl.fdt.java.android.fdtsvideofeed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.BasePlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import onl.fdt.java.android.fdtsvideofeed.api.payload.VideoEntity;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private static final Logger LOGGER = Logger.getLogger(DataAdapter.class.getName());
    private List<SimpleExoPlayer> playerList = new ArrayList<>();

    private List<SimpleExoPlayer> registerPlayer(SimpleExoPlayer player) {
        playerList.add(player);
        return playerList;
    }

    public void pauseAllPlayers() {
        playerList.forEach(o -> {
            o.setPlayWhenReady(false);
            o.getPlaybackState();
        });
    }

    @Override
    public int getItemCount() {
        return this.videoEntities.size();
    }

    private List<VideoEntity> videoEntities = new ArrayList<VideoEntity>();

    private final AppCompatActivity context;

    DataAdapter(AppCompatActivity context) {
        this.context = context;
    }

    void setVideoEntities(List<VideoEntity> videoEntities) {
        this.videoEntities = videoEntities;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        private final View view;
        private final TextView leftTextView;
        private final ImageView imageView;
        private final PlayerView playerView;

        private final TextView likeCountTextView;

        private int position = 0;

        ViewHolder(View v) {
            super(v);
            this.view = v;
            this.leftTextView = v.findViewById(R.id.left_text);
            this.imageView = v.findViewById(R.id.image_view);
            this.playerView = v.findViewById(R.id.video_player);

            this.likeCountTextView = v.findViewById(R.id.like_count);

        }

        private MediaSource buildMediaSource(Uri uri) {

            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(DataAdapter.this.context, "exoplayer");
            return new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri);
        }

        @SuppressLint("DefaultLocale")
        void loadData(final VideoEntity m, int position) {
            this.leftTextView.setText(m.getLeftText());
            this.likeCountTextView.setText(String.format("%d", m.getLikeCount()));

            Picasso.with(DataAdapter.this.context).load(m.getAvatar())
                    .centerCrop().fit()
                    .into(this.imageView);

            this.position = position;

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ViewGroup)imageView.getParent()).removeView(imageView);

                    SimpleExoPlayer player = new SimpleExoPlayer.Builder(DataAdapter.this.context).build();

                    player.prepare(buildMediaSource(Uri.parse(m.getFeedUrl())));
                    player.setPlayWhenReady(true);

                    playerView.setPlayer(player);

                    registerPlayer(player);

                }
            });

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_avtivity, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.loadData(videoEntities.get(position), position);
    }

}
