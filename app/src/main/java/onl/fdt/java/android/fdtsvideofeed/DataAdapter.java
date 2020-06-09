/*
 * Copyright (c) 2020 fdt <frederic.dt.twh@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package onl.fdt.java.android.fdtsvideofeed;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.LoopingMediaSource;
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
import onl.fdt.java.android.fdtsvideofeed.doubleclick.DoubleClickListener;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private static final Logger LOGGER = Logger.getLogger(DataAdapter.class.getName());
    private List<SimpleExoPlayer> playerList = new ArrayList<>();

    private Handler handler = new Handler();

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

    /**
     * Data adapter set datasets method
     * @param videoEntities datasets to be set
     */
    public void setVideoEntities(List<VideoEntity> videoEntities) {
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

        private final TextView likeDisplay;

        private int position = 0;

        ViewHolder(View v) {
            super(v);
            this.view = v;
            this.leftTextView = v.findViewById(R.id.left_text);
            this.imageView = v.findViewById(R.id.image_view);
            this.playerView = v.findViewById(R.id.video_player);

            this.likeCountTextView = v.findViewById(R.id.like_count);

            this.likeDisplay = v.findViewById(R.id.like_display);

        }

        /**
         * https://codelabs.developers.google.com/codelabs/exoplayer-intro/#2
         * @param uri
         * @return
         */
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

            // Picasso https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library
            // Load image from URL
            Picasso.with(DataAdapter.this.context).load(m.getAvatar())
                    .centerCrop().fit()
                    .into(this.imageView);

            this.position = position;

            SimpleExoPlayer player = new SimpleExoPlayer.Builder(DataAdapter.this.context).build();

            // Avatar onClick listener
            // start playing video
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ViewGroup) imageView.getParent()).removeView(imageView);

                    // LoopingMediaSource
                    player.prepare(new LoopingMediaSource(buildMediaSource(Uri.parse(m.getFeedUrl()))));
                    // Auto start play
                    player.setPlayWhenReady(true);

                    playerView.setPlayer(player);
                    playerView.hideController();

                    registerPlayer(player);

                }
            });

            // Player double click listener
            playerView.setOnClickListener(new DoubleClickListener() {

                // Single click pause/resume
                @Override
                public void onClick(View v) {
                    super.onClick(v);

                    if (player.isPlaying()) {
                        player.setPlayWhenReady(false);
                    } else {
                        player.setPlayWhenReady(true);
                    }
                    player.getPlaybackState();
                }

                // Double click 'like' function
                @Override
                public void onDoubleClick() {
                    Animation animFadeIn = AnimationUtils.loadAnimation(DataAdapter.this.context, R.anim.fade_in);
                    likeDisplay.startAnimation(animFadeIn);

                    likeDisplay.setAlpha(1.0f);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Animation animFadeOut = AnimationUtils.loadAnimation(DataAdapter.this.context, R.anim.fade_out);
                            likeDisplay.startAnimation(animFadeOut);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    likeDisplay.setAlpha(0.0f);
                                }
                            }, 200L);
                        }
                    }, 1000L);
                }

            });

            // Hide controller
            // Which was deprecated, improvement needed
            playerView.setControllerVisibilityListener(i -> {
                if (i == 0) {
                    playerView.hideController();
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
