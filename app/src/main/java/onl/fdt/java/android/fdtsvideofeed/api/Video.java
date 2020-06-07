package onl.fdt.java.android.fdtsvideofeed.api;

import java.util.List;

import onl.fdt.java.android.fdtsvideofeed.api.payload.VideoEntity;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Video {
    @GET("/api/invoke/video/invoke/video")
    Call<List<VideoEntity>> invokeVideo();

}
